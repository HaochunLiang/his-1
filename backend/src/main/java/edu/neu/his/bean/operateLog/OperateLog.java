package edu.neu.his.bean.operateLog;

public class OperateLog {
    private int id;
    private int user_id;
    private int operate_id;
    private String type;
    private int bill_record_id;
    private float fee;
    private String create_time;

    public OperateLog(int user_id, int operate_id, String type, int bill_record_id, float fee, String create_time){
        this.user_id = user_id;
        this.operate_id = operate_id;
        this.type = type;
        this.bill_record_id = bill_record_id;
        this.fee = fee;
        this.create_time = create_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOperate_id() {
        return operate_id;
    }

    public void setOperate_id(int operate_id) {
        this.operate_id = operate_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBill_record_id() {
        return bill_record_id;
    }

    public void setBill_record_id(int bill_record_id) {
        this.bill_record_id = bill_record_id;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
