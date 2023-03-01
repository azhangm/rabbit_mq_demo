package nuc.zm.rabbit_demo.service.message;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 处理 消息 相关业务逻辑
 *
 * @author zm
 * @date 2023/03/01
 */
public class OrderDetailMessageService {

    /**
     * 处理消息 : 声明消息队列 、 交换机 、 绑定、消息的处理
     */
    public static void handleMessage() throws IOException, TimeoutException {
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
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            assert channel != null;
            channel.close();
            connection.close();
        }




    }

    public static void main(String[] args) throws IOException, TimeoutException {
        handleMessage();
    }
}
