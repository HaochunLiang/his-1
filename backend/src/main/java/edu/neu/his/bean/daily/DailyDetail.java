package edu.neu.his.bean.daily;

import java.io.Serializable;


public class DailyDetail implements Serializable {
    private Integer id;

    private Integer daily_collect_id;

    private Integer bill_record_id;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDaily_collect_id() {
        return daily_collect_id;
    }

    public void setDaily_collect_id(Integer daily_collect_id) {
        this.daily_collect_id = daily_collect_id;
    }

    public Integer getBill_record_id() {
        return bill_record_id;
    }

    public void setBill_record_id(Integer bill_record_id) {
        this.bill_record_id = bill_record_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", daily_collect_id=").append(daily_collect_id);
        sb.append(", bill_record_id=").append(bill_record_id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}