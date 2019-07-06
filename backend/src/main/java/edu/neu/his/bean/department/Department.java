package edu.neu.his.bean.department;

/**
 *该类代表医院的科室
 *
 * @author 王婧怡
 * @version 1.0
 */
public class Department {
    private int id;
    private String pinyin;
    private String name;
    private String type;
    private int classification_id;
    private String classification_name;

    /**
     * 带参数构造函数,初始化id、拼音、名称、类型和科室分类id
     * @param id 科室id
     * @param pinyin 科室拼音
     * @param name 科室名称
     * @param type 科室类型
     * @param classification_id 科室分类id
     */
    public Department(int id,String pinyin,String name,String type,int classification_id) {
        this.id = id;
        this.pinyin = pinyin;
        this.name = name;
        this.type = type;
        this.classification_id = classification_id;
    }

    public Department(){}

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setClassification_id(int classification_id) {this.classification_id = classification_id;}

    public int getClassification_id() {return classification_id;}

    public String getClassification_name() {
        return classification_name;
    }

    public void setClassification_name(String classification_name) {
        this.classification_name = classification_name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"pinyin\":\"")
                .append(pinyin).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"type\":\"")
                .append(type).append('\"');
        sb.append(",\"classification_id\":")
                .append(classification_id);
        sb.append(",\"classification_name\":\"")
                .append(classification_name).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
