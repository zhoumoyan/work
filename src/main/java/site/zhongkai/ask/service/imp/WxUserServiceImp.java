package site.zhongkai.ask.service.imp;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import site.zhongkai.ask.mapper.IWxUser;
import site.zhongkai.ask.entity.WxUser;
import site.zhongkai.ask.service.WxUserService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WxUserServiceImp extends ServiceImpl<IWxUser, WxUser> implements WxUserService {
    @Override
    public PageUtils getStudentList(Map<String, Object> params) {
        Page<WxUser> page=new Query<WxUser>(params).getPage();
        List<WxUser> list=baseMapper.getWxUsers(page,params);
        page.setRecords(list);
        return new PageUtils(page);
    }
}
