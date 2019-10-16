package site.zhongkai.ask.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import site.zhongkai.ask.entity.Manager;
import site.zhongkai.ask.entity.UserVoucher;

import java.util.List;
import java.util.Map;

public interface IUserVoucherMapper extends BaseMapper<UserVoucher> {
    //多表联查方式
    List<UserVoucher> getUserVouchers(Page<UserVoucher> page, Map<String, Object> params);
}
