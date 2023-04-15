package com.hasan.bestclothesforyou.data


data class Request(
    val type: String,
    val query: String,
    val language: String,
    val unit: String
)
