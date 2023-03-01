package nuc.zm.rabbit_demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import nuc.zm.rabbit_demo.common.OrderStatus;
import nuc.zm.rabbit_demo.dto.OrderMessageDTO;
import nuc.zm.rabbit_demo.mapper.OrderDetailMapper;
import nuc.zm.rabbit_demo.po.OrderDetailPo;
import nuc.zm.rabbit_demo.service.OrderDetailService;
import nuc.zm.rabbit_demo.vo.OrderCreateVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * 订单细节服务impl
 *  处理用户关于订单的业务请求。
 * @author zm
 * @date 2023/03/01
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
//        vo  跟前端进行交互的对象


    @Resource
    OrderDetailMapper mapper;

    @Resource
    ObjectMapper jsonutil = new ObjectMapper();

    @Override
    public void createOrder(OrderCreateVo vo) throws IOException, TimeoutException {
        OrderDetailPo po = new OrderDetailPo();
        BeanUtils.copyProperties(vo,po);
        po.setStatus(OrderStatus.ORDER_CREATING);
        po.setDate(new Date());
        mapper.insertOrderDetail(po);

//        封装发送的 消息体 传输数据  用 json
        OrderMessageDTO orderMessageDTO = new OrderMessageDTO();
        orderMessageDTO.setOrderId(po.getId());
        orderMessageDTO.setAccountId(po.getAccountId());
        orderMessageDTO.setProductId(po.getProductId());

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
       try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
           String s = jsonutil.writeValueAsString(orderMessageDTO);
//           根据ampq协议 我们发送数据
//           String exchange, String routingKey, BasicProperties props, byte[] body
           channel.basicPublish(
                   "exchange.order.restaurant",
//                   routing key 绑定在交换机上
                   "key.restaurant",null,s.getBytes(StandardCharsets.UTF_8));
       }
    }
}
