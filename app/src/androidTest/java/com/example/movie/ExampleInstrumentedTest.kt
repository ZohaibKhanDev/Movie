package com.example.movie

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movie.test.Counter

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun clickButton(){
        composeTestRule.setContent {
            Counter()
        }

        composeTestRule.onNodeWithText("Click Me").performClick()
        composeTestRule.onNodeWithText("Click Me").performClick()
        composeTestRule.onNodeWithText("Count: 2").assertIsDisplayed()
    }
}