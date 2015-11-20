package com.etiennelawlor.quickreturn;

import android.app.Application;

/**
 * Created by etiennelawlor on 1/20/15.
 */
public class QuickReturnApplication extends Application {

    // region Static Variables
    private static QuickReturnApplication sCurrentApplication = null;
    // endregion

    @Override
    public void onCreate() {
        super.onCreate();

        sCurrentApplication = this;
    }    
    
    // region Getters
    public static QuickReturnApplication getInstance() {
        return sCurrentApplication;
    }
    // endregion
}
