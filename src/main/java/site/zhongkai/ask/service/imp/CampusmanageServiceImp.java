package site.zhongkai.ask.service.imp;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import site.zhongkai.ask.dao.CampusmanageDao;
import site.zhongkai.ask.entity.Campusmanage;
import site.zhongkai.ask.service.CampusmanageService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("campusmanageService")
public class CampusmanageServiceImp extends ServiceImpl<CampusmanageDao, Campusmanage> implements CampusmanageService {
    @Override
    public PageUtils getCampusmanageList(Map<String, Object> params) {
        String name="";
        try{
            name=params.get("name").toString();
        }catch (Exception ex){
            name="";
        }
        Page<Campusmanage> page = this.selectPage(
                new Query<Campusmanage>(params).getPage(),
                new EntityWrapper<Campusmanage>().like("name",name)
        );
        return new PageUtils(page);
    }
}
