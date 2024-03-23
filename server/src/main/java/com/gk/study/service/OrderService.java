package com.gk.study.service;


import com.gk.study.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrderList();
    void createOrder(Order order);
    void deleteOrder(String id);

    void updateOrder(Order order);

    List<Order> getUserOrderList(String userId, String status);

    List<Order> getUserOrderListBySellerId(int sellerId);

    void updateOrderExpressAndStatus(Order order);

    int updateOrderPayStatus(String userId, String thingId, int count);
}
