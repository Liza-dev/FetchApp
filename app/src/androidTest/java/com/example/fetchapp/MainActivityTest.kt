package com.example.fetchapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fetchapp.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testExpandableListViewIsDisplayed() {
        onView(withId(R.id.expandableListView))
            .check(matches(isDisplayed()))

    }

    @Test
    fun toolbarDisplaysBackButtonAndNavigatesBackOnClicked() {
        onView(androidx.test.espresso.matcher.ViewMatchers.withContentDescription("Navigate up")).check(
            matches(isDisplayed())
        )
    }


}