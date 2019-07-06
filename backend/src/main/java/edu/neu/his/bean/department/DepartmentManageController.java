package edu.neu.his.bean.department;

import edu.neu.his.config.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现科室管理的相关功能
 *
 * @author 王婧怡
 * @author 李井瑞
 * @version 1.0
 */
@RestController
@RequestMapping("/departmentManage")
public class DepartmentManageController {
    @Autowired
    private DepartmentService departmentService;

    /**
     *根据名称查找科室
     * @param req 前端传递的request，包含“name”等字段
     * @return 返回查找结果
     */
    @PostMapping("/findByName")
    @ResponseBody
    public Map departmentFindByName(@RequestBody Map req){
        String name = (String)req.get("name");
        return Response.ok(departmentService.findDepartmentByName(name));
    }

    /**
     *获得所有科室的列表
     * @return 返回查找到的所有科室和状态码等信息
     */
    @RequestMapping("/getAll")
    @ResponseBody
    public Map listAllDepartment(){
        Map data = new HashMap();
        data.put("department_classification",departmentService.findAllClassification());
        data.put("department",departmentService.findAll());
        return Response.ok(data);
    }

    /**
     *更新科室
     * @param req 前端传递的request，包含Department类中的各个字段
     * @return 返回response，表示是否成功
     */
    @PostMapping("/update")
    @ResponseBody
    public Map updateDepartment(@RequestBody Map req){
        Department department = req2Department(req);
        if(canUpdate(department)) {
            departmentService.updateDepartment(department);
            return Response.ok();
        }else{
            return Response.error("编号冲突 或 该类别不存在!");
        }
    }

    /**
     *创建新的科室
     * @param req 前端传递的request，包含Department类中的各个字段
     * @return 返回response，表示是否成功
     */
    @PostMapping("/add")
    @ResponseBody
    public Map insertDepartment(@RequestBody Map req){
        Department department = req2Department(req);
        if(canInsert(department)) {
            departmentService.insertDepartment(department);
            return Response.ok();
        }else{
            return Response.error("编号冲突 或 该类别不存在!");
        }
    }

    /**
     *批量删除科室
     * @param req 前端传递的request，要删除的科室id的列表
     * @return 返回response，表示是否成功
     */
    @PostMapping("/delete")
    @ResponseBody
    public Map  deleteDepartment(@RequestBody Map req){
        ArrayList departments_id = (ArrayList)req.get("data");
        for(int i=0; i<departments_id.size(); i++) {
            int id = (int)departments_id.get(i);
            departmentService.deleteDepartment(id);
        }
        return Response.ok();
    }

    /**
     *从文件中批量导入科室
     * @param file 需要上传的文件
     * @return 返回response，表示是否成功
     */
    @PostMapping("/import")
    @ResponseBody
    public Map batchImport(@RequestParam("file") MultipartFile file) {
        String pathName = "/home/xuranus/Desktop/";//想要存储文件的地址
        String pname = file.getOriginalFilename();//获取文件名（包括后缀）
        pathName += pname;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(pathName);
            fos.write(file.getBytes()); // 写入文件
            System.out.println("文件上传成功");
            if(departmentService.importFromFile(pathName))
                return Response.ok();
            else
                return Response.error("解析失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("上传失败");
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                return Response.error("上传失败");
            }
        }
    }

    /**
     *从前端传递的response中获得信息创建Department对象
     * @param req 前端传递的response
     * @return 返回创建的Department对象
     */
    private Department req2Department(Map req) {
        int id = (int)req.get("id");
        String pinyin = (String)req.get("pinyin");
        String name = (String)req.get("name");
        int classification_id = (int)req.get("classification_id");
        String type = (String)req.get("type");
        return new Department(id,pinyin,name,type,classification_id);
    }

    /**
     *判断该科室能否插入数据库
     * @param department 要插入数据库的Department对象
     * @return 返回能否插入数据库，true代表能，false代表不能
     */
    private boolean canInsert(Department department){
        if(departmentService.canInsert(department) && departmentService.existClassification(department))
            return true;
        else return false;
    }

    /**
     *判断该科室能否进行更新
     * @param department 要进行更新的科室
     * @return 返回能否对数据库中的记录进行更新，true代表能，false代表不能
     */
    private boolean canUpdate(Department department){
        if(departmentService.canUpdate(department) && departmentService.existClassification(department))
            return true;
        else return false;
    }

}
