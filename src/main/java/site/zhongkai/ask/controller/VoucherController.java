package site.zhongkai.ask.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.zhongkai.ask.entity.SysVoucher;
import site.zhongkai.ask.entity.UserVoucher;
import site.zhongkai.ask.service.IUserVoucherService;
import site.zhongkai.ask.service.ISysVoucherService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.R;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/voucher")
public class VoucherController {

    @Resource
    private ISysVoucherService sysVoucherService;
    @Resource
    private IUserVoucherService userVoucherService;

    // 获取卡券
    @PostMapping("/get")
    @ResponseBody
    public String getVoucher(@RequestParam("openId") String openId, @RequestParam(required = false, value = "operateType") String operateType) {
        return JSON.toJSONString(sysVoucherService.findVoucher(openId, operateType));
    }

    // 兑换卡券
    @PostMapping("/confirm_exchange")
    @ResponseBody
    public String confirmVoucher(@RequestParam("openId") String openId, @RequestParam("voucherId") String voucherId){
        return JSON.toJSONString(sysVoucherService.exchangeVoucher(openId, voucherId));
    }

    //分页查询
    @PostMapping("/get_sys")
    @ResponseBody
    public R getSysVoucher(@RequestParam Map<String, Object> map, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu = sysVoucherService.findSysVoucher(map);
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object sysVoucher : pu.getList()) {
            ((SysVoucher) sysVoucher).setValidTimeFormat((formatDate).format(((SysVoucher) sysVoucher).getValidTime()));
            ((SysVoucher) sysVoucher).setCreateTimeFormat((formatTime).format(((SysVoucher) sysVoucher).getCreateTime()));
            ((SysVoucher) sysVoucher).setMoneyFormat(((SysVoucher) sysVoucher).getMoney().toString());
        }
        return new R(0, "success", pu.getTotalCount(), pu.getList());
    }

    // 根据id查询信息
    @PostMapping("/get_sys_by_id")
    @ResponseBody
    public SysVoucher getSysVoucherById(SysVoucher sysVoucher, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        SysVoucher voucher = sysVoucherService.selectOne(new EntityWrapper<SysVoucher>().eq("id", sysVoucher.getId()));
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        voucher.setValidTimeFormat(formatDate.format(voucher.getValidTime()));
        return voucher;
    }

    @PostMapping("/update_sys")
    @ResponseBody
    public String updateManager(SysVoucher sysVoucher, HttpServletResponse response) throws ParseException {
        String alidTimeString = sysVoucher.getValidTimeFormat() + " 23:59:59";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sysVoucher.setValidTime(format.parse(alidTimeString));
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (sysVoucherService.updateById(sysVoucher)) {
            return "success";
        } else {
            return "error";
        }
    }

    @PostMapping("/add_sys")
    @ResponseBody
    public String addSysVoucher(SysVoucher sysVoucher, HttpSession session, HttpServletResponse response) throws ParseException {
        String alidTimeString = sysVoucher.getValidTimeFormat() + " 23:59:59";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sysVoucher.setValidTime(format.parse(alidTimeString));
        sysVoucher.setCreateTime(new Date()).setOperateUserId((String) session.getAttribute("userid"));
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (sysVoucherService.insert(sysVoucher)) {
            return "success";
        } else {
            return "error";
        }
    }

    // 分页查询
    @PostMapping("/get_user")
    @ResponseBody
    public R getUserVoucher(@RequestParam Map<String, Object> map, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu = userVoucherService.findUserVoucher(map);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        for (Object userVoucher : pu.getList()) {
            ((UserVoucher) userVoucher).setMoneyFormat(((UserVoucher) userVoucher).getMoney().toString());
            ((UserVoucher) userVoucher).setValidTimeFormat((formatDate).format(((UserVoucher) userVoucher).getValidTime()));
            ((UserVoucher) userVoucher).setExchangeTimeFormat((simpleDateFormat).format(((UserVoucher) userVoucher).getExchangeTime()));
            if (((UserVoucher) userVoucher).getUseTime() != null) ((UserVoucher) userVoucher).setUseTimeFormat((simpleDateFormat).format(((UserVoucher) userVoucher).getUseTime()));
        }
        return new R(0, "success", pu.getTotalCount(), pu.getList());
    }

}
