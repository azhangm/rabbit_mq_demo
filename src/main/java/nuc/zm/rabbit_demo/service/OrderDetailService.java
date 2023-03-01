package nuc.zm.rabbit_demo.service;

import nuc.zm.rabbit_demo.vo.OrderCreateVo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface OrderDetailService {
    void createOrder(OrderCreateVo vo) throws IOException, TimeoutException;
}
