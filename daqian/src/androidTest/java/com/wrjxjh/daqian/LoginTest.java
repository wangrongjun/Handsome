package com.wrjxjh.daqian;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.wrjxjh.daqian.core.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {

    @Rule //指定待测试的Activity
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testLogin() {

        onView(withId(R.id.et_login_username)).perform(
                click(),
                replaceText("王荣俊"),
                closeSoftKeyboard()
        );
        onView(withId(R.id.et_login_username)).check(matches(withText("王荣俊")));

        onView(withId(R.id.et_login_password)).perform(
                click(),
                replaceText("123456"),
                closeSoftKeyboard()
        );
        onView(withId(R.id.et_login_password)).check(matches(withText("123456")));

        onView(withId(R.id.btn_login)).perform(
                click()
        );

    }
}