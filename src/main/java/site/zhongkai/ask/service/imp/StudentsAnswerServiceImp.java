package site.zhongkai.ask.service.imp;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import site.zhongkai.ask.dao.StudentsAnswerDao;
import site.zhongkai.ask.entity.Studentsanswer;
import site.zhongkai.ask.service.StudentsAnswerService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("studentsAnswerService")
public class StudentsAnswerServiceImp extends ServiceImpl<StudentsAnswerDao, Studentsanswer> implements StudentsAnswerService {
    @Override
    public PageUtils getStudentsAnswerList(Map<String, Object> params) {
        Page<Studentsanswer> page=new Query<Studentsanswer>(params).getPage();
        List<Studentsanswer> list=baseMapper.getStudentsAnswerList(page,params);
        page.setRecords(list);
        return new PageUtils(page);
    }
}
