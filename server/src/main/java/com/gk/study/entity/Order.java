package com.gk.study.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("b_order")
public class Order implements Serializable {
    @Excel(name="订单id")
    @TableId(value = "id",type = IdType.AUTO)
    public Long id;

    @Excel(name="订单状态")
    @TableField
    public String status;

    @Excel(name="订单时间")
    @TableField
    public String orderTime;

    @Excel(name="支付时间")
    @TableField
    public String payTime;

    @Excel(name="商品id")
    @TableField
    public String thingId;

    @Excel(name="下单用户id")
    @TableField
    public String userId;

    @Excel(name="商品数量")
    @TableField
    public String count;

    @Excel(name="订单编号")
    @TableField
    public String orderNumber; // 订单编号

    @Excel(name="收件地址")
    @TableField
    public String receiverAddress;

    @Excel(name="收件人")
    @TableField
    public String receiverName;

    @Excel(name="收件人电话")
    @TableField
    public String receiverPhone;

    @Excel(name="订单备注")
    @TableField
    public String remark;


    @TableField
    public int sellerId;

    @Excel(name="快递单号")
    @TableField
    public String express;

    @TableField(exist = false)
    public String username; // 用户名
    @TableField(exist = false)
    public String title; // 商品名称
    @TableField(exist = false)
    public String cover; // 商品封面
    @TableField(exist = false)
    public String price; // 商品价格

}
