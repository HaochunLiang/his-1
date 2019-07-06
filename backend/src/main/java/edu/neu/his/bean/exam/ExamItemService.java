package edu.neu.his.bean.exam;

import edu.neu.his.bean.nondrug.NonDrugChargeItem;
import edu.neu.his.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamItemService {
    @Autowired
    ExamItemMapper examItemMapper;

    @Transactional
    public int insert(ExamItem examItem){
        return examItemMapper.insert(examItem);
    }

    @Transactional
    public int deleteById(Integer id){
        return examItemMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public ExamItem selectByDetail(Integer nonDrugId, Integer examId){
        return examItemMapper.selectOneByDetail(nonDrugId, examId);
    }


    @Transactional
    public boolean register(List<Integer> itemIds){
        for (Integer itemId : itemIds) {
            ExamItem examItem = examItemMapper.selectByPrimaryKey(itemId);
            if(!hasPay(examItem)){
                return false;
            }else {
                examItem.setStatus(Common.YIDENGJI);
                examItemMapper.updateByPrimaryKey(examItem);
            }
        }
        return true;
    }

    private boolean hasPay(ExamItem item){
        if(item == null)
            return false;

        if (Common.WEIDENGJI.equals(item.getStatus())){
            return true;
        }

        return false;
    }

    public ExamItem selectByPrimaryKey(int id) {
        return examItemMapper.selectByPrimaryKey(id);
    }

    public ExamItem selectByItemId(int id) {
        return examItemMapper.selectByItemId(id);
    }

    public void update(ExamItem examItem){
        examItemMapper.updateByPrimaryKey(examItem);
    }
}
