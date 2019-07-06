package edu.neu.his.bean.dailyCheck;

public class ChartsData {
    private String name;
    private Float sum;

    public ChartsData(String name, Float sum) {
        this.name = name;
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }
}
