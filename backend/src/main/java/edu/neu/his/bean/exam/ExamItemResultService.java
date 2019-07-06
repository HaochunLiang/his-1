package edu.neu.his.bean.exam;

import edu.neu.his.auto.ExamItemResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamItemResultService {

    @Autowired
    ExamItemResultMapper examItemResultMapper;

    @Transactional
    public void insertOrUpdate(ExamItemResult examItemResult) {
        ExamItemResult searchResult = examItemResultMapper.selectByExamItemId(examItemResult.getExam_item_id());
        if (searchResult != null){
            examItemResult.setId(searchResult.getId());
            examItemResultMapper.updateByPrimaryKey(examItemResult);
        }else {
            examItemResultMapper.insert(examItemResult);
        }
    }

    @Transactional
    public ExamItemResult selectByExamItemId(int id){
        return examItemResultMapper.selectByExamItemId(id);
    }

    @Transactional
    public List<ExamItemResult> allExamItemResult() {return examItemResultMapper.selectAll();}

}
