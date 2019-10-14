package site.zhongkai.ask.service;

import site.zhongkai.ask.utils.ResponseResult;

public interface IAnswerService {

	// 随机20道题
	ResponseResult getRandomExamInfo();

	// 获取积分
	ResponseResult getUserGrade(String openId, Integer answerFraction);

}
