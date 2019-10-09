package site.zhongkai.ask.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import site.zhongkai.ask.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * 学生dao
 */
public interface StudentDao extends BaseMapper<Student> {
    //多表联查方式
    List<Student> getStudentList(Page<Student> page, Map<String, Object> params);
}
