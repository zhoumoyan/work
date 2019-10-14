package site.zhongkai.ask.config;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@SuppressWarnings({"unused"})
@Component
public  class Constant implements Serializable {
    public static final String RMI_SECRET_KEY = "CC491684-CC6C-4991-B3C5-E262142C0A48";
    private static final long serialVersionUID = 8549142630108385432L;

	// 日志说明
	public static final String EXPLAIN_SUCCESS = "成功";
	public static final String EXPLAIN_FAIL = "失败";

	// 选项名称
	public static final String OPTION_A = "A";
	public static final String OPTION_B = "B";
	public static final String OPTION_C = "C";
	public static final String OPTION_D = "D";
	public static final String OPTION_E = "E";

	// 配置文件
	public static final String SYSTEM_PROPERTIES= "configure/system.properties";

	// 数据状态
	public static final String STATE_NORMAL = "0";       // 正常
	public static final String STATE_STOP = "1";         // 暂停
	public static final String STATE_DELETE = "2";       // 销户

	// 操作类型
	public static final String TYPE_REG = "1";          // 注册
	public static final String OPER_TYPE_REG = "注册";
	public static final String TYPE_LOGIN = "2";        // 登录
	public static final String OPER_TYPE_LOGIN = "登录";
	public static final String TYPE_LOGOUT = "3";      // 退出
	public static final String OPER_TYPE_LOGOUT = "退出";
	public static final String TYPE_CHANGE_PWD = "4";   // 改密
	public static final String OPER_TYPE_CHANGE_PWD = "修改密码";
	public static final String TYPE_ALARM = "5";        // 短信告警

	// 操作结果
	public static final String OPER_SUCCESS = "0";      // 成功
	public static final String OPER_FAIL = "1";         // 失败

	public static final String EXPLAIN_ERG_REPEAT = "重复注册";
	public static final String EXPLAIN_USER_NOT_EXIST = "账号不存在";
	public static final String EXPLAIN_USER_STATE_ERROR = "账号状态不正常";
	public static final String EXPLAIN_PASSWORD_ERROR = "密码不正确";
	public static final String EXPLAIN_BUSY_ERROR = "系统忙";

	// 响应结果
	public static final Integer STATE_SUCCESS = 0;     // 成功
	public static final Integer STATE_FAIL = 1;        // 失败

	// 响应说明
	public static final String GET_DATA_SUCCESS = "获取数据成功";
	public static final String SET_DATA_SUCCESS = "更新数据成功";
	public static final String ADD_DATA_SUCCESS = "添加数据成功";
	public static final String SEND_SMS_SUCCESS = "短信发送成功";

	// 处理呼叫的状态和描述
	public static final String CALL_STATE_1 = "1";
	public static final String CALL_DESCRIBE_1 = "已接";
	public static final String CALL_STATE_2 = "2";
	public static final String CALL_DESCRIBE_2 = "未接";
	public static final String CALL_STATE_3 = "3";
	public static final String CALL_DESCRIBE_3 = "拒接";
	public static final String CALL_STATE_4 = "4";
	public static final String CALL_DESCRIBE_4 = "被他人接听";
	public static final String CALL_STATE_5 = "5";
	public static final String CALL_DESCRIBE_5 = "主动呼叫";
	public static final String CALL_STATE_6 = "6";
	public static final String CALL_DESCRIBE_6 = "结束通话";

	// 呼叫类型
	public static final Integer CALL_TYPE_PASSIVE = 1;  // App被动呼叫
	public static final Integer CALL_TYPE_ACTIVE = 2;   // App主动呼叫

	// 门禁的类型：1-门,2-闸
	public static final String TYPE_DOOR = "1";
	public static final String TYPE_GATE = "2";

	// 开门接口请求携带的参数名
	public static final String REQUEST_PARAM_NAME = "msg=";
	// 视频语音流Url后缀
	public static final String STREAM_SUFFIX = "/";
	// 拼接字符串
	public static final String STRING_DASHES = "-";

	// 中间件的接口类型：
	// 1-callTermination通话终止,2-delOrAddCard增删卡操作,3-delOrAddSect增删临时密码操作,4-openDoor开门接口,5-voiceInitiated通话发起,6-configParameter配置参数,7-查询配置configSearch,8-请求软件升级requestSoftwareUpgrade,9-配置流媒体地址configStreamAddr,10-请求软件版本号requestSoftwareVersion
	public static final Integer INTERFACE_CALL_TERMINATION = 1;
	public static final Integer INTERFACE_DEL_OR_ADD_CARD = 2;
	public static final Integer INTERFACE_DEL_OR_ADD_SECT = 3;
	public static final Integer INTERFACE_OPEN_DOOR = 4;
	public static final Integer INTERFACE_VOICE_INITIATED = 5;
	public static final Integer INTERFACE_CONFIG_PARAMETER = 6;
	public static final Integer INTERFACE_CONFIG_SEARCH = 7;
	public static final Integer INTERFACE_CONFIG_FIRMWARE = 8;
	public static final Integer INTERFACE_CONFIG_STREAME = 9;
	public static final Integer INTERFACE_FIND_FIRMWARE = 10;

	// 硬件设备默认的参数值
	public static final String PARAMETER_RING_TIMEOUT = "60";                               // 呼叫等待时间
	public static final String PARAMETER_CALL_TIMEOUT = "600";                              // 通话最长时间
	public static final String PARAMETER_DOOR_OPEN_TIMEOUT = "30";                          // 门开超时时间
	public static final String PARAMETER_BACKLIGHT_TIMEOUT = "5";                           // 按键背光时间
	public static final String PARAMETER_PUSH_STREAM_ADDR = "rtmp://219.159.68.62/live/";   // 默认的推流地址

	// 设备类型
	public static final String DEVICE_TYPE_DOOR = "1";
	public static final String DEVICE_TYPE_DOOR_DESCRIBE = "门";
	public static final String DEVICE_TYPE_GATE = "2";
	public static final String DEVICE_TYPE_GATE_DESCRIBE = "闸";

	// 同步接口
	public static final String SYNC_FAIL_PART_DATA = "存在同步失败的数据";
	public static final Integer SYNC_OPER_DELETE = 0;
	public static final Integer SYNC_OPER_ADD = 1;
	public static final Integer SYNC_OPER_UPDATE = 2;
	public static final Integer SYNC_OPER_HOST_FIRM = 3;
	public static final Integer SYNC_OPER_DEVICE_FIRM = 4;

	// 房间认证状态
	public static final Integer ROOM_AUTH_AUTO = 1;         // 自动认证
	public static final Integer ROOM_AUTH_WAIT = 2;			// 待审核
	public static final Integer ROOM_AUTH_PASS = 3;         // 审核通过
	public static final Integer ROOM_AUTH_REJECT = 4;       // 审核不通过

	// 注册人员和房间的身份
	public static final Integer ROOM_ROLE_OWNER = 1;		// 业主
	public static final Integer ROOM_ROLE_FAMILT = 2;		// 家属

	// 临时密码
	public static final String TMP_PWD_ADD = "1";
	public static final String TMP_PWD_DEL = "0";
	public static final Integer TMP_PWD_STATE_VAILD = 1;	// 有效
	public static final Integer TMP_PWD_STATE_INVAILD = 2;	// 失效

	// PC端需要的呼叫状态描述
	public static final String CALL_STATUS_RING = "呼叫中";
	public static final String CALL_STATUS_CALLING = "正在通话";

	// 开门方式
	public static final String OPEN_MODE_PWD = "密码开门";
	// 设备的初始密码
	public static final String DEVICE_INIT_PWD = "初始密码开门";
	// 用初始密码开门保存的业主ID前缀
	public static final String OPEN_INIT_PWD_PREFIX = "0000000000000";	// 13位
	// 临时密码开门找不到业主ID时保存的前缀
	public static final String OPEN_OWNERID_NULL = "找不到业主信息";
	public static final String OPEN_OWNERID_NULL_PREFIX = "1111111111111";	// 13位
}
