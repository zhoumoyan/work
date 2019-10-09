package site.zhongkai.ask.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import site.zhongkai.ask.entity.ExamInfo;
import site.zhongkai.ask.service.ExamInfomanageService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@RestController
public class ExamInfomanageController {
    @Autowired
    private ExamInfomanageService examinfomanageService;

    //分页查询
    @PostMapping("/getExamInfoList")
    public R getExamInfoList(@RequestParam Map<String, Object> map, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu = examinfomanageService.getExamInfoList(map);
        return new R(0, "success", pu.getTotalCount(), pu.getList());
    }

    //添加
    @PostMapping("/addExamInfo")
    public String addExamInfo(ExamInfo examinfo, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (examinfomanageService.insert(examinfo)) {
            return "success";
        } else {
            return "error";
        }
    }

    //删除
    @PostMapping("/delExamInfo")
    public String delExamInfo(ExamInfo examinfo, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (examinfomanageService.deleteById(examinfo)) {
            return "success";
        } else {
            return "error";
        }
    }

    //修改
    @PostMapping("/updateExamInfo")
    public String updateExamInfo(ExamInfo examinfo, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (examinfomanageService.updateById(examinfo)) {
            return "success";
        } else {
            return "error";
        }
    }

    //根据id查询题目信息
    @PostMapping("/getExamInfoById")
    public ExamInfo getExamInfoById(ExamInfo examinfo, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return examinfomanageService.selectOne(
                new EntityWrapper<ExamInfo>().eq("id", examinfo.getId()));
    }


    //根据题目类别id 随机选取20道题进行出题
    @PostMapping("/getExamInfoByTypeId")
    public List<ExamInfo> getExamInfoByTypeId(ExamInfo examinfo, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<ExamInfo> list;
        list = examinfomanageService.selectList(
                new EntityWrapper<ExamInfo>().eq("examtypeid", examinfo.getExamtypeid()));
        if (list.size() <= 20) {
            //如果题库出题小于等于20题时则直接返回
            return list;
        } else {
            ArrayList<Integer> li = new ArrayList<>();
            do {
                int result = getRandomNum(list.size());
                if (!li.contains(result)) {
                    li.add(result);
                }
            } while (li.size() < 20);
            List<ExamInfo> list_new = new ArrayList<>();
            for (int i = 0; i < li.size(); i++) {
                list_new.add(list.get(li.get(i)));
            }
            System.out.println(list_new.toString());
            return list_new;
        }
    }

    public static int getRandomNum(int count) {
        Random random = new Random();
        return random.nextInt(count);
    }


}
