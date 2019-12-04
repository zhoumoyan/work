package site.zhongkai.ask.service;

import com.baomidou.mybatisplus.service.IService;
import org.apache.poi.ss.formula.functions.T;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.vo.CountNum;

import java.util.List;
import java.util.Map;

public interface IQueryService  {

    PageUtils countVoucher(Map<String, Object> params);


    PageUtils countAnswer(Map<String, Object> params);

    List<CountNum> countNum(String openId);
}
