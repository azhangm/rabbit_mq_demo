package nuc.zm.rabbit_demo.dto;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

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

    private Boolean Confirmed;

    private BigDecimal price;
}
