package com.thoreauz.istio.ui.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


/**
 * 2018/10/5 上午10:40.
 * @author zhaozhou
 */
@Service
class UserService {
    @Autowired
    val restTemplate: RestTemplate? = null

    @Value("\${user-service.endpoint}")
    val endpoint: String? = null

    fun getUser(): User {
        return restTemplate!!.getForObject("$endpoint/user/user", User::class.java)
    }
}