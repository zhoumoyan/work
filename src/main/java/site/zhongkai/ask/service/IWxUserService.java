package site.zhongkai.ask.service;

import com.baomidou.mybatisplus.service.IService;
import site.zhongkai.ask.entity.WxUser;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.ResponseResult;

import java.util.Map;

public interface IWxUserService extends IService<WxUser> {

    PageUtils getWxUserList(Map<String, Object> params);

    // 兑换充电
    ResponseResult exchangeCharge(String openId);

}
