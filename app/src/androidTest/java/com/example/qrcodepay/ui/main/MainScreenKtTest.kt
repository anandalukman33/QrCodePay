package com.example.qrcodepay.ui.main

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltViewModel
class MainScreenKtTest {

    @JvmField
    @Rule
    val composeTestRule = createComposeRule()
    val hiltViewModel = HiltAndroidRule(this)

    lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        hiltViewModel.inject()

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            BaseScreen(navController = navController)
        }
    }

    @Test
    fun check_box() {
        composeTestRule.onNodeWithText("Box Main Screen").assertExists()
    }


}