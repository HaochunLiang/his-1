package edu.neu.his.bean.schedule;

public class AllSchedule {
    private int id;
    private String schedule_date;
    private int week;
    private String name;
    private String department_name;
    private String registration_Level;
    private String shift;
    private int reg_limit;
    private int residue;
    private String valid;

    public AllSchedule(){}

    public AllSchedule(String name, String schedule_date, String shift, int week){
        this.name = name;
        this.schedule_date = schedule_date;
        this.shift = shift;
        this.week = week;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getRegistration_Level() {
        return registration_Level;
    }

    public void setRegistration_Level(String registration_Level) {
        this.registration_Level = registration_Level;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getReg_limit() {
        return reg_limit;
    }

    public void setReg_limit(int reg_limit) {
        this.reg_limit = reg_limit;
    }

    public int getResidue() {
        return residue;
    }

    public void setResidue(int residue) {
        this.residue = residue;
    }

    @Override
    public String toString() {
        return "AllSchedule{" +
                "id=" + id +
                ", schedule_date='" + schedule_date + '\'' +
                ", week=" + week +
                ", name='" + name + '\'' +
                ", department_name='" + department_name + '\'' +
                ", registration_Level='" + registration_Level + '\'' +
                ", shift='" + shift + '\'' +
                ", reg_limit=" + reg_limit +
                ", residue=" + residue +
                ", valid='" + valid + '\'' +
                '}';
    }
}
