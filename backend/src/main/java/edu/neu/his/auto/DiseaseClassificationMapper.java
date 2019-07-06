package edu.neu.his.auto;

import edu.neu.his.bean.disease.DiseaseClassification;
import edu.neu.his.util.Importable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiseaseClassificationMapper extends Importable<DiseaseClassification> {

    @Override
    @Insert("INSERT INTO disease_classification(id, name) VALUES(#{id}, #{name})")
    int insert(DiseaseClassification instance);
}
