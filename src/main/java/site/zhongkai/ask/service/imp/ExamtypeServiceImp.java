package site.zhongkai.ask.service.imp;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import site.zhongkai.ask.dao.ExamtypeDao;
import site.zhongkai.ask.entity.ExamType;
import site.zhongkai.ask.service.ExamtypeService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("examtypeService")
public class ExamtypeServiceImp extends ServiceImpl<ExamtypeDao, ExamType> implements ExamtypeService {
    @Override
    public PageUtils getExamtypeList(Map<String, Object> params) {
        String name="";
        try{
            name=params.get("name").toString();
        }catch (Exception ex){
            name="";
        }
        Page<ExamType> page = this.selectPage(
                new Query<ExamType>(params).getPage(),
                new EntityWrapper<ExamType>().like("name",name)
        );
        return new PageUtils(page);
    }
}
