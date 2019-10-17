package site.zhongkai.ask.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_manager")
public class Manager implements Serializable {
    private static final long serialVersionUID = -3280666307446714645L;
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;          // 用户ID
    private String loginname;   // 登录账号
    private String password;    // 登录密码
    private String username;    // 用户姓名
    private String phone;       // 手机号码
    private Integer gender;     // 性别：0-女,1-男
    private String telephone;   // 电话号码
    private String email;       // 电子邮箱
    private String salt;        // 加密盐
    private String avatar;      // 用户头像
    private String token;       // 用户Token
    private String state;       // 用户状态：0-正常,1-已注销
    private Date createTime;    // 创建时间
    @TableField(exist = false)
    private String formatTime;  // 格式时间
}
