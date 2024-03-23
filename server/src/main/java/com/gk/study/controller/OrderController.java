package com.gk.study.controller;

import com.gk.study.common.APIResponse;
import com.gk.study.common.ResponeCode;
import com.gk.study.entity.Order;
import com.gk.study.permission.Access;
import com.gk.study.permission.AccessLevel;
import com.gk.study.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService service;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public APIResponse list(){
        List<Order> list =  service.getOrderList();

        return new APIResponse(ResponeCode.SUCCESS, "查询成功", list);
    }

    // 用户订单
    @RequestMapping(value = "/userOrderList", method = RequestMethod.GET)
    public APIResponse userOrderList(String userId, String status){
        List<Order> list =  service.getUserOrderList(userId, status);
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", list);
    }

    // 根据卖家id查询用户订单
    @RequestMapping(value = "/userOrderListBySellerId", method = RequestMethod.GET)
    public APIResponse userOrderListBySellerId(int sellerId){
        List<Order> list =  service.getUserOrderListBySellerId(sellerId);
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", list);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Transactional
    public APIResponse create(Order order) throws IOException {
        service.createOrder(order);
        return new APIResponse(ResponeCode.SUCCESS, "创建成功");
    }

    @Access(level = AccessLevel.ADMIN)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public APIResponse delete(String ids){
        System.out.println("ids===" + ids);
        // 批量删除
        String[] arr = ids.split(",");
        for (String id : arr) {
            service.deleteOrder(id);
        }
        return new APIResponse(ResponeCode.SUCCESS, "删除成功");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public APIResponse update(Order order) throws IOException {
        service.updateOrder(order);
        return new APIResponse(ResponeCode.SUCCESS, "更新成功");
    }

    @Access(level = AccessLevel.ADMIN)
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @Transactional
    public APIResponse cancelOrder(Long id) throws IOException {
        Order order = new Order();
        order.setId(id);
        order.setStatus("7"); // 7=取消
        service.updateOrder(order);
        return new APIResponse(ResponeCode.SUCCESS, "取消成功");
    }

    @Access(level = AccessLevel.LOGIN)
    @RequestMapping(value = "/cancelUserOrder", method = RequestMethod.POST)
    @Transactional
    public APIResponse cancelUserOrder(Long id) throws IOException {
        Order order = new Order();
        order.setId(id);
        order.setStatus("7"); // 7=取消
        service.updateOrder(order);
        return new APIResponse(ResponeCode.SUCCESS, "取消成功");
    }


    @Access(level = AccessLevel.LOGIN)
    @RequestMapping(value = "/successUserOrder", method = RequestMethod.POST)
    @Transactional
    public APIResponse successUserOrder(Long id) throws IOException {
        Order order = new Order();
        order.setId(id);
        order.setStatus("2"); // 2=支付成功
        service.updateOrder(order);
        return new APIResponse(ResponeCode.SUCCESS, "支付成功");
    }

    // 填写快递单号，并把订单状态改为已发货即status=3
    @Access(level = AccessLevel.LOGIN)
    @RequestMapping(value = "/updateOrderExpressAndStatus", method = RequestMethod.POST)
    @Transactional
    public APIResponse updateOrderExpressAndStatus(Long id, String express){
        Order order = new Order();
        order.setId(id);
        order.setExpress(express);
        order.setStatus("3"); // 3=已发货
        service.updateOrderExpressAndStatus(order);
        return new APIResponse(ResponeCode.SUCCESS, "发货成功");
    }

    // 根据买家id和商品id查找最新的订单，然后把订单状态改为已支付即status=2,并且添加支付时间，同时减去商品数量
    @Access(level = AccessLevel.LOGIN)
    @RequestMapping(value = "/updateOrderPayStatus", method = RequestMethod.POST)
    @Transactional
    public APIResponse updateOrderPayStatus(String userId, String thingId, int count){
        service.updateOrderPayStatus(userId, thingId, count);
        return new APIResponse(ResponeCode.SUCCESS, "支付成功");
    }

}
