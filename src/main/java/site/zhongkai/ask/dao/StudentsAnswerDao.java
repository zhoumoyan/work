package site.zhongkai.ask.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import site.zhongkai.ask.entity.Studentsanswer;

import java.util.List;
import java.util.Map;

/**
 * dao
 */
public interface StudentsAnswerDao extends BaseMapper<Studentsanswer> {
    //多表联查方式
    List<Studentsanswer> getStudentsAnswerList(Page<Studentsanswer> page, Map<String, Object> params);
}
