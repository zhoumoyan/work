package site.zhongkai.ask.service;

import com.baomidou.mybatisplus.service.IService;
import site.zhongkai.ask.entity.Answerresult;

import java.util.List;

/**
 * service
 */
public interface AnswerResultService extends IService<Answerresult> {
    //查询学生答题结果
    List<Answerresult> getAnswerresultList(Answerresult answerresult);
}
