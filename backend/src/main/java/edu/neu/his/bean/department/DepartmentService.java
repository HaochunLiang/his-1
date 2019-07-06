package edu.neu.his.bean.department;

import edu.neu.his.util.ExcelImportation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 实现处理数据库中department、department_classification表的相关操作
 *
 * @author 王婧怡
 * @author 李井瑞
 * @version 1.0
 */
@Service
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 更新数据库中的一条相应的科室记录
     * @param department 要进行更新的Department对象
     */
    @Transactional
    public void updateDepartment(Department department) {
        departmentMapper.update(department);
    }

    /**
     * 从数据库中根据名称找到对应的科室
     * @param name 科室名称
     * @return 返回根据名称找到的对应科室
     */
    @Transactional
    public Department findDepartmentByName(String name) {
        return departmentMapper.findByName(name);
    }

    /**
     * 向数据库中插入一条科室记录
     * @param department 要插入数据库中的Department对象
     */
    @Transactional
    public void insertDepartment(Department department) {
        departmentMapper.insert(department);
    }

    /**
     * 查找数据库中所有科室的列表
     * @return 返回查找到的所有科室列表
     */
    @Transactional
    public List<Department> findAll() {
        return departmentMapper.findAll();
    }

    /**
     * 查找数据库中所有科室分类的列表
     * @return 返回查找到的所有科室分类的列表
     */
    @Transactional
    public List<DepartmentClassification> findAllClassification() {
        return departmentMapper.findAllClassification();
    }

    /**
     * 根据id从数据库中删除对应科室
     * @param id 要删除的科室的id
     */
    @Transactional
    public void deleteDepartment(int id) {
        departmentMapper.deleteDepartment(id);
    }

    /**
     * 判断该科室的信息能否进行更新
     * @param department 要更新的Department对象
     * @return 返回是否能更新，true代表能，false代表不能
     */
    @Transactional
    public boolean canUpdate(Department department) {
        int id_num = departmentMapper.checkIdExists(department.getId());
        int name_num = departmentMapper.checkNameExists(department.getName());
        if(id_num==0 || name_num>1 || id_num>1)
            return false;
        else if(name_num==1){
            Department d = departmentMapper.findByName(department.getName());
            if(d.getId() != department.getId())
                return false;
            else
                return true;
        }else
            return true;
    }

    /**
     * 判断该科室能否插入数据库中
     * @param department 要插入数据库的Department对象
     * @return 返回是否能插入，true代表能，false代表不能
     */
    @Transactional
    public boolean canInsert(Department department) {
        int id_num = departmentMapper.checkIdExists(department.getId());
        int name_num = departmentMapper.checkNameExists(department.getName());
        if(id_num>=1 || name_num>=1)
            return false;
        else
            return true;
    }

    /**
     * 从文件中批量导入科室
     * @param pathName 文件路径
     * @return 返回是否导入成功，true代表成功，false代表失败
     */
    public boolean importFromFile(String pathName) {
        try {
            ExcelImportation excel = new ExcelImportation(new FileInputStream(pathName), Department.class, departmentMapper);
            excel.setColumnFields("id", "classification_id", "pinyin", "name", "type");
            excel.skipLine(1);
            Map<String, Function<String, ?>> preFunctionMap = excel.getPreFunctionMap();
            preFunctionMap.put("classification_id", departmentMapper::findClassificationIdByName);
            excel.exec();
            return true;
        } catch (Exception e)  {
            return false;
        }
    }

    /**
     *检查科室分类是否存在
     * @param department 要进行检查的Department对象
     * @return 返回该科室的科室分类是否存在，true代表存在，false代表不存在
     */
    @Transactional
    public boolean existClassification(Department department) {
        return departmentMapper.checkClassificationExists(department.getClassification_id())==1;
    }
}
