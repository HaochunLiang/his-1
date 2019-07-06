package edu.neu.his.bean.examTemplate;

import edu.neu.his.bean.user.User;
import edu.neu.his.auto.ExamTemplateItemMapper;
import edu.neu.his.auto.ExamTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamTemplateService {

    @Autowired
    ExamTemplateMapper examTemplateMapper;

    @Autowired
    ExamTemplateItemMapper examTemplateItemMapper;

    @Transactional
    public List<ExamTemplate> findAll(User user){
        return examTemplateMapper.selectAll().stream().filter(item->{
            switch (item.getDisplay_type()){
                case 0:
                    if(item.getUser_id() == user.getUid()){
                        return true;
                    }
                    break;
                case 1:
                    if(item.getDepartment_id() == user.getDepartment_id()){
                        return true;
                    }
                    break;
                case 2:
                    return true;
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Transactional
    public int insert(ExamTemplate examTemplate){
        return examTemplateMapper.insert(examTemplate);
    }

    @Transactional
    public ExamTemplate selectById(int id){
        return examTemplateMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public ExamTemplate selectByName(String name){
        return examTemplateMapper.selectByName(name);
    }

    @Transactional
    public void deleteByTemplateId(int id) {
        examTemplateMapper.deleteByPrimaryKey(id);
        examTemplateMapper.deleteItemByPrimaryKey(id);
    }

    @Transactional
    public void deleteItemByPrimaryKey(int id){
        examTemplateMapper.deleteItemByPrimaryKey(id);
    }

    @Transactional
    public List<Integer> getNonDrugItemIdListById(Integer id){
        List<ExamTemplateItem> itemList = examTemplateItemMapper.selectByTemplateId(id);
        return itemList.stream().map(ExamTemplateItem::getNon_drug_item_id).collect(Collectors.toList());
    }

    public Integer updateById(ExamTemplate examTemplate) {
        return examTemplateMapper.updateByPrimaryKey(examTemplate);
    }
}
