package site.zhongkai.ask.entity;

import com.baomidou.mybatisplus.annotations.TableField;
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
@TableName("t_user_voucher")
public class UserVoucher implements Serializable {
    private static final long serialVersionUID = 5090031758843513210L;
    @TableId(type= IdType.ID_WORKER_STR)
    private String id;                  // 业务主键
    private String openId;              // OpenID
    private BigDecimal money;           // 卡券金额
    @TableField(exist = false)
    private String moneyFormat;         // 格式金额
    private String voucherId;           // 系统卡券编号
    private String voucherExplain;      // 卡券说明
    private String consumeExplain;      // 消耗说明
    private Integer consumeScore;       // 消耗分数
    private Date validTime;             // 有效时间
    @TableField(exist = false)
    private String validTimeFormat;     // 格式时间
    private Integer state;              // 卡券状态：0-未充电，1-已充电
    private Date exchangeTime;          // 兑换时间
    @TableField(exist = false)
    private String exchangeTimeFormat;  // 格式时间
    private Date useTime;               // 使用时间
    @TableField(exist = false)
    private String useTimeFormat;       // 格式时间

    public UserVoucher() {
    }

    public UserVoucher(String id, Integer state, Date useTime) {
        this.id = id;
        this.state = state;
        this.useTime = useTime;
    }

    public UserVoucher(String openId, BigDecimal money, String voucherId, String voucherExplain, String consumeExplain, Integer consumeScore, Date validTime, Integer state, Date exchangeTime) {
        this.openId = openId;
        this.money = money;
        this.voucherId = voucherId;
        this.voucherExplain = voucherExplain;
        this.consumeExplain = consumeExplain;
        this.consumeScore = consumeScore;
        this.validTime = validTime;
        this.state = state;
        this.exchangeTime = exchangeTime;
    }

}
