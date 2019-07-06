
package edu.neu.his.bean.nondrug;

import edu.neu.his.bean.exam.Exam;
import edu.neu.his.bean.expenseClassification.ExpenseClassification;
import edu.neu.his.util.Common;
import edu.neu.his.util.ExcelImportation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class NonDrugChargeService {
    @Autowired
    private NonDrugChargeItemMapper nonDrugChargeItemMapper;

    @Autowired
    private edu.neu.his.auto.NonDrugChargeItemMapper autoNonDrugChargeItemMapper;

    @Transactional
    public void updateNonDrugCharge(NonDrugChargeItem nonDrugCharge) {
        nonDrugChargeItemMapper.update(nonDrugCharge);
    }

    @Transactional
    public NonDrugChargeItem findNonDrugChargeByName(String name) {
        return nonDrugChargeItemMapper.findByName(name);
    }

    @Transactional
    public void insertNonDrugCharge(NonDrugChargeItem nonDrugCharge) {
        nonDrugChargeItemMapper.insert(nonDrugCharge);
    }

    @Transactional
    public List<NonDrugChargeItem> findAll() {
        return nonDrugChargeItemMapper.findAll();
    }

    @Transactional
    public List<ExpenseClassification> findAllExpenseClassificationNames() {return nonDrugChargeItemMapper.findAllExpenseClassificationNames();}

    @Transactional
    public void deleteNonDrugCharge(int id) {
        nonDrugChargeItemMapper.deleteNonDrugCharge(id);
    }

    @Transactional
    public int findExpenseClassificationIdByName(String fee_name) {
        int id = 0;
        try {
            id = nonDrugChargeItemMapper.findExpenseClassificationIdByName(fee_name);
        } catch (Exception e) {
            id = -1;
        }
        return id;
    }

    @Transactional
    public boolean exist(NonDrugChargeItem nonDrugChargeItem) {
        if(nonDrugChargeItem==null) return false;
        return nonDrugChargeItemMapper.checkIdExistNums(nonDrugChargeItem)==1;
    }

    @Transactional
    public boolean canDelete(int id) {
        return nonDrugChargeItemMapper.checkId(id)==1;
    }

    @Transactional
    public NonDrugChargeItem selectById(int id){
        return autoNonDrugChargeItemMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public boolean importFromFile(InputStream inputStream) {
        try {
            ExcelImportation excel = new ExcelImportation(inputStream, NonDrugChargeItem.class, nonDrugChargeItemMapper);
            excel.setColumnFields(null, "id", "name", "format", "fee", "expense_classification_id", "department_id", "pinyin");
            //((Map<String, Function<String, ?>>)excel.getPreFunctionMap()).put("classification_id", departmentMapper::findClassificationIdByName);
            excel.exec();
            return true;
        } catch (Exception e)  {
            return false;
        }
    }
}