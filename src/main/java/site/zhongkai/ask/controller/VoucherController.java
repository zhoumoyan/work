package site.zhongkai.ask.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.zhongkai.ask.config.Constant;
import site.zhongkai.ask.entity.ExamType;
import site.zhongkai.ask.entity.Voucher;
import site.zhongkai.ask.entity.WxUser;
import site.zhongkai.ask.mapper.IVoucherMapper;
import site.zhongkai.ask.mapper.IWxUserMapper;
import site.zhongkai.ask.service.WxUserService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.R;
import site.zhongkai.ask.utils.ResponseResult;
import site.zhongkai.ask.vo.GetVoucher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/voucher")
public class VoucherController {

    @Resource
    private IVoucherMapper voucherMapper;
    @Resource
    private IWxUserMapper wxUserMapper;

    // 获取代金卷
    @PostMapping("/get_all")
    @ResponseBody
    public String getAllVoucher(@RequestParam("openId") String openId) {
        List<Voucher> vouchers = voucherMapper.selectList(new EntityWrapper<Voucher>().eq("state",0));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        for (Voucher voucher : vouchers) {
            (voucher).setValidTimeFormat((simpleDateFormat).format((voucher).getValidTime()));
        }
        WxUser wxUser = wxUserMapper.selectOne(new WxUser(openId));
        if (wxUser.getGrade() == null) wxUser.setGrade(0);
        return JSON.toJSONString(new ResponseResult<>(true, Constant.STATE_SUCCESS, Constant.EXPLAIN_SUCCESS, new GetVoucher(wxUser.getGrade(), vouchers)));
    }

}
