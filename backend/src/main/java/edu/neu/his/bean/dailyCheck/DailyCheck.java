package edu.neu.his.bean.dailyCheck;

import edu.neu.his.bean.expenseClassification.ExpenseClassification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailyCheck {
    private Integer toll_collector_id;
    private Float classificationTotal;
    private  Float registrationTotal;
    List<ClassificationFee> classifitationFees;//每个费用科目的费用

    List<Integer> init_bill_record_id;
    List<Integer> invalid_bill_record_id;
    List<Integer> reprint_bill_record_id;
    int init_bill_record_num = 0;
    int invalid_bill_record_num = 0;
    int reprint_bill_record_num = 0;

    public DailyCheck(Integer toll_collector_id, Float classificationTotal, Float registrationTotal,
                      List<ClassificationFee> classifitationFees, List<Integer> init_bill_record_id,
                      List<Integer> invalid_bill_record_id, List<Integer> reprint_bill_record_id,
                      int init_bill_record_num,int invalid_bill_record_num,int reprint_bill_record_num) {
        this.toll_collector_id = toll_collector_id;
        this.classificationTotal = classificationTotal;
        this.registrationTotal = registrationTotal;
        this.classifitationFees = classifitationFees;
        this.init_bill_record_id = init_bill_record_id;
        this.invalid_bill_record_id = invalid_bill_record_id;
        this.reprint_bill_record_id = reprint_bill_record_id;
        this.init_bill_record_num = init_bill_record_num;
        this.invalid_bill_record_num = invalid_bill_record_num;
        this.reprint_bill_record_num = reprint_bill_record_num;
    }

    public Integer getToll_collector_id() {
        return toll_collector_id;
    }

    public void setToll_collector_id(Integer toll_collector_id) {
        this.toll_collector_id = toll_collector_id;
    }

    public Float getClassificationTotal() {
        return classificationTotal;
    }

    public void setClassificationTotal(Float classificationTotal) {
        this.classificationTotal = classificationTotal;
    }

    public Float getRegistrationTotal() {
        return registrationTotal;
    }

    public void setRegistrationTotal(Float registrationTotal) {
        this.registrationTotal = registrationTotal;
    }

    public List<ClassificationFee> getClassifitationFees() {
        return classifitationFees;
    }

    public void setClassifitationFees(List<ClassificationFee> classifitationFees) {
        this.classifitationFees = classifitationFees;
    }

    public List<Integer> getInit_bill_record_id() {
        return init_bill_record_id;
    }

    public void setInit_bill_record_id(List<Integer> init_bill_record_id) {
        this.init_bill_record_id = init_bill_record_id;
    }

    public List<Integer> getInvalid_bill_record_id() {
        return invalid_bill_record_id;
    }

    public void setInvalid_bill_record_id(List<Integer> invalid_bill_record_id) {
        this.invalid_bill_record_id = invalid_bill_record_id;
    }

    public List<Integer> getReprint_bill_record_id() {
        return reprint_bill_record_id;
    }

    public void setReprint_bill_record_id(List<Integer> reprint_bill_record_id) {
        this.reprint_bill_record_id = reprint_bill_record_id;
    }
}
