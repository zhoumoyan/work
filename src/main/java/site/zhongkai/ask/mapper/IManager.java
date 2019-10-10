package site.zhongkai.ask.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import site.zhongkai.ask.entity.Manager;

import java.util.List;
import java.util.Map;

public interface IManager extends BaseMapper<Manager> {
    //多表联查方式
    List<Manager> getManagers(Page<Manager> page, Map<String, Object> params);
}
