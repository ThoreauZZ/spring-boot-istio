package com.thoreauz.istio.ui

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * 2018/10/5 上午2:17.
 * @author zhaozhou
 */
@SpringBootApplication
class UIApplication
fun main(args: Array<String>) {
    SpringApplication.run(UIApplication::class.java, *args)
}