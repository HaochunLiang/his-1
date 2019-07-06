package edu.neu.his.bean.schedule;

import java.util.ArrayList;
import java.util.Date;

public class DoctorWorkforce {
    int id;
    String name;
    String shift;//排班
    Date expiry_date;//有效期限
    Boolean valid;//是否有效
    Integer limit;//排班限额
    Integer limitRatio;//排班次数
    Integer scheduled;//剩余次数
    //Integer scheduledPM;
    ArrayList<int[][]> workforce;//已排班时间
    String registration_Level;//号别

    public DoctorWorkforce(int id, String name,String shift,Date expiry_date,Integer limit,String registration_Level){
        this.id = id;
        this.name = name;
        this.shift = shift;
        this.expiry_date = expiry_date;
        this.limit = limit;
        this.registration_Level = registration_Level;
    }

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

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public Date getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(Date expiry_date) {
        this.expiry_date = expiry_date;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimitRatio() {
        return limitRatio;
    }

    public void setLimitRatio(Integer limitRatio) {
        this.limitRatio = limitRatio;
    }

    public Integer getScheduled() {
        return scheduled;
    }

    public void setScheduled(Integer scheduled) {
        this.scheduled = scheduled;
    }

    public ArrayList<int[][]> getWorkforce() {
        return workforce;
    }

    public void setWorkforce(ArrayList<int[][]> workforce) {
        this.workforce = workforce;
    }

    public String getRegistration_Level() {
        return registration_Level;
    }

    public void setRegistration_Level(String registration_Level) {
        this.registration_Level = registration_Level;
    }
}
