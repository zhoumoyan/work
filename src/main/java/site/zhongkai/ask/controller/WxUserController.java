package site.zhongkai.ask.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.zhongkai.ask.entity.WxUser;
import site.zhongkai.ask.service.ISysVoucherService;
import site.zhongkai.ask.service.IWxUserService;
import site.zhongkai.ask.utils.JudgeUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Log4j2
@Controller
@RequestMapping("/wx_user")
public class WxUserController {

    private static final String USE_SECRETKEY = "zk123456";
    @Resource
    private IWxUserService wxUserService;
    @Resource
    private ISysVoucherService sysVoucherService;

    //用户登录
    @GetMapping("/login")
    public ModelAndView handleLogin(String openId, String nickName, String secretKey, ModelAndView modelAndView, HttpServletRequest request) {
        String md5String = DigestUtils.md5DigestAsHex((openId + USE_SECRETKEY).getBytes());
        if (!md5String.equalsIgnoreCase(secretKey) || JudgeUtils.isAnyEmpty(openId, nickName, secretKey)) {
            log.error("收到来自[" + request.getRemoteAddr() + "]的非法访问-[openId=" + openId + ",nickName=" + nickName + ",secretKey=" + secretKey + "]");
            return null;
        }
        log.info("收到来自[" + request.getRemoteAddr() + "]的访问请求-[OpenID=" + openId + ",微信昵称=" + nickName + ",Key=" + secretKey + "]");
        WxUser wxUser = new WxUser(openId, nickName);
        modelAndView.addObject("openId", wxUser.getOpenId());
        WxUser user = wxUserService.selectOne(new EntityWrapper<WxUser>().eq("open_id", wxUser.getOpenId()));
        if (user == null) {
            wxUser.setGrade(0);
            wxUserService.insert(wxUser);
        } else {
            user.setNickName(nickName).setActiveTime(new Date());
            wxUserService.updateById(user);
        }
        modelAndView.setViewName("index");
        return modelAndView;
    }

    // 获取卡券
    @PostMapping("/get_voucher")
    @ResponseBody
    public String getVoucher(@RequestParam("openId") String openId, @RequestParam(required = false, value = "operateType") String operateType) {
        return JSON.toJSONString(sysVoucherService.findVoucher(openId, operateType));
    }

    // 兑换卡券
    @PostMapping("/confirm_exchange")
    @ResponseBody
    public String confirmVoucher(@RequestParam("openId") String openId, @RequestParam("voucherId") String voucherId) {
        return JSON.toJSONString(sysVoucherService.exchangeVoucher(openId, voucherId));
    }

    // 充电
    @PostMapping("/confirm_charge")
    @ResponseBody
    public String confirmCharge(@RequestParam("openId") String openId) {
        return JSON.toJSONString(wxUserService.exchangeCharge(openId));
    }

}
