package com.github.esdemo.service;

import com.github.esdemo.data.Product;
import com.github.esdemo.dto.SumOrderAmountDto;
import com.github.esdemo.entity.Order;
import com.github.esdemo.entity.Order.OrderDetail;
import com.github.esdemo.entity.Order.OrderStatus;
import com.github.esdemo.repository.OrderRepository;
import com.github.esdemo.service.data.OrderData;
import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService implements OrderData {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ElasticsearchTemplate elasticsearchTemplate;

  public List<Order> testCreateOrder() {
    return IntStream.rangeClosed(1, 10).mapToObj(this::createOrder).collect(Collectors.toList());
  }

  private Order createOrder(int index) {
    return orderRepository.save(buildOrder(index));
  }

  private Order buildOrder(int index) {
    return Order.builder()
        .buyerId(RandomStringUtils.randomAlphanumeric(10))
        .orderId(RandomStringUtils.randomNumeric(8) + index)
        .orderTime(new Date())
        .orderAmount(100)
        .payChannel(PAY_CHANNELS.get(RandomUtils.nextInt(PAY_CHANNELS.size() - 1)))
        .sellerId(SELLER_IDS.get(RandomUtils.nextInt(SELLER_IDS.size() - 1)))
        .orderStatus(OrderStatus.values()[RandomUtils.nextInt(OrderStatus.values().length - 1)])
        .products(buildProducts())
        .build();
  }

  private List<OrderDetail> buildProducts() {
    return Lists.newArrayList(product2OrderDetail());
  }

  private OrderDetail product2OrderDetail() {
    Product product = PRODUCTS.get(RandomUtils.nextInt(PRODUCTS.size() - 1));
    return OrderDetail.builder().productId(product.getProductId())
        .productName(product.getProductName())
        .productPrice(product.getProductPrice())
        .build();
  }

  public List<Order> findByProductId(String productId) {
    SearchQuery query = new NativeSearchQueryBuilder()
        .withQuery(new MatchQueryBuilder("products.productId", productId)).build();
    return elasticsearchTemplate.queryForList(query, Order.class);
  }

  public List<Order> findByProductName(String productName) {
    SearchQuery query = new NativeSearchQueryBuilder()
        .withQuery(new MatchQueryBuilder("products.productName", productName)).build();
    return elasticsearchTemplate.queryForList(query, Order.class);
  }

  public List<SumOrderAmountDto> sumOrderAmountBySellerId() {
    SearchQuery query = new NativeSearchQueryBuilder()
        .withIndices("order")
        .withTypes("doc")
        .addAggregation(AggregationBuilders.terms("sellerId").field("sellerId.keyword")
            .subAggregation(AggregationBuilders.sum("sumOrderAmount").field("orderAmount")))
        .build();

    Aggregations results = elasticsearchTemplate
        .query(query, (response) -> response.getAggregations());
    return ((StringTerms) results.asList().get(0)).getBuckets()
        .stream()
        .map(this::buildSumOrderAmoutDto)
        .collect(Collectors.toList());
  }

  private SumOrderAmountDto buildSumOrderAmoutDto(Bucket bucket) {
    return SumOrderAmountDto.builder()
        .sellerId(bucket.getKeyAsString()).totalAmount(bucket.getAggregations().asList().stream()
            .map(agg -> ((InternalSum) agg).getValue()).reduce(0D, Double::sum)).build();
  }
}
