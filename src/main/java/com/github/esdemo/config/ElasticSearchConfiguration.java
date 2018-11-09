package com.github.esdemo.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfiguration {

  static {
    System.setProperty("es.set.netty.runtime.available.processors", "false");
  }
}
