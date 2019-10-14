package site.zhongkai.ask.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import site.zhongkai.ask.entity.ExamType;
import site.zhongkai.ask.utils.PageUtils;

import java.util.Map;

public interface IExamTypeMapper extends BaseMapper<ExamType> {
    PageUtils getExamtypeList(Map<String, Object> params);
}
