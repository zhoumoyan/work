package site.zhongkai.ask.service.imp;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import site.zhongkai.ask.config.Constant;
import site.zhongkai.ask.entity.ExamInfo;
import site.zhongkai.ask.service.ExamInfoService;
import site.zhongkai.ask.service.IAnswerService;
import site.zhongkai.ask.utils.ResponseResult;
import site.zhongkai.ask.vo.ExamOption;
import site.zhongkai.ask.vo.ExamRandom;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Log4j2
@Service
public class AnswerServiceImpl implements IAnswerService {

    @Resource
    private ExamInfoService examInfoService;

    @Override
    public ResponseResult getRandomExamInfo() {
        List<ExamInfo> examInfos = examInfoService.selectList(new EntityWrapper<>());
        List<ExamInfo> examInfoRandoms = new ArrayList<>();
        List<ExamRandom> responseExamInfos = new ArrayList<>();
        if (examInfos.size() > 20) {
            List<Integer> randoms = new ArrayList<>();
            while (randoms.size() < 20) {
                int random = (new Random()).nextInt(examInfos.size());
                if (!randoms.contains(random)) randoms.add(random);
            }
            for (Integer integer : randoms) examInfoRandoms.add(examInfos.get(integer));
        }
        for (ExamInfo examInfo : examInfoRandoms) {
            List<ExamOption> options = new LinkedList<>();
            options.add(new ExamOption(Constant.OPTION_A, examInfo.getOptionA()));
            options.add(new ExamOption(Constant.OPTION_B, examInfo.getOptionB()));
            if (examInfo.getOptionC() != null && !"".equals(examInfo.getOptionC())) options.add(new ExamOption(Constant.OPTION_C, examInfo.getOptionC()));
            if (examInfo.getOptionD() != null && !"".equals(examInfo.getOptionD())) options.add(new ExamOption(Constant.OPTION_D, examInfo.getOptionD()));
            if (examInfo.getOptionE() != null && !"".equals(examInfo.getOptionE())) options.add(new ExamOption(Constant.OPTION_E, examInfo.getOptionE()));
            responseExamInfos.add(new ExamRandom(examInfo.getId(), examInfo.getExamName(), examInfo.getCorrectAnswer(), options));
        }
        return new ResponseResult<>(true, 0, Constant.EXPLAIN_SUCCESS, responseExamInfos);
    }

}
