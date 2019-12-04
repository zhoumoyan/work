package site.zhongkai.ask.service.imp;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import site.zhongkai.ask.mapper.IQueryMapper;
import site.zhongkai.ask.service.IQueryService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.Query;
import site.zhongkai.ask.vo.CountAnswerNum;
import site.zhongkai.ask.vo.CountNum;
import site.zhongkai.ask.vo.CountVoucherNum;


import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class QueryServiceImp  implements IQueryService {

    @Resource
    private IQueryMapper queryMapper;

    @Override
    public PageUtils countVoucher(Map<String, Object> params) {

        Page<CountVoucherNum> page=new Query<CountVoucherNum>(params).getPage();
        List<CountVoucherNum> list=queryMapper.getVoucherNum(page,params);
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public PageUtils countAnswer(Map<String, Object> params) {
        Page<CountAnswerNum> page=new Query<CountAnswerNum>(params).getPage();
        List<CountAnswerNum> list=queryMapper.getAnswersNum(page,params);
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public List<CountNum> countNum(String openId) {
        return  queryMapper.getNum(openId);
    }

}
