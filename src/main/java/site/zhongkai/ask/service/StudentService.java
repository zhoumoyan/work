package site.zhongkai.ask.service;

import com.baomidou.mybatisplus.service.IService;
import site.zhongkai.ask.entity.Student;
import site.zhongkai.ask.utils.PageUtils;

import java.util.Map;

/**
 * 学生service
 */
public interface StudentService extends IService<Student> {
    PageUtils getStudentList(Map<String, Object> params);
}
