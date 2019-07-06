package edu.neu.his.bean.expenseClassification;

public class ExpenseClassification {
    private int id;
    private String pinyin;
    private String fee_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFee_name() {
        return fee_name;
    }

    public void setFee_name(String fee_name) {
        this.fee_name = fee_name;
    }

    @Override
    public String toString() {
        return "ExpenseClassification{" +
                "id=" + id +
                ", pinyin='" + pinyin + '\'' +
                ", fee_name='" + fee_name + '\'' +
                '}';
    }
}