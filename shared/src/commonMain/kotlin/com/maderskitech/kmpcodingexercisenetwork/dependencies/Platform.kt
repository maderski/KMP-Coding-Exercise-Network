package com.maderskitech.kmpcodingexercisenetwork.dependencies

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform