package edu.neu.his.bean.nondrug;

import java.io.Serializable;

public class NonDrugChargeItem implements Serializable {
    private Integer id;

    private String code;

    private String pinyin;

    private String format;

    private String name;

    private Float fee;

    private Integer expense_classification_id;

    private Integer department_id;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public Integer getExpense_classification_id() {
        return expense_classification_id;
    }

    public void setExpense_classification_id(Integer expense_classification_id) {
        this.expense_classification_id = expense_classification_id;
    }

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", pinyin=").append(pinyin);
        sb.append(", format=").append(format);
        sb.append(", name=").append(name);
        sb.append(", fee=").append(fee);
        sb.append(", expense_classification_id=").append(expense_classification_id);
        sb.append(", department_id=").append(department_id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}