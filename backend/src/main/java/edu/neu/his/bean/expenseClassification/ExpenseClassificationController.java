package edu.neu.his.bean.expenseClassification;

import edu.neu.his.util.Utils;
import org.springframework.web.bind.annotation.RestController;
import edu.neu.his.config.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expenseClassificationManage")
public class ExpenseClassificationController {
    @Autowired
    private ExpenseClassificationService expenseClassificationService;

    @RequestMapping("/all")
    @ResponseBody
    public Map findAll(){
        return Response.ok(expenseClassificationService.findAll());
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateExpenseClassification(@RequestBody ExpenseClassification expenseClassification) {
        if (canUpdate(expenseClassification)) {
            expenseClassificationService.updateExpenseClassification(expenseClassification);
            return Response.ok();
        } else {
            return Response.error("编号冲突 或 该费用科目不存在!");
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public Map insertExpenseClassification(@RequestBody Map req){
        ExpenseClassification expenseClassification = Utils.fromMap(req,ExpenseClassification.class);
        if(canInsert(expenseClassification)) {
            expenseClassificationService.insertExpenseClass(expenseClassification);
            return Response.ok();
        }else
            return Response.error("错误, 该ID或名称已存在");
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map  deleteExpenseClassification(@RequestBody Map req){
        List ids = (List)req.get("idArr");
        ids.forEach(id->{
            if(canDelete((int)id))
                expenseClassificationService.deleteExpenseClassification((int)id);
        });
        return Response.ok();
    }

    private boolean canUpdate(ExpenseClassification expenseClassification){
        return expenseClassificationService.exist(expenseClassification);
    }

    private boolean canInsert(ExpenseClassification expenseClassification){
        return !expenseClassificationService.exist(expenseClassification);
    }

    private boolean canDelete(int id){
        return expenseClassificationService.canDelete(id);
    }
}
