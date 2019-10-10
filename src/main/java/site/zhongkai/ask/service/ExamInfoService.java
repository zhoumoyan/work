package site.zhongkai.ask.service;

import com.baomidou.mybatisplus.service.IService;
import site.zhongkai.ask.entity.ExamInfo;
import site.zhongkai.ask.utils.PageUtils;

import java.util.Map;

public interface ExamInfoService extends IService<ExamInfo> {
    PageUtils getExamInfoList(Map<String, Object> params);
}
