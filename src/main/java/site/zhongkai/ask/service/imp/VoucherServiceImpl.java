package site.zhongkai.ask.service.imp;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import site.zhongkai.ask.config.Constant;
import site.zhongkai.ask.config.Response;
import site.zhongkai.ask.entity.SysVoucher;
import site.zhongkai.ask.entity.UserVoucher;
import site.zhongkai.ask.entity.WxUser;
import site.zhongkai.ask.mapper.IUserVoucherMapper;
import site.zhongkai.ask.mapper.ISysVoucherMapper;
import site.zhongkai.ask.mapper.IWxUserMapper;
import site.zhongkai.ask.service.IVoucherService;
import site.zhongkai.ask.utils.ResponseResult;
import site.zhongkai.ask.vo.GetVoucher;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class VoucherServiceImpl implements IVoucherService {

    @Resource
    private IWxUserMapper wxUserMapper;
    @Resource
    private ISysVoucherMapper voucherMapper;
    @Resource
    private IUserVoucherMapper userVoucherMapper;

    @Override
    public ResponseResult findVoucher(String openId, String operateType) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        if (operateType == null){
            List<SysVoucher> sysVouchers = voucherMapper.selectList(new EntityWrapper<SysVoucher>().eq("state", 0).ge("valid_time", new Date()));
            for (SysVoucher sysVoucher : sysVouchers) {
                sysVoucher.setValidTimeFormat((simpleDateFormat).format(sysVoucher.getValidTime()));
            }
            WxUser wxUser = wxUserMapper.selectOne(new WxUser(openId));
            return new ResponseResult<>(true, Constant.STATE_SUCCESS, Constant.EXPLAIN_SUCCESS, new GetVoucher(wxUser.getGrade(), sysVouchers));
        } else {
            List<UserVoucher> userVouchers = userVoucherMapper.selectList(new EntityWrapper<UserVoucher>().eq("open_id", openId).orderBy("state", true));
            for (UserVoucher userVoucher : userVouchers) {
                userVoucher.setValidTimeFormat((simpleDateFormat).format(userVoucher.getValidTime()));
            }
            return new ResponseResult<>(true, Constant.STATE_SUCCESS, Constant.EXPLAIN_SUCCESS, userVouchers);
        }
    }

    @Override
    public ResponseResult exchangeVoucher(String openId, String voucherId) {
        List<SysVoucher> sysVouchers = voucherMapper.selectList(new EntityWrapper<SysVoucher>().eq("state", 0).ge("valid_time", new Date()));
        for (SysVoucher sysVoucher : sysVouchers) {
            if (sysVoucher.getId().equals(voucherId)) {
                WxUser wxUser = wxUserMapper.selectOne(new WxUser(openId));
                if (wxUser.getGrade() < sysVoucher.getConsumeScore()) return Response.getErrorResult(40003);
                UserVoucher userVoucher = new UserVoucher(openId, sysVoucher.getMoney(), voucherId, sysVoucher.getVoucherExplain(), sysVoucher.getConsumeExplain(), sysVoucher.getConsumeScore(), sysVoucher.getValidTime(), Constant.VOUCHER_NORMAL, new Date());
                userVoucherMapper.insert(userVoucher);
                wxUserMapper.updateForSet("grade=grade-" + sysVoucher.getConsumeScore(), new EntityWrapper<WxUser>().eq("open_id", openId));
                return Response.getSuccessResult(20000);
            }
        }
        return Response.getErrorResult(40002);
    }
}
