package com.github.bkhablenko.web.controller

import com.github.bkhablenko.service.PopularToppingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/popular/toppings")
class PopularToppingController(private val popularToppingService: PopularToppingService) {

    @GetMapping
    fun getPopularToppings(): Map<String, Int> {
        return popularToppingService.countDistinctEmailsByTopping()
    }
}
