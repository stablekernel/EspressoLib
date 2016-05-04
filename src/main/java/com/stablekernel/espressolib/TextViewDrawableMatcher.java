package com.stablekernel.espressolib;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class TextViewDrawableMatcher extends TypeSafeMatcher<View> {

    private final int expectedId;
    private final Direction direction;
    String resourceName;

    public enum Direction {
        LEFT(0),
        TOP(1),
        RIGHT(2),
        BOTTOM(3);

        private int value;

        Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public TextViewDrawableMatcher(int expectedId, Direction direction) {
        super(View.class);
        this.expectedId = expectedId;
        this.direction = direction;
    }

    @Override
    protected boolean matchesSafely(View target) {
        if (!(target instanceof TextView)){
            return false;
        }
        TextView textView = (TextView) target;
        if (expectedId < 0){
            return textView.getCompoundDrawables()[direction.getValue()] == null;
        }
        Resources resources = target.getContext().getResources();
        Drawable expectedDrawable = resources.getDrawable(expectedId);
        resourceName = resources.getResourceEntryName(expectedId);

        if (expectedDrawable == null) {
            return false;
        }

        Bitmap bitmap = ((BitmapDrawable) textView.getCompoundDrawables()[direction.getValue()]).getBitmap();
        Bitmap otherBitmap = ((BitmapDrawable) expectedDrawable).getBitmap();
        return bitmap.sameAs(otherBitmap);
    }


    @Override
    public void describeTo(Description description) {
        description.appendText("with drawable from resource id: ");
        description.appendValue(expectedId);
        if (resourceName != null) {
            description.appendText("[");
            description.appendText(resourceName);
            description.appendText("]");
        }
    }
}
