package edu.neu.his.bean.drug;

import edu.neu.his.bean.outpatientCharges.OutpatientChargesRecordStatus;
import edu.neu.his.bean.prescription.PrescriptionItem;
import edu.neu.his.bean.prescription.PrescriptionStatus;
import edu.neu.his.config.Response;
import edu.neu.his.bean.prescription.PrescriptionService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/drugDispense")
public class DrugDispenseController {
    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private DrugService drugService;

    @PostMapping("/list")
    @ResponseBody
    public Map list(@RequestBody Map req){
        int medical_record_id = (int)req.get("medical_record_id");
        List<Map> result = prescriptionService.getList(medical_record_id,
                PrescriptionStatus.PrescriptionItemToTake, OutpatientChargesRecordStatus.Charged);

        return Response.ok(result);
    }

    @PostMapping("/submit")
    @ResponseBody
    public Map submit(@RequestBody Map req){
        List<Integer> ids = (List)req.get("prescription_item_id");
        if(!prescriptionService.allCanTake(ids))
            return Response.error("错误，有id不存在或库存不足");

        ids.forEach(id->{
            PrescriptionItem prescriptionItem = prescriptionService.findPrescriptionItemById((int)id);
            Drug drug = drugService.selectDrugById(prescriptionItem.getDrug_id());
            int stock = drug.getStock()-prescriptionItem.getAmount();
            drug.setStock(stock);
            drugService.updateDrug(drug);
            prescriptionItem.setStatus(PrescriptionStatus.PrescriptionItemTaken);
            prescriptionService.updatePrescriptionItem(prescriptionItem);
        });

        return Response.ok();
    }

    @PostMapping("/withdrawHistory")
    @ResponseBody
    public Map withdrawHistory(){
        List<PrescriptionItem> list = prescriptionService.findPrescriptionItemByStatus(PrescriptionStatus.PrescriptionItemReturned);
        if(list==null)
            return Response.error("无退药记录");

        List<Map> result = new ArrayList<>();
        list.forEach(prescriptionItem -> {
            Map item = Utils.objectToMap(prescriptionItem);
            Drug drug = drugService.selectDrugById(prescriptionItem.getDrug_id());
            item.put("drug",drug);
            result.add(item);
        });
        return Response.ok(result);
    }
}
