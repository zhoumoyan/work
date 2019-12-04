package site.zhongkai.ask.service.imp;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import site.zhongkai.ask.entity.UserVoucher;
import site.zhongkai.ask.mapper.IUserVoucherMapper;
import site.zhongkai.ask.service.IUserVoucherService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.Query;
import site.zhongkai.ask.vo.CountAnswerNum;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class UserVoucherServiceImpl extends ServiceImpl<IUserVoucherMapper, UserVoucher> implements IUserVoucherService {
    @Resource
    private IUserVoucherMapper userVoucherMapper;
    @Override
    public PageUtils findUserVoucher(Map<String, Object> params) {
        Page<UserVoucher> page=new Query<UserVoucher>(params).getPage();
        List<UserVoucher> list=baseMapper.getUserVouchers(page,params);
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public List<CountAnswerNum> countValue() {
       return userVoucherMapper.getNum();
    }

}
