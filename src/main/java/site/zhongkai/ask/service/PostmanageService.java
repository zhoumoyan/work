package site.zhongkai.ask.service;

import com.baomidou.mybatisplus.service.IService;
import site.zhongkai.ask.entity.CampusPostVo;
import site.zhongkai.ask.utils.PageUtils;

import java.util.Map;

/**
 * 岗位的service
 */
public interface PostmanageService extends IService<CampusPostVo> {
    PageUtils getPostmanageList(Map<String, Object> params);
}
