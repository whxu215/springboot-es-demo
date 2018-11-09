package com.github.esdemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Builder
@Document(indexName = "order", type = "doc")
public class Order {

  @Id
  private String orderId;
  //商家ID
  private String sellerId;
  //买家ID
  private String buyerId;
  //订单金额
  @Field(type = FieldType.Double)
  private double orderAmount;
  private String payChannel;
  private OrderStatus orderStatus;
  //下单时间
  @Field(type = FieldType.Date, store = true, format = DateFormat.custom,
      pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date orderTime;
  @Field(type = FieldType.Date, store = true, format = DateFormat.custom,
      pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date deliverTime;
  @Field(type = FieldType.Date, store = true, format = DateFormat.custom,
      pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date confirmTime;
  //收货时间
  //发货时间
  //订单产品详情
  @Field(type = FieldType.Object)
  private List<OrderDetail> products;

  @Getter
  @Setter
  @Builder
  public static class OrderDetail {

    private String productId;
    private String productName;
    @Field(type = FieldType.Double)
    private double productPrice;
  }

  public enum OrderStatus {
    // 未支付
    NotPayed,
    // 已支付
    Payed,
    // 已发货
    Delivered,
    // 已收货
    Confirmed
  }
}
