package site.zhongkai.ask.config;

import site.zhongkai.ask.utils.PropertiesUtils;
import site.zhongkai.ask.utils.ResponseResult;

public class Response {
	private static final String MAX_ANSWER_COUNT = PropertiesUtils.getValue(Constant.SYSTEM_PROPERTIES, "maxAnswerCount");
	private static final ResponseResult E40001_RESULT = new ResponseResult<>(false, 40001, "您的当天答题次数已达到" + MAX_ANSWER_COUNT + "次，请明日再来。");
	private static final ResponseResult E40002_RESULT = new ResponseResult<>(false, 40002, "您的当天答题次数已达到" + MAX_ANSWER_COUNT + "次，请明日再来。");
	private static final ResponseResult E50000_RESULT = new ResponseResult<>(false, 50000, "未知错误");

	// 异常响应
	public static ResponseResult getErrorResult(int state, String... strings) {
		switch (state) {
			case 40001:
				return E40001_RESULT;
			case 40002:
				return E40002_RESULT;
			default:
				return E50000_RESULT;
		}
	}

}
