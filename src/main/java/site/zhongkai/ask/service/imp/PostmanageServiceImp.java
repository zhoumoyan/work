package site.zhongkai.ask.service.imp;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import site.zhongkai.ask.dao.PostmanageDao;
import site.zhongkai.ask.entity.CampusPostVo;
import site.zhongkai.ask.service.PostmanageService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("postmanageService")
public class PostmanageServiceImp extends ServiceImpl<PostmanageDao, CampusPostVo> implements PostmanageService {
    @Override
    public PageUtils getPostmanageList(Map<String, Object> params) {
        Page<CampusPostVo> page=new Query<CampusPostVo>(params).getPage();
        List<CampusPostVo> list=baseMapper.getPostmanageList(page,params);
        page.setRecords(list);
        return new PageUtils(page);
    }
}
