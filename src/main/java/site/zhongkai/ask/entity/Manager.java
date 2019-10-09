package site.zhongkai.ask.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_manager")
public class Manager implements Serializable {
    private static final long serialVersionUID = -3280666307446714645L;
    private String id;          // 用户ID
    private String loginname;   // 登录账号
    private String password;    // 登录密码
    private String username;    // 用户姓名
    private String phone;       // 手机号码
    private String gender;      // 性别：1-男,2-女
    private String telephone;   // 电话号码
    private String email;       // 电子邮箱
    private String salt;        // 加密盐
    private String avatar;      // 用户头像
    private String token;       // 用户Token
    private String state;       // 用户状态：0-正常,1-已注销
    private Date create_time;   // 创建时间
}
