package site.zhongkai.ask.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import site.zhongkai.ask.entity.ExamType;
import site.zhongkai.ask.utils.PageUtils;

import java.util.Map;

/**
 * 校区dao
 */
public interface ExamtypeDao extends BaseMapper<ExamType> {
    PageUtils getExamtypeList(Map<String, Object> params);
}
