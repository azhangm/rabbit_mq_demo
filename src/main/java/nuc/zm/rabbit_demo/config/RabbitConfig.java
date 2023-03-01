package nuc.zm.rabbit_demo.config;


import nuc.zm.rabbit_demo.service.message.OrderDetailMessageService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class RabbitConfig {

    @Resource
    private OrderDetailMessageService orderDetailMessageService;

    @Resource
    public void startListenMessage() throws IOException, TimeoutException {
        orderDetailMessageService.handleMessage();
    }
}
