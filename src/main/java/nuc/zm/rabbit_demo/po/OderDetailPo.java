package nuc.zm.rabbit_demo.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nuc.zm.rabbit_demo.common.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据库订单
 * @description : 持久化对象
 * @author zm
 * @date 2023/03/01
 */
@Getter
@Setter
@ToString
public class OderDetailPo {

    private Integer id;
    private OrderStatus status;
    private String address;
    private Integer accountId;
    private Integer productId;
    private Integer deliverymanId;
    private Integer settlementId;
    private Integer rewardId;
    private BigDecimal price;
    private Date date;
}
