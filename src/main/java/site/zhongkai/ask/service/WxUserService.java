package site.zhongkai.ask.service;

import com.baomidou.mybatisplus.service.IService;
import site.zhongkai.ask.entity.WxUser;
import site.zhongkai.ask.utils.PageUtils;

import java.util.Map;

public interface WxUserService extends IService<WxUser> {

    PageUtils getWxUserList(Map<String, Object> params);

}
