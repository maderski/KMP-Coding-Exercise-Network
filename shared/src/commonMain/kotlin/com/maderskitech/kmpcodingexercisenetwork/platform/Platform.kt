package com.maderskitech.kmpcodingexercisenetwork.platform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform