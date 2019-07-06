package edu.neu.his.bean.medicalRecord;

import java.io.Serializable;

public class MedicalRecord implements Serializable {
    private Integer id;

    private String create_time;

    private String status;

    private String chief_complaint;

    private String current_medical_history;

    private String current_treatment_situation;

    private String past_history;

    private String allergy_history;

    private String physical_examination;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time == null ? null : create_time.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getChief_complaint() {
        return chief_complaint;
    }

    public void setChief_complaint(String chief_complaint) {
        this.chief_complaint = chief_complaint == null ? null : chief_complaint.trim();
    }

    public String getCurrent_medical_history() {
        return current_medical_history;
    }

    public void setCurrent_medical_history(String current_medical_history) {
        this.current_medical_history = current_medical_history == null ? null : current_medical_history.trim();
    }

    public String getCurrent_treatment_situation() {
        return current_treatment_situation;
    }

    public void setCurrent_treatment_situation(String current_treatment_situation) {
        this.current_treatment_situation = current_treatment_situation == null ? null : current_treatment_situation.trim();
    }

    public String getPast_history() {
        return past_history;
    }

    public void setPast_history(String past_history) {
        this.past_history = past_history == null ? null : past_history.trim();
    }

    public String getAllergy_history() {
        return allergy_history;
    }

    public void setAllergy_history(String allergy_history) {
        this.allergy_history = allergy_history == null ? null : allergy_history.trim();
    }

    public String getPhysical_examination() {
        return physical_examination;
    }

    public void setPhysical_examination(String physical_examination) {
        this.physical_examination = physical_examination == null ? null : physical_examination.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", create_time=").append(create_time);
        sb.append(", status=").append(status);
        sb.append(", chief_complaint=").append(chief_complaint);
        sb.append(", current_medical_history=").append(current_medical_history);
        sb.append(", current_treatment_situation=").append(current_treatment_situation);
        sb.append(", past_history=").append(past_history);
        sb.append(", allergy_history=").append(allergy_history);
        sb.append(", physical_examination=").append(physical_examination);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}