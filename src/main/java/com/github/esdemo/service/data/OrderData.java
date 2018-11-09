package com.github.esdemo.service.data;

import com.github.esdemo.data.Product;
import com.google.common.collect.Lists;
import java.util.List;
import org.apache.commons.lang.RandomStringUtils;

public interface OrderData {

  List<String> PAY_CHANNELS = Lists.newArrayList("wxpay", "alipay");
  List<String> SELLER_IDS = Lists.newArrayList(
      RandomStringUtils.randomNumeric(10),
      RandomStringUtils.randomNumeric(10),
      RandomStringUtils.randomNumeric(10),
      RandomStringUtils.randomNumeric(10),
      RandomStringUtils.randomNumeric(10));
  List<Product> PRODUCTS = Lists.newArrayList(
      Product.builder()
          .productId(RandomStringUtils.randomNumeric(10))
          .productName("测试产品1")
          .productPrice(10.2).build(),
      Product.builder()
          .productId(RandomStringUtils.randomNumeric(10))
          .productName("测试产品2")
          .productPrice(100.25).build(),
      Product.builder()
          .productId(RandomStringUtils.randomNumeric(10))
          .productName("测试产品3")
          .productPrice(20).build(),
      Product.builder()
          .productId(RandomStringUtils.randomNumeric(10))
          .productName("测试产品4")
          .productPrice(78).build(),
      Product.builder()
          .productId(RandomStringUtils.randomNumeric(10))
          .productName("测试产品5")
          .productPrice(35).build());
}
