package com.github.esdemo.controller;

import com.github.esdemo.dto.SumOrderAmountDto;
import com.github.esdemo.entity.Order;
import com.github.esdemo.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
public class OrderController {

  @Autowired
  private OrderService orderService;

  @ApiOperation("创建订单")
  @GetMapping("/order/create")
  public Flux<Order> create() {
    return Flux.fromIterable(orderService.testCreateOrder());
  }

  @ApiOperation("产品ID查询相关订单")
  @GetMapping("/orders/productId/{productId}")
  public Flux<Order> findByProductId(@PathVariable String productId) {
    return Flux.fromIterable(orderService.findByProductId(productId));
  }

  @ApiOperation("产品名称查询相关订单")
  @GetMapping("/orders/productName/{productName}")
  public Flux<Order> findByProductName(@PathVariable String productName) {
    return Flux.fromIterable(orderService.findByProductName(productName));
  }

  @ApiOperation("根据卖家ID统计订单总额")
  @GetMapping("/orders/aggregation/sellerId/orderAmount")
  public Flux<SumOrderAmountDto> sumOrderAmountBySellerId() {
    return Flux.fromIterable(orderService.sumOrderAmountBySellerId());
  }

}
