package com.github.esdemo.repository;

import com.github.esdemo.entity.Order;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OrderRepository extends ElasticsearchRepository<Order, String> {

}
