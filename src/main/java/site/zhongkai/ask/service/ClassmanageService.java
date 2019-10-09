package site.zhongkai.ask.service;

import com.baomidou.mybatisplus.service.IService;
import site.zhongkai.ask.entity.Classinfo;
import site.zhongkai.ask.utils.PageUtils;

import java.util.Map;

/**
 * 班级的service
 */
public interface ClassmanageService extends IService<Classinfo> {
    PageUtils getClassmanageList(Map<String, Object> params);
}
