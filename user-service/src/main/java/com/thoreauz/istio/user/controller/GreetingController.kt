package com.thoreauz.istio.user.controller

import com.thoreauz.istio.user.entity.Greeting
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.InetAddress
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.atomic.AtomicLong

/**
 * 2018/10/5 上午11:11.
 * @author zhaozhou
 */
@RestController
class GreetingController {
    val counter = AtomicLong()
    var inetAddress = InetAddress.getLocalHost()
    @GetMapping("/user/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) : Greeting {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        val time = LocalDateTime.now().format(formatter);
        return Greeting(counter.incrementAndGet(),
                "Hello, $name", inetAddress.hostAddress
                , inetAddress.hostName
                , time)

    }

}