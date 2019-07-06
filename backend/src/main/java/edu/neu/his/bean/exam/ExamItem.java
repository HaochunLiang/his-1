package edu.neu.his.bean.exam;

import java.io.Serializable;

public class ExamItem implements Serializable {
    private Integer id;

    private Integer exam_id;

    private Integer non_drug_item_id;

    private String status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExam_id() {
        return exam_id;
    }

    public void setExam_id(Integer exam_id) {
        this.exam_id = exam_id;
    }

    public Integer getNon_drug_item_id() {
        return non_drug_item_id;
    }

    public void setNon_drug_item_id(Integer non_drug_item_id) {
        this.non_drug_item_id = non_drug_item_id;
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
        sb.append(", exam_id=").append(exam_id);
        sb.append(", non_drug_item_id=").append(non_drug_item_id);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}