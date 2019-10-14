package site.zhongkai.ask.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import site.zhongkai.ask.entity.WxUser;

import java.util.List;
import java.util.Map;

public interface IWxUserMapper extends BaseMapper<WxUser> {
    //多表联查方式
    List<WxUser> getWxUsers(Page<WxUser> page, Map<String, Object> params);
}
