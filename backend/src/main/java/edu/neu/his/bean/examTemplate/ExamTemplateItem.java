package edu.neu.his.bean.examTemplate;

import java.io.Serializable;

public class ExamTemplateItem implements Serializable {
    private Integer id;

    private Integer exam_template_id;

    private Integer non_drug_item_id;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExam_template_id() {
        return exam_template_id;
    }

    public void setExam_template_id(Integer exam_template_id) {
        this.exam_template_id = exam_template_id;
    }

    public Integer getNon_drug_item_id() {
        return non_drug_item_id;
    }

    public void setNon_drug_item_id(Integer non_drug_item_id) {
        this.non_drug_item_id = non_drug_item_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", exam_template_id=").append(exam_template_id);
        sb.append(", non_drug_item_id=").append(non_drug_item_id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}