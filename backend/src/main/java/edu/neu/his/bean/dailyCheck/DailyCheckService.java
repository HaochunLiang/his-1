package edu.neu.his.bean.dailyCheck;

import edu.neu.his.bean.billRecord.BillRecord;
import edu.neu.his.bean.expenseClassification.ExpenseClassification;
import edu.neu.his.bean.outpatientCharges.OutpatientChargesRecord;
import edu.neu.his.bean.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DailyCheckService {
    @Autowired
    private DailyCheckMapper dailyCheckMapper;

    private Float registrationFee = 0f;
    private Float total = 0f;
    private List<ExpenseClassification>  expenseClassifications;
    private float[] classtotal;

    private Map<String,String> columnsMap = new HashMap<String,String>();
    private Map<String,String> tableData = new HashMap<String,String>();
    private List<Map<String,String>> tableDatas = new ArrayList<Map<String,String>>();
    private List<ChartsData> chartsDatas = new ArrayList<ChartsData>();
    private int colNum = 0;

    @Transactional
    public List<InitUser> getTollCollector() {
        return dailyCheckMapper.getTollCollector();
    }

    @Transactional
    public List<Report> getReport(String start_date,String end_date,int toll_collector_id){
        List<Report> reports = dailyCheckMapper.getReport(start_date,end_date,toll_collector_id);

        total = 0f;
        registrationFee = 0f;
        for(Report report:reports){
            total += report.getCost();
            //System.out.println(report.toString());
        }
        return dailyCheckMapper.getReport(start_date,end_date,toll_collector_id);
    }

    @Transactional
    public Float[] getTotal(String start_date,String end_date,int toll_collector_id){
        Float[] t= new Float[]{total,getRegistrationFee(start_date,end_date,toll_collector_id)};
        return t;
    }

    public Float getRegistrationFee(String start_date,String end_date,int toll_collector_id){
        List<Float> fees = dailyCheckMapper.getRegistrationFees(start_date,end_date,toll_collector_id);
        for(Float fee:fees) {
            registrationFee += fee;
        }
        return registrationFee;
    }

    @Transactional
    public List<ExpenseClassification> getAllClassifitation(){
        return dailyCheckMapper.getAllClassifitation();
    }

    @Transactional
    public List<ClassificationFee> getClassifitationFee(List<ExpenseClassification> expenseClassifications, int toll_collector_id,String start_date,String end_date){
        List<ClassificationFee> classifitationFees = new ArrayList<ClassificationFee>();
        classtotal = new float[expenseClassifications.size()];

        expenseClassifications.forEach(expenseClassification-> {
            List<Float> classificationCosts = dailyCheckMapper.getClassifitationFee(start_date,end_date,expenseClassification.getId(), toll_collector_id);
            if (!classificationCosts.isEmpty()) {
                classificationCosts.forEach(classificationCost -> {
                    classtotal[expenseClassifications.indexOf(expenseClassification)] += classificationCost;
                });
                //classifitationFees.put(expenseClassification.getFee_name(), classtotal[expenseClassifications.indexOf(expenseClassification)]);
                classifitationFees.add(new ClassificationFee(expenseClassification.getFee_name(),classtotal[expenseClassifications.indexOf(expenseClassification)]));
            }
        });
        return classifitationFees;
        //return dailyCheckMapper.getClassifitationFee(expense_classification_id,toll_collector_id);
    }

    @Transactional
    public void confirmCheck(String start_date,String end_date,int toll_collector_id,int checker_id) {
        dailyCheckMapper.confirmCheck(start_date,end_date,toll_collector_id,checker_id);
    }

    @Transactional
    public boolean exist(String start_date,String end_date,int toll_collector_id,int checker_id) {
        return dailyCheckMapper.checkIdExistNums(start_date,end_date,toll_collector_id,checker_id)>0;
    }

    @Transactional
    public List<BillRecord> history(String start_date, String end_date) {
        return dailyCheckMapper.history(start_date, end_date);
    }

    @Transactional
    public List<Column> getDepartmentColumns(String start_date, String end_date){
        List<Column> columns = new ArrayList<Column>();
        columnsMap.clear();
        //tableData.clear();

        columns.add(new Column("科室名称","col_1"));
        columns.add(new Column("看诊人次","col_2"));
        columns.add(new Column("发票数量","col_3"));
        columnsMap.put("科室名称","col_1");
        columnsMap.put("看诊人次","col_2");
        columnsMap.put("发票数量","col_3");
        //tableData.put("","col_1");
        //tableData.put("","col_2");
        //tableData.put("","col_3");
        List<String> depColx;
        depColx = dailyCheckMapper.getDepartmentColumns(start_date, end_date);
        int i = 0;


        for(String s:depColx){
            columns.add(new Column(s,"col"+i));
            columnsMap.put(s,"col"+i);
            //tableData.put("col"+i,"");
            i++;
        }
        //System.out.println("columnsMap:"+columnsMap);
        colNum = i;
        return columns;
    }

    @Transactional
    public List<Map<String,String>> getDepartmentCheck(String start_date,String end_date){
        //@XUranus tableData必须清空
        tableData.clear();

        List<ObjectCount> departmentCounts = dailyCheckMapper.getDepartmentCount(start_date,end_date);
        List<ObjectSum> departmentSums =  dailyCheckMapper.getDepartmentSum(start_date,end_date);
        //需要提交的表格中所有数据
        List<ObjectCheck> departmentChecks = new ArrayList<ObjectCheck>();
        Map<String,Float> sum;
        Float chartsDataSum = 0f;

        //每个科室的名字，人次，订单数
        for(ObjectCount departmentCount:departmentCounts){
            chartsDataSum = 0f;
            ObjectCheck departmentCheck = new ObjectCheck(departmentCount.getName(),departmentCount.getPerson_time(),departmentCount.getBill_num());
            //每个费用科目的名字，总额
            for(ObjectSum departmentSum:departmentSums){
                if(departmentSum.getName().equals(departmentCount.getName())){
                    sum = departmentCheck.getSum();
                    sum.put(columnsMap.get(departmentSum.getFee_name()),departmentSum.getSum());
                    departmentCheck.setSum(sum);
                    chartsDataSum += departmentSum.getSum();
                }
            }
            departmentChecks.add(departmentCheck);
            chartsDatas.add(new ChartsData(departmentCount.getName(),chartsDataSum));
        }


        int key = 1;
        for(ObjectCheck objectCheck:departmentChecks){
            tableData = new HashMap<String,String>();
            tableData.put("col_1",objectCheck.getName());
            tableData.put("col_2",String.valueOf(objectCheck.getPerson_time()));
            tableData.put("col_3",String.valueOf(objectCheck.getBill_num()));
            for(int i=0;i<colNum;i++){
                sum = objectCheck.getSum();
                if(sum.containsKey("col"+i)){
                    tableData.put("col"+i,sum.get("col"+i).toString());
                }else{
                    tableData.put("col"+i,"0");
                }
            }
            tableData.put("key",String.valueOf(key));
            key++;
            tableDatas.add(tableData);
        }
        return tableDatas;
    }

    @Transactional
    public List<ChartsData> getChartsDatas(){
        return chartsDatas;
    }

    //用户工作量统计
    /*@Transactional
    public List<ObjectCheck> getUserCheck(String start_date,String end_date){
        List<ObjectCount> userCounts = dailyCheckMapper.getUserCount(start_date,end_date);
        List<ObjectSum> userSums =  dailyCheckMapper.getUserSum(start_date,end_date);
        List<ObjectCheck> userChecks = new ArrayList<ObjectCheck>();
        Map<String,Float> sum;

        for(ObjectCount userCount:userCounts){
            ObjectCheck userCheck = new ObjectCheck(userCount.getName(),userCount.getPerson_time(),userCount.getBill_num());
            for(ObjectSum userSum:userSums){
                if(userSum.getName().equals(userCount.getName())){
                    sum = userCheck.getSum();
                    sum.put(userSum.getFee_name(),userSum.getSum());
                    userCheck.setSum(sum);
                }
            }
            userChecks.add(userCheck);
        }
        return userChecks;
    }*/

    @Transactional
    public List<Column> getUserColumns(){
        List<Column> columns = new ArrayList<Column>();
        columnsMap.clear();

        columns.add(new Column("医生姓名","col_1"));
        columns.add(new Column("看诊人次","col_2"));
        columns.add(new Column("发票数量","col_3"));
        columnsMap.put("医生姓名","col_1");
        columnsMap.put("看诊人次","col_2");
        columnsMap.put("发票数量","col_3");
        List<String> userColx;
        //userColx = dailyCheckMapper.getUserColumns(start_date, end_date);
        userColx = dailyCheckMapper.getUserColumns();
        int i = 0;


        for(String s:userColx){
            columns.add(new Column(s,"col"+i));
            columnsMap.put(s,"col"+i);
            i++;
        }
        colNum = i;
        return columns;
    }

    @Transactional
    public List<Map<String,String>> getUserCheck(String start_date,String end_date){
        List<ObjectCount> userCounts = dailyCheckMapper.getUserCount(start_date,end_date);
        List<ObjectSum> userSums =  dailyCheckMapper.getUserSum(start_date,end_date);
        List<ObjectCheck> userChecks = new ArrayList<ObjectCheck>();
        Map<String,Float> sum;
        Float chartsDataSum = 0f;

        //@XUranus:chartsData必须清空
        chartsDatas.clear();

        for(ObjectCount userCount:userCounts){
            chartsDataSum = 0f;
            ObjectCheck userCheck = new ObjectCheck(userCount.getName(),userCount.getPerson_time(),userCount.getBill_num());
            for(ObjectSum userSum:userSums){
                if(userSum.getName().equals(userCount.getName())){
                    sum = userCheck.getSum();
                    sum.put(columnsMap.get(userSum.getFee_name()),userSum.getSum());
                    userCheck.setSum(sum);
                    chartsDataSum += userSum.getSum();
                }
            }
            userChecks.add(userCheck);
            chartsDatas.add(new ChartsData(userCount.getName(),chartsDataSum));
        }



        int key = 1;
        for(ObjectCheck objectCheck:userChecks){
            tableData = new HashMap<String,String>();
            tableData.put("col_1",objectCheck.getName());
            tableData.put("col_2",String.valueOf(objectCheck.getPerson_time()));
            tableData.put("col_3",String.valueOf(objectCheck.getBill_num()));
            for(int i=0;i<colNum;i++){
                sum = objectCheck.getSum();
                if(sum.containsKey("col"+i)){
                    tableData.put("col"+i,sum.get("col"+i).toString());
                }else{
                    tableData.put("col"+i,"0");
                }
            }
            tableData.put("key",String.valueOf(key));
            key++;
            tableDatas.add(tableData);
        }
        return tableDatas;
    }
}
