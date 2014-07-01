package com.etiennelawlor.quickreturn.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
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

//        GridView gridview = (GridView) findViewById(R.id.gv);
//        gridview.setAdapter(new ImageAdapter(this));
//
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                Toast.makeText(QuickReturnActivity.this, "" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
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
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

//        // create a new ImageView for each item referenced by the Adapter
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ImageView imageView;
//            if (convertView == null) {  // if it's not recycled, initialize some attributes
//                imageView = new ImageView(mContext);
//                imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT));
////                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setPadding(8, 8, 8, 8);
//            } else {
//                imageView = (ImageView) convertView;
//            }
//
//            imageView.setImageResource(mThumbIds[position]);
//            return imageView;
//        }

        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                textView = new TextView(mContext);
                textView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT));
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                textView.setPadding(8, 8, 8, 8);
            } else {
                textView = (TextView) convertView;
            }

//            imageView.setImageResource(mThumbIds[position]);
            textView.setText("Facebook");
            return textView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher
        };
    }
    // endregion

}
