package site.zhongkai.ask.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.zhongkai.ask.entity.Manager;
import site.zhongkai.ask.entity.SysVoucher;
import site.zhongkai.ask.entity.UserVoucher;
import site.zhongkai.ask.entity.WxUser;
import site.zhongkai.ask.service.IManagerService;
import site.zhongkai.ask.service.ISysVoucherService;
import site.zhongkai.ask.service.IUserVoucherService;
import site.zhongkai.ask.service.IWxUserService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.R;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/manage")
public class ManageController {

    @Resource
    private IManagerService managerService;
    @Resource
    private ISysVoucherService sysVoucherService;
    @Resource
    private IUserVoucherService userVoucherService;
    @Resource
    private IWxUserService wxUserService;

    @GetMapping("/logout")
    public void logout(HttpServletResponse response, HttpSession session) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(session == null){
            response.sendRedirect("/ask/login.html");
            return;
        }
        session.removeAttribute("userid");
        session.removeAttribute("username");
        session.removeAttribute("avatar");
        response.sendRedirect("/ask/login.html");
    }

    // 处理用户登录
    @PostMapping("/handle_login")
    @ResponseBody
    public Manager handleLogin(Manager manager, HttpServletResponse response, HttpSession session) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Manager loginUser = managerService.selectOne(new EntityWrapper<Manager>()
                .eq("loginname", manager.getLoginname())
                .eq("password", manager.getPassword()));
        loginUser.setPassword(null).setSalt(null).setToken(null);
        session.setAttribute("userid", loginUser.getId());
        session.setAttribute("username", loginUser.getUsername());
        session.setAttribute("avatar", loginUser.getAvatar());
        return loginUser;
    }

    // 获取后台用户信息
    @PostMapping("/get_users")
    @ResponseBody
    public R getAllManager(@RequestParam Map<String, Object> map, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu = managerService.getManagers(map);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object manager : pu.getList()) {
            ((Manager) manager).setFormatTime((simpleDateFormat).format(((Manager) manager).getCreateTime()));
        }
        return new R(0, "success", pu.getTotalCount(), pu.getList());
    }

    @PostMapping("/add_user")
    @ResponseBody
    public String addManager(Manager manager, HttpServletResponse response) {
        manager.setState("0").setCreateTime(new Date());
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (managerService.insert(manager)) {
            return "success";
        } else {
            return "error";
        }
    }

    @PostMapping("/delete_user")
    @ResponseBody
    public String delManager(Manager manager, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (managerService.deleteById(manager)) {
            return "success";
        } else {
            return "error";
        }
    }

    @PostMapping("/update_user")
    @ResponseBody
    public String updateManager(Manager manager, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (managerService.updateById(manager)) {
            return "success";
        } else {
            return "error";
        }
    }

    // 根据id查询信息
    @PostMapping("/get_user_by_id")
    @ResponseBody
    public Manager getManagerById(Manager manager, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return managerService.selectOne(
                new EntityWrapper<Manager>().eq("id", manager.getId()));
    }

    // 获取卡券
    @PostMapping("/get_vouchers")
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
    @PostMapping("/get_voucher_by_id")
    @ResponseBody
    public SysVoucher getSysVoucherById(SysVoucher sysVoucher, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        SysVoucher voucher = sysVoucherService.selectOne(new EntityWrapper<SysVoucher>().eq("id", sysVoucher.getId()));
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        voucher.setValidTimeFormat(formatDate.format(voucher.getValidTime()));
        return voucher;
    }

    // 更新卡券
    @PostMapping("/update_voucher")
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

    // 添加卡券
    @PostMapping("/add_voucher")
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

    // 获取用户兑换的卡券
    @PostMapping("/get_user_vouchers")
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

    // 获取微信用户
    @PostMapping("/get_wx_users")
    @ResponseBody
    public R getAllWxUser(@RequestParam Map<String, Object> map, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu = wxUserService.getWxUserList(map);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object wxUser : pu.getList()) {
            ((WxUser) wxUser).setCreateTimeFormat((simpleDateFormat).format(((WxUser) wxUser).getCreateTime()));
            if (((WxUser) wxUser).getActiveTime() != null)
                ((WxUser) wxUser).setActiveTimeFormat((simpleDateFormat).format(((WxUser) wxUser).getActiveTime()));
        }
        return new R(0, "success", pu.getTotalCount(), pu.getList());
    }

}
