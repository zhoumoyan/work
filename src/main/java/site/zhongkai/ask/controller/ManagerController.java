package site.zhongkai.ask.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.zhongkai.ask.entity.Manager;
import site.zhongkai.ask.service.IManagerService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.R;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Resource
    private IManagerService managerService;

    @GetMapping("/login")
    public String login() {
        return "login";
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
        session.setMaxInactiveInterval(1800);
        return loginUser;
    }

    // 分页查询
    @PostMapping("/get_all")
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

    // 添加
    @PostMapping("/add")
    @ResponseBody
    public String addManager(Manager manager, HttpServletResponse response) {
        manager.setState("0").setCreateTime(new Date());
        System.err.println(manager);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (managerService.insert(manager)) {
            return "success";
        } else {
            return "error";
        }
    }

    // 删除
    @PostMapping("/delete")
    @ResponseBody
    public String delManager(Manager manager, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (managerService.deleteById(manager)) {
            return "success";
        } else {
            return "error";
        }
    }

    // 修改
    @PostMapping("/update")
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
    @PostMapping("/get_by_id")
    @ResponseBody
    public Manager getManagerById(Manager manager, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return managerService.selectOne(
                new EntityWrapper<Manager>().eq("id", manager.getId()));
    }

}
