package edu.neu.his.bean.billRecord;

import java.io.Serializable;

/**
 * 该类代表票据记录，即对病历涉及的收费项目开具的发票
 *
 * @author 王婧怡
 * @version 1.0
 */
public class BillRecord implements Serializable {
    private Integer id;

    private Integer medical_record_id;

    private String type;

    private Integer print_status;

    private Float cost;

    private Float should_pay = 0f;

    private Float truely_pay = 0f;

    private Float retail_fee = 0f;

    private Integer user_id;

    private String create_time;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getPrint_status() {
        return print_status;
    }

    public void setPrint_status(Integer print_status) {
        this.print_status = print_status;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getShould_pay() {
        return should_pay;
    }

    public void setShould_pay(Float should_pay) {
        this.should_pay = should_pay;
    }

    public Float getTruely_pay() {
        return truely_pay;
    }

    public void setTruely_pay(Float truely_pay) {
        this.truely_pay = truely_pay;
    }

    public Float getRetail_fee() {
        return retail_fee;
    }

    public void setRetail_fee(Float retail_fee) {
        this.retail_fee = retail_fee;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time == null ? null : create_time.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", medical_record_id=").append(medical_record_id);
        sb.append(", type=").append(type);
        sb.append(", print_status=").append(print_status);
        sb.append(", cost=").append(cost);
        sb.append(", should_pay=").append(should_pay);
        sb.append(", truely_pay=").append(truely_pay);
        sb.append(", retail_fee=").append(retail_fee);
        sb.append(", user_id=").append(user_id);
        sb.append(", create_time=").append(create_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
