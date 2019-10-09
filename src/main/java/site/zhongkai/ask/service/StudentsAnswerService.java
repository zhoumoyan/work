package site.zhongkai.ask.service;

import com.baomidou.mybatisplus.service.IService;
import site.zhongkai.ask.entity.Studentsanswer;
import site.zhongkai.ask.utils.PageUtils;

import java.util.Map;

/**
 * service
 */
public interface StudentsAnswerService extends IService<Studentsanswer> {
    PageUtils getStudentsAnswerList(Map<String, Object> params);
}
