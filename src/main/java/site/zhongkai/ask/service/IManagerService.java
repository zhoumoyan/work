package site.zhongkai.ask.service;

import com.baomidou.mybatisplus.service.IService;
import site.zhongkai.ask.entity.Manager;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.ResponseResult;

import java.util.Date;
import java.util.Map;

public interface IManagerService extends IService<Manager> {
    PageUtils getManagers(Map<String, Object> params);

    ResponseResult setVoucherState(String voucherId, Date useTime);

    boolean changeAvatar(String uid, String avatar);
}
