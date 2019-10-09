package site.zhongkai.ask.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import site.zhongkai.ask.entity.ExamInfo;

import java.util.List;
import java.util.Map;

/**
 * 校区dao
 */
public interface ExamInfomanageDao extends BaseMapper<ExamInfo> {
    //多表联查方式
    List<ExamInfo> getExamInfoList(Page<ExamInfo> page, Map<String, Object> params);
}
