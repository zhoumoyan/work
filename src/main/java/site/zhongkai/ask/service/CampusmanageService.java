package site.zhongkai.ask.service;

import com.baomidou.mybatisplus.service.IService;
import site.zhongkai.ask.entity.Campusmanage;
import site.zhongkai.ask.utils.PageUtils;

import java.util.Map;

/**
 * 校区的service
 */
public interface CampusmanageService extends IService<Campusmanage> {
    PageUtils getCampusmanageList(Map<String, Object> params);
}
