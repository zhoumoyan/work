package site.zhongkai.ask.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import site.zhongkai.ask.entity.Answerresult;

import java.util.List;

/**
 * dao
 */
public interface AnswerResultDao extends BaseMapper<Answerresult> {
    //查询学生答题结果
    List<Answerresult> getAnswerresultList(Answerresult answerresult);
}
