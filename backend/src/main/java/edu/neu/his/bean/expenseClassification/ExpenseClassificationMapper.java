package edu.neu.his.bean.expenseClassification;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
@Component(value = "ExpenseClassificationMapper")
public interface ExpenseClassificationMapper {
    @Select("SELECT id FROM expense_classification where fee_name = #{fee_name}")
    int findClassificationIdByName(@Param("fee_name") String fee_name);

    @Select("SELECT fee_name FROM expense_classification where id = #{id}")
    String findClassificationById(@Param("id") int id);

    @Select("SELECT id, pinyin, fee_name " +
            "FROM  expense_classification " +
            "WHERE expense_classification.name = #{name}")
    ExpenseClassification findByName(@Param("name") String name);

    @Insert("INSERT INTO expense_classification(id, pinyin, fee_name) " +
            "VALUES(#{id}, #{pinyin}, #{fee_name})")
    void insert(ExpenseClassification expenseClassification);

    @Update("UPDATE expense_classification SET pinyin = #{pinyin}, fee_name = #{fee_name} WHERE id = #{id}")
    void update(ExpenseClassification expenseClassification);

    @Select("SELECT * FROM  expense_classification")
    List<ExpenseClassification> findAll();

    @Delete("DELETE FROM expense_classification WHERE id=#{id}")
    void delete(@Param("id") int id);

    @Select("SELECT count(*) FROM expense_classification where id = #{id}")
    int checkIdExistNums(ExpenseClassification expenseClassification);

    @Select("SELECT count(*) FROM expense_classification where fee_name = #{fee_name}")
    int checkNameExistNums(ExpenseClassification expenseClassification);

    @Select("SELECT count(*) FROM expense_classification where id = #{id}")
    int checkId(@Param("id") int id);

}
