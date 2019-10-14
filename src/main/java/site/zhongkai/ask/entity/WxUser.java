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
@TableName("t_wx_user")
public class WxUser implements Serializable {
    private static final long serialVersionUID = -342988647868559152L;
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;              // 业务主键
    private String wechatId;        // 微信号
    private String openId;          // OpenID
    private String nickName;        // 微信昵称
    private Integer grade;          // 用户积分
    private Date createTime;        // 创建时间
    @TableField(exist = false)
    private String createTimeFormat;
    private Date activeTime;        // 活跃时间
    @TableField(exist = false)
    private String activeTimeFormat;

    public WxUser() {
    }

    public WxUser(String openId) {
        this.openId = openId;
    }

    public WxUser(String openId, String nickName) {
        this.openId = openId;
        this.nickName = nickName;
    }
}
