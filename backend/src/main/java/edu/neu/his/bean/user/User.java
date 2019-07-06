package edu.neu.his.bean.user;

public class User {
    private int uid;
    private String username;
    private String password;
    private String real_name;
    private int department_id;
    private int role_id;
    private boolean participate_in_scheduling;
    private String title;
    private String role_name;
    private String department_name;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public boolean isParticipate_in_scheduling() {
        return participate_in_scheduling;
    }

    public void setParticipate_in_scheduling(boolean participate_in_scheduling) {
        this.participate_in_scheduling = participate_in_scheduling;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"uid\":")
                .append(uid);
        sb.append(",\"username\":\"")
                .append(username).append('\"');
        sb.append(",\"password\":\"")
                .append(password).append('\"');
        sb.append(",\"real_name\":\"")
                .append(real_name).append('\"');
        sb.append(",\"department_id\":")
                .append(department_id);
        sb.append(",\"role_id\":")
                .append(role_id);
        sb.append(",\"participate_in_scheduling\":")
                .append(participate_in_scheduling);
        sb.append(",\"title\":\"")
                .append(title).append('\"');
        sb.append(",\"role_name\":\"")
                .append(role_name).append('\"');
        sb.append(",\"department_name\":\"")
                .append(department_name).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
