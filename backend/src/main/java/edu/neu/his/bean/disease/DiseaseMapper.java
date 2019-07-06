
package edu.neu.his.bean.disease;

import edu.neu.his.util.Importable;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "DiseaseMapper")
public interface DiseaseMapper extends Importable<Disease> {
    @Select("SELECT disease.id,disease.code, disease.name, classification_id, disease_classification.name as classification_name, pinyin, custom_name, custom_pinyin " +
            "FROM  disease, disease_classification " +
            "WHERE disease.classification_id = disease_classification.id and disease.name = #{name}")
    Disease findByName(@Param("name") String name);

    @Select("SELECT disease.id, disease.code, disease.name, classification_id, disease_classification.name as classification_name, pinyin, custom_name, custom_pinyin " +
            "FROM  disease, disease_classification " +
            "WHERE disease.classification_id = disease_classification.id and classification_id = #{classification_id}")
    List<Disease> findAll(@Param("classification_id") int classification_id);

    @Insert("INSERT INTO disease(name, code, id, classification_id, pinyin, custom_name, custom_pinyin) " +
            "VALUES(#{name}, #{code}, #{id}, #{classification_id}, #{pinyin}, #{custom_name}, #{custom_pinyin})")
    void insertDisease(Disease disease);

    @Update("UPDATE disease SET code = #{code}, name = #{name}, id = #{id}, classification_id=#{classification_id}, pinyin=#{pinyin}," +
            "custom_name=#{custom_name}, custom_pinyin = #{custom_pinyin} WHERE id = #{raw_id}")
    void update(@Param("raw_id") int rawId,@Param("id") int id,@Param("code") String code,@Param("name") String name,@Param("classification_id") int classification_id,
                @Param("pinyin") String pinyin,@Param("custom_name") String custom_name,@Param("custom_pinyin") String custom_pinyin);

    @Delete("DELETE FROM disease WHERE id=#{id}")
    void deleteDisease(int id);

    @Insert("INSERT INTO disease_classification(id, name) VALUES(#{id}, #{name})")
    void insertDiseaseClassification(DiseaseClassification diseaseClassification);

    @Override
    default int insert(Disease instance){
        this.insertDisease(instance);
        return 1;
    }

    @Select("SELECT id FROM disease_classification where name = #{classification_name}")
    int findDiseaseClassificationIdByName(@Param("classification_name") String classification_name);

    @Select("SELECT * FROM disease_classification")
    List<DiseaseClassification> findAllDiseaseClassification();

    @Select("SELECT count(*) FROM disease WHERE code = #{code}")
    int checkCodeExist(@Param("code") String code);

    @Select("SELECT count(*) FROM disease_classification WHERE id = #{classification_id}")
    int checkClassificationExist(@Param("classification_id") int classification_id);

    @Select("SELECT count(*) FROM disease WHERE id = #{id}")
    int checkIdExist(@Param("id") int id);

    @Select("SELECT disease.id, disease.code, disease.name, classification_id, disease_classification.name as classification_name, pinyin, custom_name, custom_pinyin " +
            "FROM  disease, disease_classification " +
            "WHERE disease.classification_id = disease_classification.id")
    List<Disease> findall();
}
 