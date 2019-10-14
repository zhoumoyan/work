package site.zhongkai.ask.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import site.zhongkai.ask.entity.AnswerLog;

import java.util.List;

public interface IAnswerLogMapper extends BaseMapper<AnswerLog> {

    List<AnswerLog> selectTodayList(@Param("openId") String openId);

}
