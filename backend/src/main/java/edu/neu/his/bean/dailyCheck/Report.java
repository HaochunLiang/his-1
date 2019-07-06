package edu.neu.his.bean.dailyCheck;

public class Report {
    private Integer medical_record_id;
    private Float cost;
    private Integer bill_record_id;
    private Integer print_status;

    public Report(Integer medical_record_id, Float cost ,Integer bill_record_id, Integer print_status) {
        this.medical_record_id = medical_record_id;
        this.cost = cost;
        this.bill_record_id = bill_record_id;
        this.print_status = print_status;
    }

    public Integer getMedical_record_id() {
        return medical_record_id;
    }

    public void setMedical_record_id(Integer medical_record_id) {
        this.medical_record_id = medical_record_id;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Integer getBill_record_id() {
        return bill_record_id;
    }

    public void setBill_record_id(Integer bill_record_id) {
        this.bill_record_id = bill_record_id;
    }

    public Integer getPrint_status() {
        return print_status;
    }

    public void setPrint_status(Integer print_status) {
        this.print_status = print_status;
    }

    @Override
    public String toString() {
        return "Report{" +
                "medical_record_id=" + medical_record_id +
                ", cost=" + cost +
                ", bill_record_id=" + bill_record_id +
                ", print_status=" + print_status +
                '}';
    }
}
