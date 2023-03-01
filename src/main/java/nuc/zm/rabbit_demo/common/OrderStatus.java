package nuc.zm.rabbit_demo.common;

public enum OrderStatus {

    /**
     * 订单创建
     */
    ORDER_CREATING,
    /**
     * 餐厅确认
     */
    RESTAURANT_CONFIRMED,

    /**
     * 送货人确认
     */
    DELIVERYMAN_CONFIRMED,

    /**
     * 结算确认
     */
    SETTLEMENT_CONFIRMED,

    /**
     * 订单创建
     */
    ORDER_CREATED,

    /**
     * 失败
     */
    FAILED
}
