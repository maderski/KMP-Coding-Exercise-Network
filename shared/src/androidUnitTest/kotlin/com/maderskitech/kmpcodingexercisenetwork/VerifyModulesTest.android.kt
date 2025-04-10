package com.maderskitech.kmpcodingexercisenetwork

import com.maderskitech.kmpcodingexercisenetwork.di.platformModule
import com.maderskitech.kmpcodingexercisenetwork.di.commonModule
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class AndroidVerifyModulesTest : KoinTest {
    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkPlatformModule() {
        platformModule.verify()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkCommonModule() {
        commonModule.verify()
    }
}