package com.thoreauz.istio.user.controller

import com.thoreauz.istio.user.entity.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 2018/10/5 上午2:53.
 * @author zhaozhou
 */
@RestController
class UserResource {
    @GetMapping("/user")
    fun doGet(@RequestParam(required = false) name: String) : User {
        return User(1, "thoreau")
    }
}