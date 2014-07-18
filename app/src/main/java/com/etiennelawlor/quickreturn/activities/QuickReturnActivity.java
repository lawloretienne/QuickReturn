package com.etiennelawlor.quickreturn.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.etiennelawlor.quickreturn.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class QuickReturnActivity extends QuickReturnBaseActivity {

    // region Listeners
    @OnClick(R.id.twitter_tv)
    public void onTwitterClicked(View v){
        startActivity(new Intent(QuickReturnActivity.this, QuickReturnTwitterActivity.class));
    }

    @OnClick(R.id.facebook_tv)
     public void onFacebookClicked(View v){
        startActivity(new Intent(QuickReturnActivity.this, QuickReturnFacebookActivity.class));
    }

    @OnClick(R.id.google_plus_tv)
    public void onGooglePlusClicked(View v){
        startActivity(new Intent(QuickReturnActivity.this, QuickReturnGooglePlusActivity.class));
    }

    @OnClick(R.id.listview_tv)
    public void onListViewClicked(View v){
        startActivity(new Intent(QuickReturnActivity.this, QuickReturnListViewActivity.class));
    }

    @OnClick(R.id.scrollview_tv)
    public void onScrollViewClicked(View v){
        startActivity(new Intent(QuickReturnActivity.this, QuickReturnScrollViewActivity.class));
    }
    // endregion

    // region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickreturn);
        ButterKnife.inject(this);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }
    // endregion

}
