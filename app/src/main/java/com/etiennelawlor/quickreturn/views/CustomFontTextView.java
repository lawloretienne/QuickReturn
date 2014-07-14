package com.etiennelawlor.quickreturn.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;

/**
 * Created by etiennelawlor on 6/24/14.
 */
public class CustomFontTextView extends TextView {
    public CustomFontTextView(Context context) {
        super(context);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes( attrs, R.styleable.CustomFontTextView, 0, 0);
        try {
            String fontName = getFontName(a.getInteger(R.styleable.CustomFontTextView_textFont, 0));
            if (!fontName.equals("")) {
                try {
                    setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName));
                } catch (Exception e) {
                    Log.e("CustomFontTextView", e.getMessage());
                }
            }

        } finally {
            a.recycle();
        }
    }

    private String getFontName(int index) {

        switch (index) {
            case 0 :
                return "Roboto-Black.ttf";
            case 1 :
                return "Roboto-BlackItalic.ttf";
            case 2 :
                return "Roboto-Bold.ttf";
            case 3 :
                return "Roboto-BoldCondensed.ttf";
            case 4 :
                return "Roboto-BoldCondensedItalic.ttf";
            case 5 :
                return "Roboto-BoldItalic.ttf";
            case 6 :
                return "Roboto-Condensed.ttf";
            case 7 :
                return "Roboto-CondensedItalic.ttf";
            case 8 :
                return "Roboto-Italic.ttf";
            case 9 :
                return "Roboto-Light.ttf";
            case 10 :
                return "Roboto-LightItalic.ttf";
            case 11 :
                return "Roboto-Medium.ttf";
            case 12 :
                return "Roboto-MediumItalic.ttf";
            case 13 :
                return "Roboto-Regular.ttf";
            case 14 :
                return "Roboto-Thin.ttf";
            case 15 :
                return "Roboto-ThinItalic.ttf";
            case 16 :
                return "GothamNarrow-Light.ttf";

            default:
                return "";
        }

    }
}
