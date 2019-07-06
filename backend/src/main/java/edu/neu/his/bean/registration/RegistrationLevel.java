package edu.neu.his.bean.registration;

public class RegistrationLevel {
    private int id;
    private String name;
    private boolean is_default;
    private int seq_num;
    private float fee;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_default() {
        return is_default;
    }

    public void setIs_default(boolean is_default) {
        this.is_default = is_default;
    }

    public int getSeq_num() {
        return seq_num;
    }

    public void setSeq_num(int seq_num) {
        this.seq_num = seq_num;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }
}
