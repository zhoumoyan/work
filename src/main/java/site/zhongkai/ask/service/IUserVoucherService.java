package site.zhongkai.ask.service;

import com.baomidou.mybatisplus.service.IService;
import site.zhongkai.ask.entity.UserVoucher;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.vo.CountAnswerNum;

import java.util.List;
import java.util.Map;

public interface IUserVoucherService extends IService<UserVoucher> {

    // 获取用户卡券
    PageUtils findUserVoucher(Map<String, Object> params);

    List<CountAnswerNum> countValue();
}
