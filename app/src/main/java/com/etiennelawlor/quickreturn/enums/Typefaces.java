package com.etiennelawlor.quickreturn.enums;

import android.graphics.Typeface;

import com.etiennelawlor.quickreturn.utils.TypefaceUtil;

/**
 * Created by etiennelawlor on 1/20/15.
 */
public enum Typefaces implements TypefaceUtil.TypefaceId {
    ROBOTO_BLACK("Roboto-Black.ttf"),
    ROBOTO_BLACK_ITALIC("Roboto-BlackItalic.ttf"),
    ROBOTO_BOLD("Roboto-Bold.ttf"),
    ROBOTO_BOLD_ITALIC("Roboto-BoldItalic.ttf"),
    ROBOTO_ITALIC("Roboto-Italic.ttf"),
    ROBOTO_LIGHT("Roboto-Light.ttf"),
    ROBOTO_LIGHT_ITALIC("Roboto_LightItalic.ttf"),
    ROBOTO_MEDIUM("Roboto-Medium.ttf"),
    ROBOTO_MEDIUM_ITALIC("Roboto-MediumItalic.ttf"),
    ROBOTO_REGULAR("Roboto-Regular.ttf"),
    ROBOTO_THIN("Roboto-Thin.ttf"),
    ROBOTO_THIN_ITALIC("Roboto-ThinItalic.ttf"),
    ROBOTO_CONDENSED_BOLD("RobotoCondensed-Bold.ttf"),
    ROBOTO_CONDENSED_BOLD_ITALIC("RobotoCondensed-BoldItalic.ttf"),
    ROBOTO_CONDENSED_ITALIC("RobotoCondensed-Italic.ttf"),
    ROBOTO_CONDENSED_LIGHT("RobotoCondensed-Light.ttf"),
    ROBOTO_CONDENSED_LIGHT_ITALIC("RobotoCondensed-LightItalic.ttf"),
    ROBOTO_CONDENSED_REGULAR("RobotoCondensed-Regular.ttf");


    public static final String BASE_PATH = "fonts/";

    private String filename;

    private Typefaces(String filename) {
        this.filename = filename;
    }

    public static Typefaces from(int index) {
        switch (index) {
            case 0 :
                return ROBOTO_BLACK;
            case 1 :
                return ROBOTO_BLACK_ITALIC;
            case 2 :
                return ROBOTO_BOLD;
            case 3 :
                return ROBOTO_BOLD_ITALIC;
            case 4 :
                return ROBOTO_ITALIC;
            case 5 :
                return ROBOTO_LIGHT;
            case 6 :
                return ROBOTO_LIGHT_ITALIC;
            case 7 :
                return ROBOTO_MEDIUM;
            case 8 :
                return ROBOTO_MEDIUM_ITALIC;
            case 9 :
                return ROBOTO_REGULAR;
            case 10 :
                return ROBOTO_THIN;
            case 11 :
                return ROBOTO_THIN_ITALIC;
            case 12 :
                return ROBOTO_CONDENSED_BOLD;
            case 13 :
                return ROBOTO_CONDENSED_BOLD_ITALIC;
            case 14 :
                return ROBOTO_CONDENSED_ITALIC;
            case 15 :
                return ROBOTO_CONDENSED_LIGHT;
            case 16 :
                return ROBOTO_CONDENSED_LIGHT_ITALIC;
            case 17 :
                return ROBOTO_CONDENSED_REGULAR;
            default:
                return ROBOTO_REGULAR;
        }
    }

    @Override public Typeface get() {
        return TypefaceUtil.getTypeface(this);
    }

    @Override public String getFilePath() {
        return BASE_PATH + filename;
    }
}