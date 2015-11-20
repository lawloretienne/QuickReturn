package com.etiennelawlor.quickreturn.activities;

import android.os.Bundle;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.fragments.QuickReturnGooglePlusFragment;

import butterknife.ButterKnife;


public class QuickReturnGooglePlusActivity extends QuickReturnBaseActivity {

    // region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_return_google_plus);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new QuickReturnGooglePlusFragment())
                    .commit();
        }
    }
    // endregion

}
