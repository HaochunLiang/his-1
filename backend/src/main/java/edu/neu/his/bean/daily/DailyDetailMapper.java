package edu.neu.his.bean.daily;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *该类对数据库中的daily_detail表进行数据持久化操作
 *
 * @author 王婧怡
 * @version 1.0
 */
@Mapper
@Component(value = "DailyDetailMapper")
public interface DailyDetailMapper {
    /**
     *从daily_detail表中根据id删除一条记录
     * @param id 需要删除的DailyDetail对象的id
     * @return 返回删除的行数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 向daily_detail表中插入一条数据
     * @param record 需要插入表中的DailyDetail对象
     * @return 返回数据库为插入的对象自动生成的id
     */
    int insert(DailyDetail record);

    /**
     * 根据主键id查找相对应的daily_detail表中的记录
     * @param id daily_detail表的主键，代表日结详细记录的流水号
     * @return 将查找到的记录生成DailyDetail对象返回
     */
    DailyDetail selectByPrimaryKey(Integer id);

    /**
     *查找daily_detail表中所有的记录
     * @return 返回daily_detail表中所有记录生成的DailyDetail对象的列表
     */
    List<DailyDetail> selectAll();

    /**
     *根据id更新daily_detail表中相应的记录
     * @param record 需要更新的DailyDetail对象
     * @return 返回更新的行数
     */
    int updateByPrimaryKey(DailyDetail record);
}