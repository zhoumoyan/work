package site.zhongkai.ask.service.imp;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import site.zhongkai.ask.dao.StudentDao;
import site.zhongkai.ask.entity.Student;
import site.zhongkai.ask.service.StudentService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("studentService")
public class StudentServiceImp extends ServiceImpl<StudentDao, Student> implements StudentService {
    @Override
    public PageUtils getStudentList(Map<String, Object> params) {
        Page<Student> page=new Query<Student>(params).getPage();
        List<Student> list=baseMapper.getStudentList(page,params);
        page.setRecords(list);
        return new PageUtils(page);
    }
}
