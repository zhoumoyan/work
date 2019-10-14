package site.zhongkai.ask.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import site.zhongkai.ask.config.Constant;
import site.zhongkai.ask.service.IAnswerService;
import site.zhongkai.ask.utils.ResponseResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Log4j2
@RestController
@RequestMapping(value = "/answer", produces = "application/json;charset=utf-8")
public class AnswerController {

    @Resource
    private IAnswerService answerService;

    // 获取题目
    @GetMapping("/get_exam_info")
    public String getExamInfo(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return JSON.toJSONString(answerService.getRandomExamInfo().getData());
    }

    // 计算积分
    @PostMapping("/calculate_score")
    public String calculateScore(HttpServletRequest request, HttpServletResponse response) {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] values = request.getParameterValues(paramName);
            for (String value : values) System.err.println(paramName + "=" + value);
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        return JSON.toJSONString(new ResponseResult<>(true, Constant.STATE_SUCCESS, Constant.EXPLAIN_SUCCESS));
    }

    // 获取结果
    @PostMapping("/get_result")
    public String getResult(@RequestParam("openId") String openId, @RequestParam("answerFraction") String answerFraction) {
        return JSON.toJSONString(answerService.getUserGrade(openId, Integer.valueOf(answerFraction)));
    }
}
