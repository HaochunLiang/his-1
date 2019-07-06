package edu.neu.his.bean.disease;

public class Disease {
    private int id;
    private String code;
    private String name;
    private int classification_id;
    private String pinyin;

    public Disease(String code, String name, int classification_id, String pinyin, String custom_name, String custom_pinyin, String classification_name) {
        this.code = code;
        this.name = name;
        this.classification_id = classification_id;
        this.pinyin = pinyin;
        this.custom_name = custom_name;
        this.custom_pinyin = custom_pinyin;
        this.classification_name = classification_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String custom_name;
    private String custom_pinyin;

    private String classification_name;

    public Disease(){}



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClassification_id() {
        return classification_id;
    }

    public void setClassification_id(int classification_id) {
        this.classification_id = classification_id;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getCustom_name() {
        return custom_name;
    }

    public void setCustom_name(String custom_name) {
        this.custom_name = custom_name;
    }

    public String getCustom_pinyin() {
        return custom_pinyin;
    }

    public void setCustom_pinyin(String custom_pinyin) {
        this.custom_pinyin = custom_pinyin;
    }

    public String getClassification_name() {
        return classification_name;
    }

    public void setClassification_name(String classification_name) {
        this.classification_name = classification_name;
    }

}
