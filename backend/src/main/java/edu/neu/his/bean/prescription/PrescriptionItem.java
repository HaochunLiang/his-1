package edu.neu.his.bean.prescription;

import java.io.Serializable;

public class PrescriptionItem implements Serializable {
    private Integer id;

    private String usage;

    private String dosage;

    private String frequency;

    private Integer day_count;

    private Integer times;

    private Integer amount;

    private Integer prescription_id;

    private Integer drug_id;

    private String status;

    private String note;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage == null ? null : usage.trim();
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage == null ? null : dosage.trim();
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency == null ? null : frequency.trim();
    }

    public Integer getDay_count() {
        return day_count;
    }

    public void setDay_count(Integer day_count) {
        this.day_count = day_count;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrescription_id() {
        return prescription_id;
    }

    public void setPrescription_id(Integer prescription_id) {
        this.prescription_id = prescription_id;
    }

    public Integer getDrug_id() {
        return drug_id;
    }

    public void setDrug_id(Integer drug_id) {
        this.drug_id = drug_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", usage=").append(usage);
        sb.append(", dosage=").append(dosage);
        sb.append(", frequency=").append(frequency);
        sb.append(", day_count=").append(day_count);
        sb.append(", times=").append(times);
        sb.append(", amount=").append(amount);
        sb.append(", prescription_id=").append(prescription_id);
        sb.append(", drug_id=").append(drug_id);
        sb.append(", status=").append(status);
        sb.append(", note=").append(note);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}