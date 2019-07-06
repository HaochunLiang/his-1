package edu.neu.his.bean.drug;

import edu.neu.his.auto.AutoDrugMapper;
import edu.neu.his.util.Common;
import edu.neu.his.util.ExcelImportation;
import edu.neu.his.util.Importable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class DrugService implements Importable<Drug> {

    @Autowired
    AutoDrugMapper autoDrugMapper;

    @Autowired
    DrugMapper drugMapper;

    @Transactional
    public int insertDrug(Drug drug){
        drugMapper.insert(drug);
        return drug.getId();
    }

    @Transactional
    public int getExpenseClassificationId(Drug drug){
        switch (drug.getType()){
            case Common.ZHONGCAOYAOTYPE:
                return 15;
            case Common.XIYAOTYPE:
                return 13;
            case Common.ZHONGCHENGYAOTYPE:
                return 14;
        }
        return 0;
    }

    @Transactional
    public boolean allItemValid(List<Map> drugList) {
        for(Map map:drugList){
            Drug drug = autoDrugMapper.selectByPrimaryKey((Integer) map.get("drug_id"));
            if(drug == null) return false;
        }
        return true;
    }

    @Transactional
    public boolean existDrug(int id){
        if(autoDrugMapper.selectByPrimaryKey(id)==null)
            return false;
        else return true;
    }

    @Transactional
    public int deleteDrug(int id){
        return autoDrugMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public int updateDrug(Drug drug){
        return autoDrugMapper.updateByPrimaryKey(drug);
    }

    @Transactional
    public List<Drug> selectAllDrug(){
        return autoDrugMapper.selectAll();
    }

    @Transactional
    public List<Drug> selectDrugByName(String name){
        return drugMapper.selectByName(name);
    }

    @Transactional
    public boolean importFromFile(InputStream inputStream) {
        try {
            ExcelImportation excel = new ExcelImportation(inputStream, Drug.class, drugMapper);
            excel.setColumnFields("id", "code", "name", "format", "unit", "manufacturer", "dosage_form", "type", "price", "pinyin", "stock");
            excel.skipLine(2);
            Map<String, Function<String, ?>> preFunctionMap = excel.getPreFunctionMap();
            preFunctionMap.put("stock", (i)-> 100);
            preFunctionMap.put("type", s->{
                switch (Integer.parseInt(s)){
                    case 101:
                        return Common.XIYAOTYPE;
                    case 103:
                        return Common.ZHONGCAOYAOTYPE;
                    case 102:
                        return Common.ZHONGCHENGYAOTYPE;
                    default:
                        return "";
                }
            });
            excel.exec();
            return true;
        } catch (Exception e)  {
            return false;
        }

    }
    
    public Drug selectDrugById(int id){
        return autoDrugMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insert(Drug instance) {
        return autoDrugMapper.insert(instance);
    }
}
