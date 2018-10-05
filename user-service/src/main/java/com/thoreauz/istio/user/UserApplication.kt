package com.thoreauz.istio.user

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * 2018/9/3 下午11:13.
 * @author zhaozhou
 */

@SpringBootApplication
class UserApplication
fun main(args: Array<String>) {
    SpringApplication.run(UserApplication::class.java, *args)
}