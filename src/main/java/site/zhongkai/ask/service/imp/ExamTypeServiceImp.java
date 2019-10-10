package site.zhongkai.ask.service.imp;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import site.zhongkai.ask.mapper.IExamType;
import site.zhongkai.ask.entity.ExamType;
import site.zhongkai.ask.service.ExamTypeService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ExamTypeServiceImp extends ServiceImpl<IExamType, ExamType> implements ExamTypeService {
    @Override
    public PageUtils getExamtypeList(Map<String, Object> params) {
        String name;
        try {
            name = params.get("typeName").toString();
        } catch (Exception ex) {
            name = "";
        }
        Page<ExamType> page = this.selectPage(
                new Query<ExamType>(params).getPage(),
                new EntityWrapper<ExamType>().like("type_name", name)
        );
        return new PageUtils(page);
    }
}
