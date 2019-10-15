package site.zhongkai.ask.service;

import site.zhongkai.ask.utils.ResponseResult;

public interface IVoucherService {

    // 获取代金卷
    ResponseResult findVoucher(String openId, String operateType);

    // 兑换代金卷
    ResponseResult exchangeVoucher(String openId, String voucherId);

}
