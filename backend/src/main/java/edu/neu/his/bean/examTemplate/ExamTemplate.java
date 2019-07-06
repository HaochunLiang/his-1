package edu.neu.his.bean.examTemplate;

import java.io.Serializable;

public class ExamTemplate implements Serializable {
    private Integer id;

    private String template_name;

    private Integer user_id;

    private Integer department_id;

    private Integer display_type;

    private Integer type;

    private String create_time;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name == null ? null : template_name.trim();
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    public Integer getDisplay_type() {
        return display_type;
    }

    public void setDisplay_type(Integer display_type) {
        this.display_type = display_type;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", template_name=").append(template_name);
        sb.append(", user_id=").append(user_id);
        sb.append(", department_id=").append(department_id);
        sb.append(", display_type=").append(display_type);
        sb.append(", type=").append(type);
        sb.append(", create_time=").append(create_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}