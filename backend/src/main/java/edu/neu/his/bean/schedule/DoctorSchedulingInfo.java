package edu.neu.his.bean.schedule;


public class DoctorSchedulingInfo {
    private int id;
    private String name;
    private String department_name;
    private String title;
    private String shift;
    private String expiry_date;
    private int scheduling_limit;
    private String registration_Level;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public int getScheduling_limit() {
        return scheduling_limit;
    }

    public void setScheduling_limit(int scheduling_limit) {
        this.scheduling_limit = scheduling_limit;
    }

    public String getRegistration_Level() {
        return registration_Level;
    }

    public void setRegistration_Level(String registration_Level) {
        this.registration_Level = registration_Level;
    }

    @Override
    public String toString() {
        return "DoctorSchedulingInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department_name='" + department_name + '\'' +
                ", title='" + title + '\'' +
                ", shift='" + shift + '\'' +
                ", expiry_date='" + expiry_date + '\'' +
                ", scheduling_limit=" + scheduling_limit +
                ", registration_Level='" + registration_Level + '\'' +
                '}';
    }
}
