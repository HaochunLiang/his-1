package edu.neu.his.bean.expenseClassification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ExpenseClassificationService {
    @Autowired
    private ExpenseClassificationMapper expenseClassificationMapper;

    @Transactional
    public int findClassificationIdByName(String fee_name){
        return expenseClassificationMapper.findClassificationIdByName(fee_name);
    }

    @Transactional
    public String findClassificationById(int id){
        return expenseClassificationMapper.findClassificationById(id);
    }

    @Transactional
    public void updateExpenseClassification(ExpenseClassification expenseClass) {
        expenseClassificationMapper.update(expenseClass);
    }

    @Transactional
    public void insertExpenseClass(ExpenseClassification expenseClass) {
        expenseClassificationMapper.insert(expenseClass);
    }

    @Transactional
    public List<ExpenseClassification> findAll() {
        return expenseClassificationMapper.findAll();
    }

    @Transactional
    public void deleteExpenseClassification(int id) {
        expenseClassificationMapper.delete(id);
    }

    @Transactional
    public boolean exist(ExpenseClassification expenseClassification) {
        return expenseClassificationMapper.checkIdExistNums(expenseClassification)==1 || expenseClassificationMapper.checkNameExistNums(expenseClassification)==1;
    }

    @Transactional
    public boolean canDelete(int id) {
        return expenseClassificationMapper.checkId(id)==1;
    }

    @Transactional
    public String selectById(int id){
        return expenseClassificationMapper.findClassificationById(id);
    }

}
