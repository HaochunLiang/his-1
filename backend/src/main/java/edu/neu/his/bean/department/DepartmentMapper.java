package edu.neu.his.bean.department;

import edu.neu.his.util.Importable;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 该类对数据库中的department表进行数据持久化操作
 */
@Mapper
@Component(value = "DepartmentMapper")
public interface DepartmentMapper extends Importable<Department> {
    /**
     *
     * @param name
     * @return
     */
    @Select("SELECT department.id, pinyin, department.name, type,department_classification.name as classification_name, department_classification.id as classification_id " +
            "FROM department, department_classification " +
            "WHERE department.classification_id = department_classification.id and department.name = #{name}")
    Department findByName(@Param("name") String name);

    /**
     * 查找所有科室分类
     * @return 返回所有科室分类的列表
     */
    @Select("SELECT * from department_classification")
    List<DepartmentClassification> findAllClassification();

    /**
     * 根据名称查找对应的科室分类id
     * @param name 科室分类名称
     * @return 返回对应的科室分类id
     */
    @Select("SELECT id FROM department_classification where name = #{name}")
    int findClassificationIdByName(@Param("name") String name);

    /**
     *向数据库的department表中插入一条记录
     * @param department 要插入数据库中的Department对象
     */
    @Insert("INSERT INTO department(id,pinyin, name , type, classification_id ) VALUES(#{id}, #{pinyin}, #{name} ,#{type},#{classification_id})")
    int insert(Department department);

    /**
     *根据id更新数据库的department表中相应的记录
     * @param department 要在数据库中更新的Department对象
     */
    @Update("UPDATE department SET pinyin = #{pinyin} ,type = #{type} ,name = #{name}, classification_id = #{classification_id} WHERE id = #{id}")
    void update(Department department);

    /**
     *查找所有科室记录
     * @return 返回所有科室记录的列表
     */
    @Select("SELECT department.id, pinyin, department.name, type,department_classification.name as classification_name, department_classification.id as classification_id " +
            "FROM department, department_classification " +
            "WHERE department.classification_id = department_classification.id;\n")
    List<Department> findAll();

    /**
     *根据id从数据库中删除对应的科室
     * @param id 要删除的科室的id
     */
    @Delete("DELETE FROM department WHERE id=#{id}")
    void deleteDepartment(int id);

    /**
     *检查数据库中是否已存在该科室id
     * @param id 要检查是否存在的科室id
     * @return 返回该科室id在数据库department表中存在的数量
     */
    @Select("SELECT count(*) FROM department WHERE id = #{id}")
    int checkIdExists(@Param("id") int id);

    /**
     *检查数据库中是否已存在该科室名称
     * @param name 要检查是否存在的科室名称
     * @return 返回该科室名称在数据库department表中存在的数量
     */
    @Select("SELECT count(*) FROM department WHERE name = #{name}")
    int checkNameExists(@Param("name") String name);

    /**
     *检查数据库中是否存在该科室分类
     * @param id 科室分类id
     * @return 返回该科室分类id在department_classification表中存在的数量
     */
    @Select("SELECT count(*) FROM department_classification WHERE id = #{id}")
    int checkClassificationExists(@Param("id") int id);

    /**
     *检查数据库中是否存在该科室分类
     * @param id 科室分类id
     * @return 返回该科室分类id在department_classification表中存在的数量
     */
    @Select("SELECT * FROM department WHERE id = #{id}")
    Department selectById(@Param("id") int id);
}
