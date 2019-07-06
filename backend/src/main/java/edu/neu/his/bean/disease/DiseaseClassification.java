package edu.neu.his.bean.disease;

public class DiseaseClassification {
    private int id;
    private String name;

    public DiseaseClassification(String name) {
        this.name = name;
    }

    public DiseaseClassification(){}

    public DiseaseClassification(int id,String name) {
        this.name = name;
        this.id = id;
    }

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
}
