package com.stablekernel.espressolib;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.v7.app.AppCompatActivity;

import com.stablekernel.espressolib.TextViewDrawableMatcher.Direction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.stablekernel.espressolib.MatcherViews.getViewWithParent;
import static com.stablekernel.espressolib.MatcherViews.withDrawable;
import static com.stablekernel.espressolib.MatcherViews.withTextDrawable;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

public class ViewInteractions {

    public static ViewInteraction clickRes(int resId) {
        return onView(withId(resId))
                       .perform(click());
    }

    public static ViewInteraction clickText(String text) {
        return onView(withText(text))
                .perform(click());
    }

    public static ViewInteraction clickText(int resId) {
        return onView(withText(resId))
                .perform(click());
    }

    public static ViewInteraction clickDescription(int resId) {
        return onView(withContentDescription(resId))
                .perform(click());
    }

    public static ViewInteraction checkDisplayed(int resId) {
        return onView(withId(resId))
                       .check(matches(isDisplayed()));
    }

    public static ViewInteraction checkDisplayed(int resId, int parentId) {
        return onView(getViewWithParent(resId, parentId))
                       .check(matches(isDisplayed()));
    }

    public static ViewInteraction checkNotDisplayed(int resId) {
        return onView(withId(resId))
                       .check(matches(not(isDisplayed())));
    }

    public static ViewInteraction checkTextDisplayed(int resId, String text) {
        return onView(allOf(withId(resId), withText(text)))
                .check(matches(isDisplayed()));
    }

    public static ViewInteraction toastShowsText(final int textId, AppCompatActivity activity) {
        return onView(withText(textId))
                       .inRoot(withDecorView(not(activity.getWindow().getDecorView())))
                       .check(matches(isDisplayed()));
    }

    public static ViewInteraction perform(final int resId, ViewAction... viewActions) {
        return onView(withId(resId))
                       .perform(viewActions);
    }

    public static ViewInteraction checkImageViewHasImageIsDisplayed(int resId, int drawableId) {
        return onView(withId(resId))
                       .check(matches(allOf(withDrawable(drawableId), isDisplayed())));
    }

    public static ViewInteraction checkText(int resId, String text) {
        return onView(withId(resId))
                       .check(matches(withText(text)));
    }

    public static ViewInteraction checkTextViewHasDrawableIsDisplayed(int resId, int drawableId, Direction direction) {
        return onView(withId(resId))
                .check(matches(allOf(withTextDrawable(drawableId, direction), isDisplayed())));
    }
}
