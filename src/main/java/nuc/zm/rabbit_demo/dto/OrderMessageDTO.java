package nuc.zm.rabbit_demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 订单消息dto
 *
 * @author zm
 * @date 2023/03/01
 */
@Getter
@Setter
@ToString
public class OrderMessageDTO {

    private Integer orderId;

    private Integer productId;

    private Integer accountId;

}
