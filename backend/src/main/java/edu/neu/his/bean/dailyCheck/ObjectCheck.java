package edu.neu.his.bean.dailyCheck;

import java.util.HashMap;
import java.util.Map;

public class ObjectCheck {
    private String name;
    private int person_time;
    private int bill_num;
    private Map<String,Float> sum = new HashMap<String,Float>();

    public ObjectCheck(String name, int person_time, int bill_num) {
        this.name = name;
        this.person_time = person_time;
        this.bill_num = bill_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPerson_time() {
        return person_time;
    }

    public void setPerson_time(int person_time) {
        this.person_time = person_time;
    }

    public int getBill_num() {
        return bill_num;
    }

    public void setBill_num(int bill_num) {
        this.bill_num = bill_num;
    }

    public Map<String, Float> getSum() {
        return sum;
    }

    public void setSum(Map<String, Float> sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "ObjectCheck{" +
                "name='" + name + '\'' +
                ", person_time=" + person_time +
                ", bill_num=" + bill_num +
                ", sum=" + sum +
                '}';
    }
}
