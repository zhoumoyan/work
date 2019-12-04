package site.zhongkai.ask.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.zhongkai.ask.config.Constant;
import site.zhongkai.ask.config.Response;
import site.zhongkai.ask.entity.Manager;
import site.zhongkai.ask.entity.SysVoucher;
import site.zhongkai.ask.entity.UserVoucher;
import site.zhongkai.ask.entity.WxUser;
import site.zhongkai.ask.service.IManagerService;
import site.zhongkai.ask.service.ISysVoucherService;
import site.zhongkai.ask.service.IUserVoucherService;
import site.zhongkai.ask.service.IWxUserService;
import site.zhongkai.ask.utils.*;
import site.zhongkai.ask.vo.CountAnswerNum;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Log4j2
@Controller
@RequestMapping("/manage")
public class ManageController {

    private static final String CHARGING_PILE_IP = PropertiesUtils.getValue(Constant.SYSTEM_PROPERTIES, "chargingPileIp");
    @Resource
    private IManagerService managerService;
    @Resource
    private ISysVoucherService sysVoucherService;
    @Resource
    private IUserVoucherService userVoucherService;
    @Resource
    private IWxUserService wxUserService;

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        log.info("[" + request.getRemoteAddr() + "]-管理员[" + session.getAttribute("username") + "]退出登录");
        if (session == null) {
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
    public Manager handleLogin(Manager manager, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Manager loginUser = managerService.selectOne(new EntityWrapper<Manager>()
                .eq("loginname", manager.getLoginname())
                .eq("password", manager.getPassword()));
        if (loginUser == null) return null;
        log.info("[" + request.getRemoteAddr() + "]-管理员[" + loginUser.getUsername() + "]登录后台");
        loginUser.setPassword(null).setSalt(null).setToken(null);
        session.setAttribute("userid", loginUser.getId());
        session.setAttribute("username", loginUser.getUsername());
        session.setAttribute("avatar", loginUser.getAvatar());
        return loginUser;
    }

    // 获取后台用户信息
    @PostMapping("/get_users")
    @ResponseBody
    public ResponseLayui getAllManager(@RequestParam Map<String, Object> map, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu = managerService.getManagers(map);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object manager : pu.getList()) {
            ((Manager) manager).setFormatTime((simpleDateFormat).format(((Manager) manager).getCreateTime()));
        }
        return new ResponseLayui(0, "success", pu.getTotalCount(), pu.getList());
    }

    @PostMapping("/get_voucher_used_type")
    @ResponseBody
    public String getVoucherUsedType(@RequestParam(required = false, value ="username") String username,@RequestParam(required = false, value ="userid")String uid) {
        Manager manager=managerService.selectOne(new EntityWrapper<Manager>().eq("id",uid));
        if(manager==null||username.equals(manager.getLoginname())){
            return "0";//用户信息失效，重新登陆
        }
        List<CountAnswerNum> nums=userVoucherService.countValue();

        return JSON.toJSONString(nums);
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
    public ResponseLayui getSysVoucher(@RequestParam Map<String, Object> map, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu = sysVoucherService.findSysVoucher(map);
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object sysVoucher : pu.getList()) {
            ((SysVoucher) sysVoucher).setValidTimeFormat((formatDate).format(((SysVoucher) sysVoucher).getValidTime()));
            ((SysVoucher) sysVoucher).setCreateTimeFormat((formatTime).format(((SysVoucher) sysVoucher).getCreateTime()));
            ((SysVoucher) sysVoucher).setMoneyFormat(((SysVoucher) sysVoucher).getMoney().toString());
        }
        return new ResponseLayui(0, "success", pu.getTotalCount(), pu.getList());
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
    public ResponseLayui getUserVoucher(@RequestParam Map<String, Object> map, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu = userVoucherService.findUserVoucher(map);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        for (Object userVoucher : pu.getList()) {
            ((UserVoucher) userVoucher).setMoneyFormat(((UserVoucher) userVoucher).getMoney().toString());
            ((UserVoucher) userVoucher).setValidTimeFormat((formatDate).format(((UserVoucher) userVoucher).getValidTime()));
            ((UserVoucher) userVoucher).setExchangeTimeFormat((simpleDateFormat).format(((UserVoucher) userVoucher).getExchangeTime()));
            if (((UserVoucher) userVoucher).getUseTime() != null)
                ((UserVoucher) userVoucher).setUseTimeFormat((simpleDateFormat).format(((UserVoucher) userVoucher).getUseTime()));
        }
        return new ResponseLayui(0, "success", pu.getTotalCount(), pu.getList());
    }

    // 获取微信用户
    @PostMapping("/get_wx_users")
    @ResponseBody
    public ResponseLayui getAllWxUser(@RequestParam Map<String, Object> map, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu = wxUserService.getWxUserList(map);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object wxUser : pu.getList()) {
            ((WxUser) wxUser).setCreateTimeFormat((simpleDateFormat).format(((WxUser) wxUser).getCreateTime()));
            if (((WxUser) wxUser).getActiveTime() != null)
                ((WxUser) wxUser).setActiveTimeFormat((simpleDateFormat).format(((WxUser) wxUser).getActiveTime()));
        }
        return new ResponseLayui(0, "success", pu.getTotalCount(), pu.getList());
    }

    // 获取微信用户
    @PostMapping("/use_voucher")
    @ResponseBody
    public String useVoucher(@RequestParam("voucherId") String voucherId, @RequestParam("useTimestamp") String useTimestamp, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<String> strings = Arrays.asList(Objects.requireNonNull(CHARGING_PILE_IP).split(","));
        if (strings.contains(request.getRemoteAddr()) || ("*".equals(strings.get(0)))) {
            if (JudgeUtils.isAnyEmpty(voucherId, useTimestamp)) return JSON.toJSONString(Response.getErrorResult(40008));
            Date useTime;
            try {
                useTime = new Date(Long.parseLong(useTimestamp));
            } catch (Exception e) {
                log.error(e.toString());
                return JSON.toJSONString(Response.getErrorResult(40008));
            }
            return JSON.toJSONString(managerService.setVoucherState(voucherId, useTime));
        } else {
            log.error("收到来自[" + request.getRemoteAddr() + "]的非法访问manage/use_voucher接口");
            return JSON.toJSONString(Response.getErrorResult(40004));
        }
    }

    // 获取微信用户信息
    @PostMapping("/showInfo")
    @ResponseBody
    public String getUserInfo(@RequestParam("userId") String userId, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Manager manager=managerService.selectOne(new EntityWrapper<Manager>().eq("id",userId));

        if(manager==null){
            return JSON.toJSONString(Response.getErrorResult(40008));
        }
        manager.setId(null);
        manager.setPassword(null);
        manager.setSalt(null);
        manager.setToken(null);
        manager.setState(null);
        return JSON.toJSONString(manager);
    }
    /**
     * 上传的头像的最大大小
     */
    private static final long UPLOAD_MAX_SIZE = 3 * 1024 * 1024;
    /**
     * 允许上传的头像文件的类型列表
     */
    private static final List<String> UPLOAD_CONTENT_TYPES = new ArrayList<>();

    static {
        UPLOAD_CONTENT_TYPES.add("image/jpeg");
        UPLOAD_CONTENT_TYPES.add("image/bmp");
        UPLOAD_CONTENT_TYPES.add("image/png");
        UPLOAD_CONTENT_TYPES.add("image/gif");
    }
    // 获取微信用户信息
    @PostMapping("/changeAvatar")
    @ResponseBody
    public ResponseResult updateAvatar( @RequestParam(value = "userId",required=false)String userId,@RequestParam("avatar") MultipartFile avatar, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        HttpSession   session   =   request.getSession();

        // 检查文件大小
        long size = avatar.getSize();
        //System.out.println("size:"+size);
        if (avatar.isEmpty()) {
            return Response.getErrorResult(50001);
        }
        if (size > UPLOAD_MAX_SIZE) {
            return Response.getErrorResult(50002);
        }
        // 检查文件类型
        String contentType = avatar.getContentType();
        if (!UPLOAD_CONTENT_TYPES.contains(contentType)) {
            return Response.getErrorResult(50003);
        }
        // 基于session获取上传文件夹的路径：session.getServletContext().getRealPath("upload")
        String parentPath = session.getServletContext().getRealPath("upload");
        //System.out.println("parentPath:"+parentPath);
        // 检查上传文件夹是否存在，如果不存在，则创建
        File parent = new File(parentPath);
        if (!parent.exists()) {
            parent.mkdirs();
        }

        // 通过参数avatar获取原始文件名：avatar.getOriginalFilename()
        String originalFilename = avatar.getOriginalFilename();
        // 获取文件的扩展名
        int beginIndex = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(beginIndex);
        // 生成目标文件名
        String filename = System.currentTimeMillis() + suffix;

        // 执行保存头像文件
        File dest = new File(parent, filename);
        try {
            avatar.transferTo(dest);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return Response.getErrorResult(50004);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.getErrorResult(50005);
        }
        // 执行将头像路径存储到数据库中："/upload/" + 目标文件名
        // http://localhost:8096/upload/1.jpg
        String avatarPath = "/upload/" + filename;
        userId=(String)session.getAttribute("userid");
       boolean result=managerService.changeAvatar(userId, avatarPath);

        if (result!=true)
            return Response.getErrorResult(50000);
        session.setAttribute("avatar",avatarPath);
        return new ResponseResult(true,Constant.STATE_SUCCESS,Constant.OPERATE_SUCCESS,avatarPath);

    }

}
