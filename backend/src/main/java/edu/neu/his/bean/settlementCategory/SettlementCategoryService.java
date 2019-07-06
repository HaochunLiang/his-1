package edu.neu.his.bean.settlementCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SettlementCategoryService {
    @Autowired
    SettlementCategoryMapper settlementCategoryMapper;

    @Transactional
    public void addSettlementCategory(SettlementCategory settlementCategory) {
        settlementCategoryMapper.addSettlementCategory(settlementCategory);
    }

    @Transactional
    public void deleteSettlementCategoryByName(String name) {
        settlementCategoryMapper.deleteSettlementCategoryByName(name);
    }

    @Transactional
    public void deleteSettlementCategoryById(int id) {
        settlementCategoryMapper.deleteSettlementCategoryById(id);
    }

    @Transactional
    public void update(SettlementCategory settlementCategory) {
        settlementCategoryMapper.updateSettlementCategory(settlementCategory);
    }

    @Transactional
    public List<SettlementCategory> findAll() {
        return settlementCategoryMapper.findAll();
    }

    @Transactional
    public boolean canUpdate(SettlementCategory settlementCategory) {
        int id_num = settlementCategoryMapper.checkIdExists(settlementCategory.getId());
        int name_num = settlementCategoryMapper.checkNameExists(settlementCategory.getName());
        if(id_num==0 || name_num>1 || id_num>1)
            return false;
        else if(name_num==1){
            SettlementCategory d = settlementCategoryMapper.findByName(settlementCategory.getName());
            if(d.getId() != settlementCategory.getId())
                return false;
            else
                return true;
        } else
            return true;
    }

    @Transactional
    public boolean canInsert(SettlementCategory settlementCategory) {
        int id_num = settlementCategoryMapper.checkIdExists(settlementCategory.getId());
        int name_num = settlementCategoryMapper.checkNameExists(settlementCategory.getName());
        if(id_num>=1 || name_num>=1)
            return false;
        else
            return true;
    }

    @Transactional
    public int canDelete(int id) {
        return settlementCategoryMapper.checkIdExists(id);//0,不能删
    }
}
