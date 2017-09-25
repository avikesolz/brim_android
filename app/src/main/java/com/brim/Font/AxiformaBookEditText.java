package com.brim.Font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by apple on 18/08/17.
 */

public class AxiformaBookEditText extends AppCompatEditText {


    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public AxiformaBookEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public AxiformaBookEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public AxiformaBookEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.attrs = attrs;
        this.defStyle = defStyle;
        init();
    }

    private void init() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Axiforma-Book.ttf");
        this.setTypeface(font);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "Axiforma-Book.ttf");
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "Axiforma-Book.ttf");
        super.setTypeface(tf);
    }
}
