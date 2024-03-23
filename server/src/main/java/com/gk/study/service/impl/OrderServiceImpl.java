package com.gk.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.study.entity.Order;
import com.gk.study.entity.Thing;
import com.gk.study.mapper.ThingMapper;
import com.gk.study.service.OrderService;
import com.gk.study.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    OrderMapper mapper;
    @Autowired
    ThingMapper thingMapper;

    @Override
    public List<Order> getOrderList() {
        return mapper.getList();
    }

    @Override
    public void createOrder(Order order) {
        long ct = System.currentTimeMillis();
        order.setOrderTime(String.valueOf(ct));
        order.setOrderNumber(String.valueOf(ct));
        order.setStatus("1");
        mapper.insert(order);
    }

    @Override
    public void deleteOrder(String id) {
        mapper.deleteById(id);
    }

    @Override
    public void updateOrder(Order order) {
//        mapper.updateById(order);
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", order.getId()).set("status", order.getStatus()); // 只更新指定的column_name字段
        mapper.update(null, updateWrapper);
    }

    @Override
    public List<Order> getUserOrderList(String userId, String status) {
        return mapper.getUserOrderList(userId, status);
//        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id", userId);
//        if (StringUtils.isNotBlank(status)) {
//            queryWrapper.eq("status", status);
//        }
//        queryWrapper.orderBy(true, false, "order_time");
//        return mapper.selectList(queryWrapper);
    }

    @Override
    public List<Order> getUserOrderListBySellerId(int sellerId) {
//        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("seller_id", sellerId);
//        queryWrapper.orderBy(true, false, "order_time");
//        return mapper.selectList(queryWrapper);
        return mapper.getUserOrderListBySellerId(sellerId);
    }

    @Override
    public void updateOrderExpressAndStatus(Order order) {
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", order.getId()).set("status", order.status).set("express", order.express); // 只更新指定的column_name字段
        mapper.update(null, updateWrapper);
    }

    @Override
    public int updateOrderPayStatus(String userId, String thingId, int count) {
        long ct = System.currentTimeMillis();
        // 找到该用户该商品的最新的订单
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("thing_id", thingId);
        List<Order> list = mapper.selectList(queryWrapper);
        Order order = new Order();
        if(list != null){
            for(Order o : list){
                order = o;
            }
            // 在商品表中减去该商品的数量
            Thing thing = thingMapper.selectById(order.getThingId());
            if(thing != null){
                int count1 = Integer.parseInt(thing.getRepertory()) - count;
                if(count >= 0){
                    UpdateWrapper<Thing> updateWrapper1 = new UpdateWrapper<>();
                    updateWrapper1.eq("id", order.getThingId()).set("repertory", count1);
                    thingMapper.update(null, updateWrapper1);
                }
                else {
                    return 0;
                }
            }
            // 把该订单的status改为2，并且添加支付时间
            order.setStatus("2");
            UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", order.getId()).set("status", order.getStatus()).set("pay_time", String.valueOf(ct));
            mapper.update(null, updateWrapper);
            return 1;
        }
        return 0;
    }
}
