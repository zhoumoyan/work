package site.zhongkai.ask.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import site.zhongkai.ask.entity.Campusmanage;
import site.zhongkai.ask.utils.PageUtils;

import java.util.Map;

/**
 * 校区dao
 */
public interface CampusmanageDao extends BaseMapper<Campusmanage> {
    PageUtils getCampusmanageList(Map<String, Object> params);
}
