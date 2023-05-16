package com.github.bkhablenko.service

interface PopularToppingService {

    fun countDistinctEmailsByTopping(): Map<String, Int>
}
