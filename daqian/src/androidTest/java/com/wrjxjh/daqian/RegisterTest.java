package com.wrjxjh.daqian;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.wrjxjh.daqian.core.register.RegisterActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * by wangrongjun on 2017/3/10.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterTest {

    @Rule
    public ActivityTestRule<RegisterActivity> rule = new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void testRegister() {
        //输入用户名
        onView(withId(R.id.et_username)).perform(
                click(),
                replaceText("王荣俊"),
                closeSoftKeyboard()
        );
        //输入密码
        onView(withId(R.id.et_password)).perform(
                click(),
                replaceText("123"),
                closeSoftKeyboard()
        );
        //再次输入密码
        onView(withId(R.id.et_confirm_password)).perform(
                click(),
                replaceText("123"),
                closeSoftKeyboard()
        );
        //输入昵称
        onView(withId(R.id.et_nickname)).perform(
                click(),
                replaceText("王宝宝"),
                closeSoftKeyboard()
        );
        //设置性别
        onView(withId(R.id.rb_woman)).perform(
                click()
        );
        //点击注册
        onView(withId(R.id.btn_register)).perform(
                click()
        );
    }

}
