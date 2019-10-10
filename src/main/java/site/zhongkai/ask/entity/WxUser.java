package site.zhongkai.ask.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_wx_user")
public class WxUser {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;              // 业务主键
    private String wechatId;        // 微信号
    private String openId;          // OpenID
    private Integer grade;          // 用户积分
    private Date createTime;        // 创建时间
    @TableField(exist = false)
    private String createTimeFormat;
    private Date activeTime;        // 活跃时间
    @TableField(exist = false)
    private String activeTimeFormat;
}
