package edu.neu.his.bean.registration;

public class Registration {
    private int medical_record_id;
    private String address;
    private int age;
    private String birthday;
    private String consultation_date;
    private String id_number;
    private String medical_certificate_number;
    private String medical_category;
    private String patient_name;
    private int outpatient_doctor_id;
    private int registration_department_id;
    private int settlement_category_id;
    private String registration_category;
    private String status;
    private float cost;
    private String registration_source;
    private String gender;
    private String medical_insurance_diagnosis;

    public Registration(){

    }

    public int getMedical_record_id() {
        return medical_record_id;
    }

    public void setMedical_record_id(int medical_record_id) {
        this.medical_record_id = medical_record_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getConsultation_date() {
        return consultation_date;
    }

    public void setConsultation_date(String consultation_date) {
        this.consultation_date = consultation_date;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getMedical_certificate_number() {
        return medical_certificate_number;
    }

    public void setMedical_certificate_number(String medical_certificate_number) {
        this.medical_certificate_number = medical_certificate_number;
    }

    public String getMedical_category() {
        return medical_category;
    }

    public void setMedical_category(String medicial_category) {
        this.medical_category = medicial_category;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public int getOutpatient_doctor_id() {
        return outpatient_doctor_id;
    }

    public void setOutpatient_doctor_id(int outpatient_doctor_id) {
        this.outpatient_doctor_id = outpatient_doctor_id;
    }

    public int getRegistration_department_id() {
        return registration_department_id;
    }

    public void setRegistration_department_id(int registration_department_id) {
        this.registration_department_id = registration_department_id;
    }

    public int getSettlement_category_id() {
        return settlement_category_id;
    }

    public void setSettlement_category_id(int settlement_category_id) {
        this.settlement_category_id = settlement_category_id;
    }

    public String getRegistration_category() {
        return registration_category;
    }

    public void setRegistration_category(String registration_category) {
        this.registration_category = registration_category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getRegistration_source() {
        return registration_source;
    }

    public void setRegistration_source(String registration_source) {
        this.registration_source = registration_source;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMedical_insurance_diagnosis() {
        return medical_insurance_diagnosis;
    }

    public void setMedical_insurance_diagnosis(String medical_insurance_diagnosis) {
        this.medical_insurance_diagnosis = medical_insurance_diagnosis;
    }
}
