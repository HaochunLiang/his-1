package edu.neu.his.bean.dailyCheck;

import edu.neu.his.bean.expenseClassification.ExpenseClassification;
import edu.neu.his.util.Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.neu.his.config.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/outpatientDailyReportCheck")
public class DailyCheckController {
    @Autowired
    private DailyCheckService dailyCheckService;

    private List<ExpenseClassification>  expenseClassifications;
    private Float[] classificationTotal;
    private  Float registrationTotal = 0f;
    //private Float[] total;
    private Report report;
    private DailyCheck dailyCheck;
    List<Integer> init_bill_record_id;
    List<Integer> invalid_bill_record_id;
    List<Integer> reprint_bill_record_id;
    int init_bill_record_num = 0;
    int invalid_bill_record_num = 0;
    int reprint_bill_record_num = 0;

    @RequestMapping("/init")
    @ResponseBody
    public Map init(){
        return Response.ok(dailyCheckService.getTollCollector());
    }

    @PostMapping("/getReport")
    @ResponseBody
    public Map getReport(@RequestBody Map req){
        String start_date = (String)req.get("start_date");
        String end_date = (String)req.get("end_date");
        int toll_collector_id = (int)req.get("toll_collector_id");

        //System.out.println("wxx "+start_date+" "+end_date+" "+toll_collector_id);


        List<Report> reports = dailyCheckService.getReport(start_date,end_date,toll_collector_id);

        //System.out.println("reports: "+reports);

        getBillRecord(reports);
        //getRegistrationFee(reports);
        classificationTotal = dailyCheckService.getTotal(start_date,end_date,toll_collector_id);

        //得到所有费用科目
        expenseClassifications = getAllClassifitation();
        //total = new Float[expenseClassifications.size()];
        //得到每个费用科目的总额
        List<ClassificationFee> classifitationFees = getClassifitationFee(expenseClassifications,toll_collector_id,start_date,end_date);

        dailyCheck = new DailyCheck(toll_collector_id,classificationTotal[0],classificationTotal[1],classifitationFees,
                init_bill_record_id, invalid_bill_record_id, reprint_bill_record_id, init_bill_record_num, invalid_bill_record_num,reprint_bill_record_num);
        return Response.ok(dailyCheck);
    }

    /*public void getRegistrationFee(List<Report> reports){
        reports.forEach(report -> {
            System.out.println("reg fee:"+report.getMedical_record_id());
            dailyCheckService.getRegistrationFee(report.getMedical_record_id());
        });
    }*/

    public List<ExpenseClassification> getAllClassifitation(){
        return dailyCheckService.getAllClassifitation();
    }

    public List<ClassificationFee> getClassifitationFee(List<ExpenseClassification> expenseClassifications,int toll_collector_id,String start_date,String end_date){
        return dailyCheckService.getClassifitationFee(expenseClassifications,toll_collector_id,start_date,end_date);

     /*   Map<String,Float> classifitationFees = new HashMap<String,Float>();
        expenseClassifications.forEach(expenseClassification->{
            List<Integer> classificationCosts = dailyCheckService.getClassifitationFee(expenseClassification.getId(),toll_collector_id);
            classificationCosts.forEach(classificationCost->{
                total[expenseClassifications.indexOf(expenseClassification)] += classificationCost;
            });
            classifitationFees.put(expenseClassification.getFee_name(),total[expenseClassifications.indexOf(expenseClassification)]);
        });
        return classifitationFees;*/
    }

    public void getBillRecord(List<Report> reports){
        init_bill_record_id = new ArrayList<Integer>();
        invalid_bill_record_id = new ArrayList<Integer>();
        reprint_bill_record_id = new ArrayList<Integer>();
        reports.forEach(report -> {
            if(report.getPrint_status()==0) {
                init_bill_record_id.add(report.getBill_record_id());
                init_bill_record_num++;
            }else if(report.getPrint_status()==1) {
                invalid_bill_record_id.add(report.getBill_record_id());
                invalid_bill_record_num++;
            }else if(report.getPrint_status()==2) {
                reprint_bill_record_id.add(report.getBill_record_id());
                reprint_bill_record_num++;
            }
        });
    }

    @PostMapping("/confirmCheck")
    @ResponseBody
    public Map confirmCheck(@RequestBody Map req){
        String start_date = (String)req.get("start_date");
        String end_date = (String)req.get("end_date");
        int toll_collector_id = (Integer)req.get("toll_collector_id");
        int checker_id = Utils.getSystemUser(req).getUid();
        //int checker_id = (Integer)req.get("checker_id");

        if (canUpdate(start_date,end_date,toll_collector_id,checker_id)) {
            dailyCheckService.confirmCheck(start_date,end_date,toll_collector_id,checker_id);
            return Response.ok();
        } else {
            return Response.error("没有需要更新的日结核对信息！");
        }
    }

    private boolean canUpdate(String start_date,String end_date,int toll_collector_id,int checker_id){
        return dailyCheckService.exist(start_date,end_date,toll_collector_id,checker_id);
    }

    @PostMapping("/history")
    @ResponseBody
    public Map history(@RequestBody Map req){
        String start_date = (String)req.get("start_date");
        String end_date = (String)req.get("end_date");
        return Response.ok(dailyCheckService.history(start_date,end_date));
    }


    @PostMapping("/departmentCheck")
    @ResponseBody
    public Map getDepartmentCheck(@RequestBody Map req){
        String start_date = (String)req.get("start_date");
        String end_date = (String)req.get("end_date");

        Map data = new HashMap();
        data.put("columns",dailyCheckService.getDepartmentColumns(start_date,end_date));
        data.put("tableData", dailyCheckService.getDepartmentCheck(start_date,end_date));
        data.put("chartsData",dailyCheckService.getChartsDatas());
        return Response.ok(data);
    }

    @PostMapping("/userCheck")
    @ResponseBody
    public Map getUserCheck(@RequestBody Map req){
        String start_date = (String)req.get("start_date");
        String end_date = (String)req.get("end_date");
        //return Response.ok(dailyCheckService.getUserCheck(start_date,end_date));

        Map data = new HashMap();
        data.put("columns",dailyCheckService.getUserColumns());
        data.put("tableData", dailyCheckService.getUserCheck(start_date,end_date));
        data.put("chartsData",dailyCheckService.getChartsDatas());
        return Response.ok(data);
    }
}
