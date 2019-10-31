package site.zhongkai.ask.config;

import site.zhongkai.ask.utils.PropertiesUtils;
import site.zhongkai.ask.utils.ResponseResult;

public class Response {
	private static final String MAX_ANSWER_COUNT = PropertiesUtils.getValue(Constant.SYSTEM_PROPERTIES, "maxAnswerCount");
	private static final ResponseResult E40001_RESULT = new ResponseResult<>(false, 40001, "您当日答题次数已达到" + MAX_ANSWER_COUNT + "次上限\n请明日再来");
	private static final ResponseResult E40002_RESULT = new ResponseResult<>(false, 40002, "该兑换卷已失效或不可用");
	private static final ResponseResult E40003_RESULT = new ResponseResult<>(false, 40003, "可用积分不足，兑换失败");
	private static final ResponseResult E40004_RESULT = new ResponseResult<>(false, 40004, "非法访问");
	private static final ResponseResult E40005_RESULT = new ResponseResult<>(false, 40005, "兑换充电失败，请稍后再试");
	private static final ResponseResult E40006_RESULT = new ResponseResult<>(false, 40006, "部分卡券兑换充电失败");
	private static final ResponseResult E40007_RESULT = new ResponseResult<>(false, 40007, "操作失败，您没有未使用的卡券");
	private static final ResponseResult E50000_RESULT = new ResponseResult<>(false, 50000, "未知错误");

	// 异常响应
	public static ResponseResult getErrorResult(int state, String... strings) {
		switch (state) {
			case 40001:
				return E40001_RESULT;
			case 40002:
				return E40002_RESULT;
			case 40003:
				return E40003_RESULT;
			case 40004:
				return E40004_RESULT;
			case 40005:
				return E40005_RESULT;
			case 40006:
				return E40006_RESULT;
			case 40007:
				return E40007_RESULT;
			default:
				return E50000_RESULT;
		}
	}

	private static final ResponseResult S20000_RESULT = new ResponseResult<>(true, Constant.STATE_SUCCESS, Constant.EXPLAIN_SUCCESS);
	private static final ResponseResult S20001_RESULT = new ResponseResult<>(true, Constant.STATE_SUCCESS, Constant.OPERATE_SUCCESS);
	private static final ResponseResult S20002_RESULT = new ResponseResult<>(true, Constant.STATE_SUCCESS, Constant.OPERATE_SUCCESS);

	// 成功响应
	public static ResponseResult getSuccessResult(int state) {
		switch (state) {
			case 20001:
				return S20001_RESULT;
			case 20002:
				return S20002_RESULT;
			default:
				return S20000_RESULT;
		}
	}

}
