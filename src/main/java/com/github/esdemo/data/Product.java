package com.github.esdemo.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Product {
  private String productId;
  private String productName;
  private double productPrice;
}
