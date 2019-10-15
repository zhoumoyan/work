package site.zhongkai.ask.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_grade_log")
public class InterfaceLog implements Serializable {
    private static final long serialVersionUID = 2522681266361112175L;
    @TableId(type= IdType.ID_WORKER_STR)
    private String id;                      // 业务主键
    private String openId;                  // OpenID
    private Integer consumeScore;           // 消耗积分
    private String userVoucherId;           // 用户卡券编号
    private String interfaceSource;         // 接口来源
    private Date operateTime;               // 操作时间
}
