package com.example.versionci_cd

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    private var activity: Activity? = null

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity {
            activity = it
        }
    }

    @Test
    fun login_with_empty_username_shows_error_toast() {
        onView(withId(R.id.username)).perform(typeText(""), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("1234"), closeSoftKeyboard())
        onView(withId(R.id.login_button)).perform(click())

        // Wait for Toast to show
        Thread.sleep(1500)

        onView(withText("Username or password cannot be empty"))
            .inRoot(withDecorView(not(`is`(activity?.window?.decorView))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun login_with_invalid_credentials_shows_error_toast() {
        onView(withId(R.id.username)).perform(typeText("wrongUser"), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("wrongPass"), closeSoftKeyboard())
        onView(withId(R.id.login_button)).perform(click())

        // Wait for Toast to show
        Thread.sleep(1500)

        onView(withText("Invalid credentials"))
            .inRoot(withDecorView(not(`is`(activity?.window?.decorView))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun login_with_correct_credentials_does_not_show_error_toast() {
        onView(withId(R.id.username)).perform(typeText("admin"), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("1234"), closeSoftKeyboard())
        onView(withId(R.id.login_button)).perform(click())

        // Wait in case any unexpected toast appears
        Thread.sleep(1500)

        onView(withText("Invalid credentials"))
            .inRoot(withDecorView(not(`is`(activity?.window?.decorView))))
            .check(doesNotExist())
    }
}

