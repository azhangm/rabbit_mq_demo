package nuc.zm.rabbit_demo.controller;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import nuc.zm.rabbit_demo.service.OrderDetailService;
import nuc.zm.rabbit_demo.vo.OrderCreateVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 订单详细信息有关控制器
 *
 * @author zm
 * @date 2023/03/01
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderDetailController {

    @Resource
    private OrderDetailService service;

    @PostMapping("/add-order")
    public void createOrder(@RequestBody OrderCreateVo vo) throws IOException, TimeoutException {
        log.info("createOrder:OrderCreateVo:{}"  , vo.toString());
        service.createOrder(vo);
    }
}
