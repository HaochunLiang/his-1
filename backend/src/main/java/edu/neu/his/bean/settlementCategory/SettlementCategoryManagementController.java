package edu.neu.his.bean.settlementCategory;

import edu.neu.his.config.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/settlementCategoryManagement")
public class SettlementCategoryManagementController {
    @Autowired
    SettlementCategoryService settlementCategoryService;

    @RequestMapping("/all")
    @ResponseBody
    public Map getAllSettlementCategory() {
        return Response.ok(settlementCategoryService.findAll());
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map deleteSettlementCategory(@RequestBody Map req) {
        List<Integer> failed = new ArrayList<>();
        List<Integer> settlementCategoryIds = (List<Integer>)req.get("data");
        settlementCategoryIds.forEach(id -> {
            if(canDelete(id))
                settlementCategoryService.deleteSettlementCategoryById(id);
            else
                failed.add(id);
        });
        if(failed.isEmpty())
            return Response.ok();
        else{
            Map data = new HashMap();
            data.put("success number",settlementCategoryIds.size()-failed.size());
            data.put("fail number",failed.size());
            data.put("fail id",failed);
            return Response.error(data);
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public Map addAllSettlementCategory(@RequestBody SettlementCategory settlementCategory) {
        if(canInsert(settlementCategory)) {
            settlementCategoryService.addSettlementCategory(settlementCategory);
            return Response.ok();
        }else
            return Response.error("id或名称冲突");
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateAllSettlementCategory(@RequestBody SettlementCategory settlementCategory) {
        if(canUpdate(settlementCategory)) {
            settlementCategoryService.update(settlementCategory);
            return Response.ok();
        }else
            return Response.error("名称冲突 或 id不存在");
    }

    private boolean canInsert(SettlementCategory settlementCategory){
        return settlementCategoryService.canInsert(settlementCategory);
    }

    private boolean canUpdate(SettlementCategory settlementCategory){
        return settlementCategoryService.canUpdate(settlementCategory);
    }

    private boolean canDelete(int id){
        return settlementCategoryService.canDelete(id)!=0;
    }

}
