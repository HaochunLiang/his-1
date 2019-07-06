package edu.neu.his.bean.exam;

import java.io.Serializable;

public class ExamItemResult implements Serializable {
    private Integer id;

    private String file;

    private Integer exam_item_id;

    private Integer user_id;

    private String result;

    private String advice;

    private String create_time;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file == null ? null : file.trim();
    }

    public Integer getExam_item_id() {
        return exam_item_id;
    }

    public void setExam_item_id(Integer exam_item_id) {
        this.exam_item_id = exam_item_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice == null ? null : advice.trim();
    }

    public String getCreate_time() {return create_time;}

    public void setCreate_time(String create_time) {this.create_time = create_time;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", file=").append(file);
        sb.append(", exam_item_id=").append(exam_item_id);
        sb.append(", user_id=").append(user_id);
        sb.append(", result=").append(result);
        sb.append(", advice=").append(advice);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}