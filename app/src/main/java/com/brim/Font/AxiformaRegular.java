package com.brim.Font;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by apple on 18/05/17.
 */

public class AxiformaRegular extends AppCompatTextView {

    public AxiformaRegular(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    public AxiformaRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AxiformaRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {

        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                "Axiforma-Regular.ttf"));

    }




    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if(focused)
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    public void onWindowFocusChanged(boolean focused) {
        if(focused)
            super.onWindowFocusChanged(focused);
    }


    @Override
    public boolean isFocused() {
        return true;
    }

}
