package edu.neu.his.bean.workloadstatictic;

public class WorkloadRecord {
    private int id;
    private String type;
    private int user_id;
    private float fee;
    private int department_id;
    private String create_time;


    public WorkloadRecord() {

    }

    public WorkloadRecord(String type, int user_id, float fee, int department_id) {
        this.type = type;
        this.user_id = user_id;
        this.fee = fee;
        this.department_id = department_id;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

}
