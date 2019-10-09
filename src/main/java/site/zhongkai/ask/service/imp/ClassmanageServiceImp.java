package site.zhongkai.ask.service.imp;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import site.zhongkai.ask.dao.ClassmanageDao;
import site.zhongkai.ask.entity.Classinfo;
import site.zhongkai.ask.service.ClassmanageService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("classmanageService")
public class ClassmanageServiceImp extends ServiceImpl<ClassmanageDao, Classinfo> implements ClassmanageService {
    @Override
    public PageUtils getClassmanageList(Map<String, Object> params) {
        Page<Classinfo> page=new Query<Classinfo>(params).getPage();
        List<Classinfo> list=baseMapper.getClassmanageList(page,params);
        page.setRecords(list);
        return new PageUtils(page);
    }
}
