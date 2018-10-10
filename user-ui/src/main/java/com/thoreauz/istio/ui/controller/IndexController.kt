package com.thoreauz.istio.ui.controller

import com.thoreauz.istio.ui.service.User
import com.thoreauz.istio.ui.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

/**
 * 2018/10/5 上午10:12.
 * @author zhaozhou
 */
@RestController
@RequestMapping("/user")
class IndexController {
    @Autowired
    val restTemplate: RestTemplate? = null

    @Autowired
    val userService: UserService? = null

    @RequestMapping("/")
    fun doGet() :String{
        return "index"
    }
    @RequestMapping("/greet")
    fun greet(@RequestParam name:String) :Object{
        return restTemplate!!.getForObject("http://user-service/user/greeting?name="+name, Object::class.java)
    }
    @RequestMapping("/user")
    fun getUser() : User {
        return userService!!.getUser()
    }

}