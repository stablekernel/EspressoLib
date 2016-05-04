package com.stablekernel.espressolib;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.stablekernel.espressolib.TextViewDrawableMatcher.Direction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

public class MatcherViews {

    @NonNull
    public static Matcher<View> getViewWithParent(int resId, int parentId) {
        return allOf(withId(resId), withParent(withId(parentId)));
    }

    public static Matcher<View> getViewWithSibling(int resId, Matcher<View> sibling) {
        return allOf(withId(resId), hasSibling(sibling));
    }

    public static Matcher<View> nthChildOfViewGroup(final int resId,
                                                    final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if (item.getParent() instanceof ViewGroup) {
                    ViewGroup group = (ViewGroup) item.getParent();
                    return withId(resId).matches(item.getParent())
                            && item.equals(group.getChildAt(childPosition));
                }

                return withId(resId).matches(item.getParent());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with " + childPosition + " child view of type parentMatcher");
            }
        };
    }

    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    public static Matcher<View> noDrawable() {
        return new DrawableMatcher(-1);
    }

    public static Matcher<View> withTextDrawable(final int resId, Direction direction) {
        return new TextViewDrawableMatcher(resId, direction);
    }

}
