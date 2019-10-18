package site.zhongkai.ask.config;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@SuppressWarnings({"unused"})
@Component
public  class Constant implements Serializable {
    private static final long serialVersionUID = 8549142630108385432L;

	// 响应结果
	public static final Integer STATE_SUCCESS = 0;     			// 成功
	public static final Integer STATE_FAIL = 1;        			// 失败

	// 响应说明
	public static final String EXPLAIN_SUCCESS = "成功";
	public static final String EXPLAIN_FAIL = "失败";
	public static final String OPERATE_SUCCESS = "操作成功";
	public static final String OPERATE_FAIL = "操作成功";

	// 选项名称
	public static final String OPTION_A = "A";
	public static final String OPTION_B = "B";
	public static final String OPTION_C = "C";
	public static final String OPTION_D = "D";
	public static final String OPTION_E = "E";

	// 配置文件
	public static final String SYSTEM_PROPERTIES= "configure/system.properties";

	// 卡券状态
	public static final Integer VOUCHER_NORMAL = 0;       		// 未使用
	public static final Integer VOUCHER_USED = 1;         		// 已使用

	// 接口类型
	public static final Integer INTERFACE_CHARGING_PILE = 1;	// 充电桩

}
