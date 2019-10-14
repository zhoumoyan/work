package site.zhongkai.ask.service.imp;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import site.zhongkai.ask.mapper.IManagerMapper;
import site.zhongkai.ask.entity.Manager;
import site.zhongkai.ask.service.ManagerService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ManagerServiceImp extends ServiceImpl<IManagerMapper, Manager> implements ManagerService {
    @Override
    public PageUtils getManagers(Map<String, Object> params) {
        Page<Manager> page=new Query<Manager>(params).getPage();
        List<Manager> list=baseMapper.getManagers(page,params);
        page.setRecords(list);
        return new PageUtils(page);
    }
}
