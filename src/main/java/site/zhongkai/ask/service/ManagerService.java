package site.zhongkai.ask.service;

import com.baomidou.mybatisplus.service.IService;
import site.zhongkai.ask.entity.Manager;
import site.zhongkai.ask.utils.PageUtils;

import java.util.Map;

public interface ManagerService extends IService<Manager> {
    PageUtils getManagers(Map<String, Object> params);
}
