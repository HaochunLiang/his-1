package edu.neu.his.bean.exam;

import java.io.Serializable;

public class Exam implements Serializable {
    private Integer id;

    private Integer medical_record_id;

    private Integer type;

    private String create_time;

    private Integer user_id;

    private String status;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time == null ? null : create_time.trim();
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
        sb.append(", create_time=").append(create_time);
        sb.append(", user_id=").append(user_id);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}