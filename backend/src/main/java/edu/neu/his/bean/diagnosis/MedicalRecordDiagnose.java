package edu.neu.his.bean.diagnosis;

import java.io.Serializable;

public class MedicalRecordDiagnose implements Serializable {
    private Integer id;

    private Integer medical_record_id;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMedical_record_id() {
        return medical_record_id;
    }

    public void setMedical_record_id(Integer medical_record_id) {
        this.medical_record_id = medical_record_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", medical_record_id=").append(medical_record_id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}