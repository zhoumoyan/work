package site.zhongkai.ask.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_interface_log")
public class InterfaceLog implements Serializable {
    private static final long serialVersionUID = 2522681266361112175L;
    @TableId(type= IdType.ID_WORKER_STR)
    private String id;                      // 业务主键
    private String openId;                  // OpenID
    private Integer consumeScore;           // 消耗积分
    private String userVoucherId;           // 用户卡券编号
    private BigDecimal money;               // 卡券金额
    private Date validTime;                 // 有效时间
    private Integer interfaceType;          // 接口类型：1-充电桩
    private String messageContent;          // 消息内容
    private Date operateTime;               // 操作时间
    private Integer operateResult;          // 操作结果：0-成功，1-接口请求失败，2-充电桩操作失败
    private String resultMessage;           // 结果描述

    public InterfaceLog() {
    }

    public InterfaceLog(String openId, Integer consumeScore, String userVoucherId, BigDecimal money, Date validTime, Integer interfaceType, String messageContent, Date operateTime) {
        this.openId = openId;
        this.consumeScore = consumeScore;
        this.userVoucherId = userVoucherId;
        this.money = money;
        this.validTime = validTime;
        this.interfaceType = interfaceType;
        this.messageContent = messageContent;
        this.operateTime = operateTime;
    }

}
