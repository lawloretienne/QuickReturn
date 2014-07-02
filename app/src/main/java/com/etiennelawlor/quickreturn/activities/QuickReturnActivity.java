package com.etiennelawlor.quickreturn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;


public class QuickReturnActivity extends QuickReturnBaseActivity {

    // region Constants
    // endregion

    // region Member Variables
    private TextView mTwitterTextView;
    private TextView mFacebookTextView;
    private TextView mListViewTextView;
    private TextView mScrollViewTextView;
    // endregion

    // region Listeners
    private View.OnClickListener mTwitterTextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(QuickReturnActivity.this, QuickReturnTwitterActivity.class));
        }
    };

    private View.OnClickListener mFacebookTextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(QuickReturnActivity.this, QuickReturnFacebookActivity.class));
        }
    };

    private View.OnClickListener mListViewTextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(QuickReturnActivity.this, QuickReturnListViewActivity.class));
        }
    };

    private View.OnClickListener mScrollViewTextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(QuickReturnActivity.this, QuickReturnScrollViewActivity.class));
        }
    };
    // endregion

    // region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickreturn);

        bindUIElements();
        setUpListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    // endregion

    // region Helper Methods
    private void bindUIElements(){
        mTwitterTextView = (TextView) findViewById(R.id.twitter_tv);
        mFacebookTextView = (TextView) findViewById(R.id.facebook_tv);
        mListViewTextView = (TextView) findViewById(R.id.listview_tv);
        mScrollViewTextView = (TextView) findViewById(R.id.scrollview_tv);

    }

    private void setUpListeners(){
        mTwitterTextView.setOnClickListener(mTwitterTextViewOnClickListener);
        mFacebookTextView.setOnClickListener(mFacebookTextViewOnClickListener);
        mListViewTextView.setOnClickListener(mListViewTextViewOnClickListener);
        mScrollViewTextView.setOnClickListener(mScrollViewTextViewOnClickListener);
    }
    // endregion

    // region Inner Classes
    // endregion

}
