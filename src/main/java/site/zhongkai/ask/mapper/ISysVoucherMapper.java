package site.zhongkai.ask.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import site.zhongkai.ask.entity.SysVoucher;
import site.zhongkai.ask.entity.WxUser;

import java.util.List;
import java.util.Map;

public interface ISysVoucherMapper extends BaseMapper<SysVoucher> {
    //多表联查方式
    List<SysVoucher> getSysVouchers(Page<SysVoucher> page, Map<String, Object> params);
}
