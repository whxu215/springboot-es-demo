package com.github.esdemo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SumOrderAmountDto {
  private String sellerId;
  private double totalAmount;
}
