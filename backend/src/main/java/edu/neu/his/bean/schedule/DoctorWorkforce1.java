package edu.neu.his.bean.schedule;

import java.util.ArrayList;
import java.util.Date;

public class DoctorWorkforce1 {
    String name;
    String shift;//排班
    Date expiry_date;//有效期限
    Integer limit;//排班限额

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


    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

}
