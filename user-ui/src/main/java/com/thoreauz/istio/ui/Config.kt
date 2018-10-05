package com.thoreauz.istio.ui

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate



/**
 * 2018/10/5 上午10:38.
 * @author zhaozhou
 */
@Configuration
class Config {
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}
