package site.zhongkai.ask.service;

import com.baomidou.mybatisplus.service.IService;
import site.zhongkai.ask.entity.Manager;
import site.zhongkai.ask.entity.Sysuser;
import site.zhongkai.ask.utils.PageUtils;

import java.util.Map;

/**
 * 后台管理service
 */
public interface SysuserService extends IService<Manager> {
    PageUtils getSysuserList(Map<String, Object> params);
}
