package site.zhongkai.ask.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import site.zhongkai.ask.entity.UserVoucher;
import site.zhongkai.ask.vo.CountAnswerNum;
import site.zhongkai.ask.vo.CountNum;
import site.zhongkai.ask.vo.CountVoucherNum;


import java.util.List;
import java.util.Map;

public interface IQueryMapper {

    List<CountVoucherNum> getVoucherNum(Page<CountVoucherNum> page, Map<String, Object> params);

    List<CountAnswerNum> getAnswersNum(Page<CountAnswerNum> page, Map<String, Object> params);

    List<CountNum> getNum(@Param("openId")String openId);
}
