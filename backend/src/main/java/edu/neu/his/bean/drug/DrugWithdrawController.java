package edu.neu.his.bean.drug;

import edu.neu.his.bean.outpatientCharges.ChargeAndRefundService;
import edu.neu.his.bean.prescription.PrescriptionItem;
import edu.neu.his.bean.outpatientCharges.OutpatientChargesRecordStatus;
import edu.neu.his.bean.prescription.PrescriptionStatus;
import edu.neu.his.config.Response;
import edu.neu.his.bean.prescription.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/drugWithdrawal")
public class DrugWithdrawController {
    @Autowired
    PrescriptionService prescriptionService;

    @Autowired
    DrugService drugService;

    @Autowired
    ChargeAndRefundService chargeAndRefundService;

    @PostMapping("/list")
    @ResponseBody
    public Map list(@RequestBody Map req){
        int medical_record_id = (int)req.get("medical_record_id");
        List<Map> list = prescriptionService.getList(medical_record_id,
                PrescriptionStatus.PrescriptionItemTaken, OutpatientChargesRecordStatus.Charged);
        return Response.ok(list);
    }

    @PostMapping("/submit")
    @ResponseBody
    public Map submit(@RequestBody Map req){
        List<Map> list = (List<Map>)req.get("prescription_items");

        if(!prescriptionService.allCanReturn(list))
            return Response.error("错误，有ID不存在或药品不可退");

        list.forEach(map->{
            int id = (int)map.get("id");
            int amount = (int)map.get("amount");

            //System.out.println("id:"+id+" amount:"+amount);//

            PrescriptionItem prescriptionItem = prescriptionService.findPrescriptionItemById(id);
            //System.out.println("prescriptionItem:"+prescriptionItem);//

            if(prescriptionItem.getStatus().equals(PrescriptionStatus.PrescriptionItemTaken)){
                Drug drug = drugService.selectDrugById(prescriptionItem.getDrug_id());
                //System.out.println("drug:"+drug);//

                int stock = drug.getStock() + amount;
                //System.out.println("stock:"+stock);//
                drug.setStock(stock);
                drugService.updateDrug(drug);
                float cost = drug.getPrice()*amount;
                prescriptionService.returnDrug(prescriptionItem,amount,cost,req);
            }
        });

        return Response.ok();
    }
}
