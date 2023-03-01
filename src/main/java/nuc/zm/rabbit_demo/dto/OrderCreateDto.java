package nuc.zm.rabbit_demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nuc.zm.rabbit_demo.common.OrderStatus;

import java.math.BigDecimal;

/**
 * 订单创建dto
 * dto 对外通信的结构体 消息体是可能为空的 要用包装类
 * @author zm
 * @date 2023/02/28
 */
@Getter
@Setter
@ToString
public class OrderCreateDto {

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 订单状态
     */
    private OrderStatus orderStatus;

    /**
     * 钱 : 高精度
     */
    private BigDecimal money;


    /**
     * 送货员身份证
     */
    private Integer deliveryManId;

    /**
     * 积分结算的 Id
     */
    private Integer rewardId;

    /**
     * 奖励金额
     */
    private Integer rewardAmount;

    /**
     * 确认订单是否完成
     */
    private Boolean confirmed;
}
