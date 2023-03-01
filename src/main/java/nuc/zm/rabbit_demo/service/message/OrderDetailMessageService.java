package nuc.zm.rabbit_demo.service.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import nuc.zm.rabbit_demo.common.OrderStatus;
import nuc.zm.rabbit_demo.dto.OrderMessageDTO;
import nuc.zm.rabbit_demo.mapper.OrderDetailMapper;
import nuc.zm.rabbit_demo.po.OrderDetailPo;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * 订单详细信息服务
 * 处理 消息 相关业务逻辑
 *
 * @author zm
 * @date 2023/03/01
 */
@Slf4j
public class OrderDetailMessageService {

    @Resource
    private ObjectMapper jsonUtil;

    @Resource
    private OrderDetailMapper orderDetailMapper;

    /**
     * 处理消息 : 声明消息队列 、 交换机 、 绑定、消息的处理
     */
    @Async // 异步线程调用这个方法。
    public  void handleMessage() throws IOException, TimeoutException {
//        更底层的 connectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            /*  餐厅微服务 从订单微服务接收消息 ， 声明队列
             *    骑手微服务  从订单微服务接收消息， 声明队列
             * */
            String exchangeName = "exchange.order.restaurant";
//       设置交换机 交换机名称，交换机类型，是否持久化，是否自动删除、argument 特殊属性
            channel.exchangeDeclare(exchangeName ,BuiltinExchangeType.DIRECT, true, false, null);
            String queueName = "queue.order";

            /*骑手微服务*/
            String deliveryManExchange = "exchange.order.delveryman";
//       设置交换机 交换机名称，交换机类型，是否持久化，是否自动删除、argument 特殊属性
            channel.exchangeDeclare(deliveryManExchange ,BuiltinExchangeType.DIRECT, true, false, null);
            String deliveryManQueue = "queue.deliveryman";
//        创建队列 队列名称 ，是否持久化 , 是否独占信道、是否自动删除 ， 特殊属性
            channel.queueDeclare(deliveryManQueue, true, false, false, null);


            String routingKey = "key.order";
            channel.queueBind(queueName,exchangeName,routingKey);

//            ack
            channel.basicConsume("queue.order",true,deliverCallback,consumerTag -> {});

            while (true){
                Thread.sleep(1000000);
            }

        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            assert channel != null;
            channel.close();
            connection.close();
        }



    }

//    FunctionalInterface 函数式接口  设置 在哪个队列中  消费者取值 进入监听状态。
    DeliverCallback deliverCallback = (consumerTag, message) -> {
    String messageBody = new String(message.getBody());
//        发送消息 要经过 connection 获得一个connectionFactory
    ConnectionFactory connectionFactory = new ConnectionFactory();
//        由于实现了 ShutdownNotifier, Closeable 接口 使用新特性 接收到消息了~ 处理消息
    OrderMessageDTO orderMessageDTO = jsonUtil.readValue(messageBody, OrderMessageDTO.class);
    OrderDetailPo po = orderDetailMapper.selectOderDetailById(orderMessageDTO.getOrderId());

    /*
     *
     * 忽略了这个问题 如何知道 消息是谁发来的？ ？  ？ 可以通过这个字段 consumerTag
     * 但是 大可不必 ： 根据数据库的 字敦状态 通过业务流程确定
     *
     * */

    OrderStatus status = po.getStatus();
    switch (status) {
        case ORDER_CREATING:
            if (orderMessageDTO.getConfirmed() && null != orderMessageDTO.getPrice()) {
                po.setStatus(OrderStatus.RESTAURANT_CONFIRMED);
                po.setPrice(orderMessageDTO.getPrice());
                orderDetailMapper.updateOrderDetail(po);
           
            try (Connection connection = connectionFactory.newConnection();
                 Channel channel = connection.createChannel();
                 ) {
                String s = jsonUtil.writeValueAsString(orderMessageDTO);
                channel.basicPublish("exchange.order.deliveryman",
                                        "key.deliveryman",
                                        null,
                                        s.getBytes()
                        );
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            }
           
            break;
        case FAILED:
            break;
        case SETTLEMENT_CONFIRMED:
            break;
        case RESTAURANT_CONFIRMED:
            break;
        case DELIVERYMAN_CONFIRMED:
            break;
    }

};

//    public static void main(String[] args) throws IOException, TimeoutException {
//        handleMessage();
//    }
}
