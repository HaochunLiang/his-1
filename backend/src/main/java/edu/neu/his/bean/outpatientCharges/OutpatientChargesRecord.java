package edu.neu.his.bean.outpatientCharges;

import java.io.Serializable;

public class OutpatientChargesRecord implements Serializable {
    private Integer id;

    private Integer medical_record_id;

    private Integer bill_record_id;

    private Integer item_id;

    private Integer type;

    private Integer expense_classification_id;

    private String status;

    private Integer quantity;

    private Float cost;

    private Integer execute_department_id;

    private String create_time;

    private String collect_time;

    private String return_time;

    private Integer create_user_id;

    private Integer collect_user_id;

    private Integer return_user_id;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMedical_record_id() {
        return medical_record_id;
    }

    public void setMedical_record_id(Integer medical_record_id) {
        this.medical_record_id = medical_record_id;
    }

    public Integer getBill_record_id() {
        return bill_record_id;
    }

    public void setBill_record_id(Integer bill_record_id) {
        this.bill_record_id = bill_record_id;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getExpense_classification_id() {
        return expense_classification_id;
    }

    public void setExpense_classification_id(Integer expense_classification_id) {
        this.expense_classification_id = expense_classification_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Integer getExecute_department_id() {
        return execute_department_id;
    }

    public void setExecute_department_id(Integer execute_department_id) {
        this.execute_department_id = execute_department_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time == null ? null : create_time.trim();
    }

    public String getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(String collect_time) {
        this.collect_time = collect_time == null ? null : collect_time.trim();
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time == null ? null : return_time.trim();
    }

    public Integer getCreate_user_id() {
        return create_user_id;
    }

    public void setCreate_user_id(Integer create_user_id) {
        this.create_user_id = create_user_id;
    }

    public Integer getCollect_user_id() {
        return collect_user_id;
    }

    public void setCollect_user_id(Integer collect_user_id) {
        this.collect_user_id = collect_user_id;
    }

    public Integer getReturn_user_id() {
        return return_user_id;
    }

    public void setReturn_user_id(Integer return_user_id) {
        this.return_user_id = return_user_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", medical_record_id=").append(medical_record_id);
        sb.append(", bill_record_id=").append(bill_record_id);
        sb.append(", item_id=").append(item_id);
        sb.append(", type=").append(type);
        sb.append(", expense_classification_id=").append(expense_classification_id);
        sb.append(", status=").append(status);
        sb.append(", quantity=").append(quantity);
        sb.append(", cost=").append(cost);
        sb.append(", execute_department_id=").append(execute_department_id);
        sb.append(", create_time=").append(create_time);
        sb.append(", collect_time=").append(collect_time);
        sb.append(", return_time=").append(return_time);
        sb.append(", create_user_id=").append(create_user_id);
        sb.append(", collect_user_id=").append(collect_user_id);
        sb.append(", return_user_id=").append(return_user_id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}