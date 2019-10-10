package site.zhongkai.ask.service;

import com.baomidou.mybatisplus.service.IService;
import site.zhongkai.ask.entity.ExamType;
import site.zhongkai.ask.utils.PageUtils;

import java.util.Map;

public interface ExamTypeService extends IService<ExamType> {
    PageUtils getExamtypeList(Map<String, Object> params);
}
