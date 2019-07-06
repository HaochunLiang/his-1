package edu.neu.his.bean.diagnosis;

import java.io.Serializable;

public class MedicalRecordDiagnoseTemplateItem implements Serializable {
    private Integer id;

    private Integer medical_record_diagnose_template_id;

    private Integer disease_id;

    private String disease_name;

    private String disease_code;

    private String diagnose_type;

    private Boolean main_symptom;

    private Boolean suspect;

    private String syndrome_differentiation;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMedical_record_diagnose_template_id() {
        return medical_record_diagnose_template_id;
    }

    public void setMedical_record_diagnose_template_id(Integer medical_record_diagnose_template_id) {
        this.medical_record_diagnose_template_id = medical_record_diagnose_template_id;
    }

    public Integer getDisease_id() {
        return disease_id;
    }

    public void setDisease_id(Integer disease_id) {
        this.disease_id = disease_id;
    }

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name == null ? null : disease_name.trim();
    }

    public String getDisease_code() {
        return disease_code;
    }

    public void setDisease_code(String disease_code) {
        this.disease_code = disease_code == null ? null : disease_code.trim();
    }

    public String getDiagnose_type() {
        return diagnose_type;
    }

    public void setDiagnose_type(String diagnose_type) {
        this.diagnose_type = diagnose_type == null ? null : diagnose_type.trim();
    }

    public Boolean getMain_symptom() {
        return main_symptom;
    }

    public void setMain_symptom(Boolean main_symptom) {
        this.main_symptom = main_symptom;
    }

    public Boolean getSuspect() {
        return suspect;
    }

    public void setSuspect(Boolean suspect) {
        this.suspect = suspect;
    }

    public String getSyndrome_differentiation() {
        return syndrome_differentiation;
    }

    public void setSyndrome_differentiation(String syndrome_differentiation) {
        this.syndrome_differentiation = syndrome_differentiation == null ? null : syndrome_differentiation.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", medical_record_diagnose_template_id=").append(medical_record_diagnose_template_id);
        sb.append(", disease_id=").append(disease_id);
        sb.append(", disease_name=").append(disease_name);
        sb.append(", disease_code=").append(disease_code);
        sb.append(", diagnose_type=").append(diagnose_type);
        sb.append(", main_symptom=").append(main_symptom);
        sb.append(", suspect=").append(suspect);
        sb.append(", syndrome_differentiation=").append(syndrome_differentiation);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}