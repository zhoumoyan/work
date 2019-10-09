package site.zhongkai.ask.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import site.zhongkai.ask.entity.Manager;
import site.zhongkai.ask.entity.Sysuser;

import java.util.List;
import java.util.Map;

/**
 * 学生dao
 */
public interface SysuserDao extends BaseMapper<Manager> {
    //多表联查方式
    List<Sysuser> getSysuserList(Page<Sysuser> page, Map<String, Object> params);
}
