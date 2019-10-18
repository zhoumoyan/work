package site.zhongkai.ask.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ChargingPile {
    private String volumeNo;
    private String openid;
    private BigDecimal money;
    private Integer isUse;
    private Long createTime;
    private Long validTime;
    private String remark;

    public ChargingPile() {
    }

    public ChargingPile(String volumeNo, String openid, BigDecimal money, Integer isUse, Long createTime, Long validTime, String remark) {
        this.volumeNo = volumeNo;
        this.openid = openid;
        this.money = money;
        this.isUse = isUse;
        this.createTime = createTime;
        this.validTime = validTime;
        this.remark = remark;
    }

}
