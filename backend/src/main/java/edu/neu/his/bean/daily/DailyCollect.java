package edu.neu.his.bean.daily;

import java.io.Serializable;

/**
 * 该类代表日结记录
 *
 * @author 王婧怡
 * @version 1.0
 */
public class DailyCollect implements Serializable {
    private Integer id;

    private String start_time;

    private String end_time;

    private Integer user_id;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time == null ? null : start_time.trim();
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time == null ? null : end_time.trim();
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", start_time=").append(start_time);
        sb.append(", end_time=").append(end_time);
        sb.append(", user_id=").append(user_id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}