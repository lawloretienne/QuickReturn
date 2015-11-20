package com.etiennelawlor.quickreturn.utils;

import android.graphics.Typeface;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.QuickReturnApplication;
import com.google.common.collect.Maps;

import java.util.HashMap;

/**
 * Created by etiennelawlor on 1/20/15.
 */
public class TypefaceUtil {

    // region Static Variables
    private static HashMap<TypefaceId, Typeface> sTypefaceCache = Maps.newHashMap();
    // endregion

    public static void apply(TypefaceId id, TextView tv) {
        if (tv == null || tv.isInEditMode()) {
            return;
        }
        tv.setTypeface(getTypeface(id));
    }

    public static Typeface getTypeface(TypefaceId id) {
        Typeface typeface = sTypefaceCache.get(id);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(QuickReturnApplication.getInstance().getAssets(), id.getFilePath());
            sTypefaceCache.put(id, typeface);
        }
        return typeface;
    }

    // region Interfaces
    public static interface TypefaceId {
        public Typeface get();

        public String getFilePath();
    }
    // endregion
}

