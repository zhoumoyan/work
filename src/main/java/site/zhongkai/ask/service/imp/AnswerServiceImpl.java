package site.zhongkai.ask.service.imp;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import site.zhongkai.ask.config.Constant;
import site.zhongkai.ask.config.Response;
import site.zhongkai.ask.entity.AnswerLog;
import site.zhongkai.ask.entity.ExamInfo;
import site.zhongkai.ask.entity.GradeLog;
import site.zhongkai.ask.entity.WxUser;
import site.zhongkai.ask.mapper.IAnswerLogMapper;
import site.zhongkai.ask.mapper.IExamInfoMapper;
import site.zhongkai.ask.mapper.IGradeLogMapper;
import site.zhongkai.ask.mapper.IWxUserMapper;
import site.zhongkai.ask.service.IAnswerService;
import site.zhongkai.ask.utils.PropertiesUtils;
import site.zhongkai.ask.utils.ResponseResult;
import site.zhongkai.ask.vo.ExamOption;
import site.zhongkai.ask.vo.ExamRandom;
import site.zhongkai.ask.vo.UserGrade;

import javax.annotation.Resource;
import java.util.*;

@Log4j2
@Service
public class AnswerServiceImpl implements IAnswerService {

    private static final String MAX_ANSWER_COUNT = PropertiesUtils.getValue(Constant.SYSTEM_PROPERTIES, "maxAnswerCount");
    @Resource
    private IExamInfoMapper examInfo;
    @Resource
    private IAnswerLogMapper answerLog;
    @Resource
    private IGradeLogMapper gradeLog;
    @Resource
    private IWxUserMapper wxUser;

    @Override
    public ResponseResult getRandomExamInfo() {
        List<ExamInfo> examInfos = examInfo.selectList(new EntityWrapper<>());
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

    @Override
    public ResponseResult getUserGrade(String openId, Integer answerFraction) {
        List<AnswerLog> answerLogs = answerLog.selectTodayList(openId);
        if (answerLogs.size() >= Integer.parseInt(Objects.requireNonNull(MAX_ANSWER_COUNT))) return Response.getErrorResult(40001);
        answerLog.insert(new AnswerLog(openId, answerFraction, new Date()));
        // 当日积分
        for (AnswerLog answerLog : answerLogs) {
            answerFraction += answerLog.getScore();
        }
        List<AnswerLog> logList = answerLog.selectList(new EntityWrapper<AnswerLog>().eq("open_id", openId));
        // 历史积分
        int historyScore = 0;
        for (AnswerLog answerLog : logList) {
            historyScore += answerLog.getScore();
        }
        List<GradeLog> gradeLogs = gradeLog.selectList(new EntityWrapper<GradeLog>().eq("open_id", openId));
        Integer deductPoints = 0;
        for (GradeLog gradeLog : gradeLogs) {
            deductPoints += gradeLog.getDeductPoints();
        }
        // 可用积分
        int validScore = historyScore - deductPoints;
        wxUser.updateForSet("grade=" + validScore, new EntityWrapper<WxUser>().eq("open_id", openId));
        return new ResponseResult<>(true, Constant.STATE_SUCCESS, Constant.EXPLAIN_SUCCESS, new UserGrade(openId, answerFraction, validScore, historyScore));
    }

}
