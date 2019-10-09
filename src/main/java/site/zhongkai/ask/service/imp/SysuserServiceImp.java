package site.zhongkai.ask.service.imp;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import site.zhongkai.ask.dao.SysuserDao;
import site.zhongkai.ask.entity.Manager;
import site.zhongkai.ask.entity.Sysuser;
import site.zhongkai.ask.service.SysuserService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysuserService")
public class SysuserServiceImp extends ServiceImpl<SysuserDao, Manager> implements SysuserService {
    @Override
    public PageUtils getSysuserList(Map<String, Object> params) {
        Page<Sysuser> page=new Query<Sysuser>(params).getPage();
        List<Sysuser> list=baseMapper.getSysuserList(page,params);
        page.setRecords(list);
        return new PageUtils(page);
    }
}
