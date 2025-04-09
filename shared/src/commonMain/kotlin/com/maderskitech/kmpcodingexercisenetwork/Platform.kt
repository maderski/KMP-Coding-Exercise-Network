package com.maderskitech.kmpcodingexercisenetwork

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform