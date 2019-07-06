package edu.neu.his.bean.daily;

import edu.neu.his.bean.registration.RegistrationDailyCollectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实现处理数据库中daily_collect、daily_detail表的相关操作
 *
 * @author 王婧怡
 * @version 1.0
 */
@Service
public class DailyCollectService {
    @Autowired
    private RegistrationDailyCollectMapper registrationDailyCollectMapper;

    @Autowired
    private DailyCollectMapper dailyCollectMapper;

    @Autowired
    private DailyDetailMapper dailyDetailMapper;

    /**
     * 根据用户id查找其相关的所有日结记录
     * @param uid 操作用户id
     * @return 查找到的日结记录列表
     */
    @Transactional
    public List<DailyCollect> findDailyCollectByUid(int uid){
        return registrationDailyCollectMapper.findDailyCollectByUid(uid);
    }

    /**
     * 通过日结记录id查找所有相关的日结详细记录的列表
     * @param daily_collect_id 日结记录id
     * @return 返回该日结记录的所有日结详细记录的列表
     */
    @Transactional
    public List<DailyDetail> findDailyDetailByCollectId(int daily_collect_id){
        return registrationDailyCollectMapper.findDailyDetailById(daily_collect_id);
    }

    /**
     * 向数据库中插入日结记录
     * @param dailyCollect 要插入数据库中的日结记录
     * @return 返回该日结记录插入数据库后自动生成的id
     */
    @Transactional
    public int insertDailyCollect(DailyCollect dailyCollect){
        dailyCollectMapper.insert(dailyCollect);
        return dailyCollect.getId();
    }

    /**
     * 向数据库中插入日结详细记录
     * @param dailyDetail 要插入数据库中的日结详细记录
     * @return 返回该日结详细记录插入数据库后自动生成的id
     */
    @Transactional
    public int insertDailyDetail(DailyDetail dailyDetail){
        dailyDetailMapper.insert(dailyDetail);
        return dailyDetail.getId();
    }

    /**
     * 根据id查找数据库中相应的日结记录
     * @param id 要查找的日结记录id
     * @return 返回查找到的相应日结记录
     */
    @Transactional
    public DailyCollect findDailyCollectById(int id){
        return dailyCollectMapper.selectByPrimaryKey(id);
    }
}
