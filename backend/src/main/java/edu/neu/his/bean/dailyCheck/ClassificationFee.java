package edu.neu.his.bean.dailyCheck;

public class ClassificationFee {
    private String name;
    private Float fee;

    public ClassificationFee(String name, Float fee) {
        this.name = name;
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }
}
