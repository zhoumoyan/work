package site.zhongkai.ask.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.zhongkai.ask.entity.ExamInfo;
import site.zhongkai.ask.service.ExamInfoService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.R;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Log4j2
@RestController
public class ExamInfoController {

    @Resource
    private ExamInfoService examInfoService;

    //分页查询
    @PostMapping("/get_exam_info_list")
    public R getExamInfoList(@RequestParam Map<String, Object> map, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu = examInfoService.getExamInfoList(map);
        return new R(0, "success", pu.getTotalCount(), pu.getList());
    }

    //添加
    @PostMapping("/add_exam_info")
    public String addExamInfo(ExamInfo examinfo, HttpServletResponse response) {
        examinfo.setCreateTime(new Date());
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (examInfoService.insert(examinfo)) {
            return "success";
        } else {
            return "error";
        }
    }

    //删除
    @PostMapping("/del_exam_info")
    public String delExamInfo(ExamInfo examinfo, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (examInfoService.deleteById(examinfo)) {
            return "success";
        } else {
            return "error";
        }
    }

    //修改
    @PostMapping("/update_exam_info")
    public String updateExamInfo(ExamInfo examinfo, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (examInfoService.updateById(examinfo)) {
            return "success";
        } else {
            return "error";
        }
    }

    //根据id查询题目信息
    @PostMapping("/get_exam_info_by_id")
    public ExamInfo getExamInfoById(ExamInfo examinfo, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return examInfoService.selectOne(new EntityWrapper<ExamInfo>().eq("id", examinfo.getId()));
    }


    //根据题目类别id 随机选取20道题进行出题
    @PostMapping("/get_exam_info_by_type_id")
    public List<ExamInfo> getExamInfoByTypeId(ExamInfo examinfo, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<ExamInfo> list;
        list = examInfoService.selectList(new EntityWrapper<ExamInfo>().eq("examtypeid", examinfo.getExamTypeId()));
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
            for (Integer integer : li) {
                list_new.add(list.get(integer));
            }
            System.out.println(list_new.toString());
            return list_new;
        }
    }

    private static int getRandomNum(int count) {
        Random random = new Random();
        return random.nextInt(count);
    }


}
