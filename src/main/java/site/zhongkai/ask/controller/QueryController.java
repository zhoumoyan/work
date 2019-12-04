package site.zhongkai.ask.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.zhongkai.ask.entity.WxUser;
import site.zhongkai.ask.service.IQueryService;
import site.zhongkai.ask.service.IWxUserService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.ResponseLayui;
import site.zhongkai.ask.vo.CountAnswerNum;
import site.zhongkai.ask.vo.CountNum;
import site.zhongkai.ask.vo.CountVoucherNum;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/query")
public class QueryController {

    @Resource
    private IQueryService queryService;
    @Resource
    private IWxUserService wxUserService;

    // 查询卡券使用数量
    @PostMapping("/query_user_voucher")
    @ResponseBody
    public ResponseLayui getVoucher(@RequestParam Map<String, Object> map, HttpServletResponse response) {
        //验证前段传入的用户Id和用户昵称是否存在
        response.setHeader("Access-Control-Allow-Origin", "*");
        String userId=(String)map.get("userId");
        String nickName=(String)map.get("nickName");
        if(userId!=null&&!"".equals(userId)){
            WxUser wxUser=wxUserService.selectOne(new EntityWrapper<WxUser>().eq("id",userId));
            if(wxUser==null){
                return new ResponseLayui(1, "用户Id不存在");
            }
            //仅仅根据userId获取nickName
            nickName=wxUser.getNickName();
        }
        if(nickName!=null&&!"".equals(nickName)){
            WxUser wxUser=wxUserService.selectOne(new EntityWrapper<WxUser>().eq("nick_name",nickName));
            if(wxUser==null){
                return new ResponseLayui(2, "用户昵称不存在");
            }
            //仅仅根据nickName获取userId
            userId=wxUser.getId();
        }

        PageUtils pu = queryService.countVoucher(map);

        for (Object countVoucherNum : pu.getList()) {
            CountVoucherNum countVoucher=(CountVoucherNum)countVoucherNum;
            String openId=countVoucher.getOpenId();
            //System.out.println(openId);
            WxUser wxUser=wxUserService.selectOne(new EntityWrapper<WxUser>().eq("open_id",openId));
            List<CountNum> countNums=queryService.countNum(openId);
            //System.err.printf(countNums.toString());
            int notUsedNum=0;
            int UsedNum=0;
            int TransferNum=0;

            for (CountNum c:countNums) {
                if(c.getState()==0){
                    notUsedNum=c.getNum();
                }
            }
            for (CountNum c:countNums) {
                if(c.getState()==1){
                    UsedNum=c.getNum();
                }
            }
            for (CountNum c:countNums) {
                if(c.getState()==2){
                    TransferNum=c.getNum();
                }
            }
            countVoucher.setNotUsedNum(notUsedNum);
            countVoucher.setUsedNum(UsedNum);
            countVoucher.setTransferNum(TransferNum);

            countVoucher.setUserId(wxUser.getId());
            countVoucher.setNickName(wxUser.getNickName());
        }
        //System.out.printf(pu.getList().toString());
        return new ResponseLayui(0, "success", pu.getTotalCount(), pu.getList());
    }

    // 查询答题次数
    @PostMapping("/query_answer_voucher")
    @ResponseBody
    public ResponseLayui getAnswer(@RequestParam Map<String, Object> map, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        //验证前段传入的用户Id和用户昵称是否存在
        String userId=(String)map.get("userId");
        String nickName=(String)map.get("nickName");
        if(userId!=null&&!"".equals(userId)){
            WxUser wxUser=wxUserService.selectOne(new EntityWrapper<WxUser>().eq("id",userId));
            if(wxUser==null){
                return new ResponseLayui(1, "用户Id不存在");
            }
            //仅仅根据userId获取nickName
            nickName=wxUser.getNickName();
        }
        if(nickName!=null&&!"".equals(nickName)){
            WxUser wxUser=wxUserService.selectOne(new EntityWrapper<WxUser>().eq("nick_name",nickName));
            if(wxUser==null){
                return new ResponseLayui(2, "用户昵称不存在");
            }
            //仅仅根据nickName获取userId
            userId=wxUser.getId();
        }
        PageUtils pu = queryService.countAnswer(map);

        for (Object countAnswersNum : pu.getList()) {
            CountAnswerNum countAnswers=(CountAnswerNum) countAnswersNum;
            String openId=countAnswers.getOpenId();
            WxUser wxUser=wxUserService.selectOne(new EntityWrapper<WxUser>().eq("open_id",openId));
            countAnswers.setUserId(wxUser.getId());
            countAnswers.setNickName(wxUser.getNickName());
        }
        return new ResponseLayui(0, "success", pu.getTotalCount(), pu.getList());
    }
}
