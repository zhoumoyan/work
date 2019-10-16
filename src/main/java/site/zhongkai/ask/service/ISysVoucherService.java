package site.zhongkai.ask.service;

import com.baomidou.mybatisplus.service.IService;
import site.zhongkai.ask.entity.SysVoucher;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.ResponseResult;

import java.util.Map;

public interface ISysVoucherService extends IService<SysVoucher> {

    // 获取卡券
    ResponseResult findVoucher(String openId, String operateType);

    // 兑换卡券
    ResponseResult exchangeVoucher(String openId, String voucherId);

    // 获取系统卡券
    PageUtils findSysVoucher(Map<String, Object> params);

}
