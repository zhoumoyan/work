package site.zhongkai.ask.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import site.zhongkai.ask.entity.CampusPostVo;

import java.util.List;
import java.util.Map;

/**
 * 校区dao
 */
public interface PostmanageDao extends BaseMapper<CampusPostVo> {
    //多表联查方式
    List<CampusPostVo> getPostmanageList(Page<CampusPostVo> page, Map<String, Object> params);
}
