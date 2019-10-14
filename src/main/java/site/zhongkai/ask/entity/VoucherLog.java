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
@TableName("t_voucher_log")
public class VoucherLog implements Serializable {
    private static final long serialVersionUID = 5090031758843513210L;
    @TableId(type= IdType.ID_WORKER_STR)
    private String id;                  // 业务主键
    private String openId;              // OpenID
    private BigDecimal money;           // 代金卷金额
    private String voucherId;           // 代金卷编号
    private String voucherExplain;      // 代金卷说明
    private Date validTime;             // 有效时间
    private Integer state;              // 卡卷状态：0-未使用，1-已使用
    private String addTime;             // 添加时间
    private Date useTime;               // 使用时间
}
