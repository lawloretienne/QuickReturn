package com.etiennelawlor.quickreturn.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.enums.QuickReturnType;
import com.etiennelawlor.quickreturn.listeners.QuickReturnListViewOnScrollListener;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnHeaderListFragment extends ListFragment {

    // region Member Variables
    private String[] mValues;
    private boolean isActionUp = false;

    @InjectView(android.R.id.list) ListView mListView;
    @InjectView(R.id.quick_return_tv) TextView mQuickReturnTextView;
    // endregion

    // region Constructors
    public static QuickReturnHeaderListFragment newInstance() {
        QuickReturnHeaderListFragment fragment = new QuickReturnHeaderListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnHeaderListFragment() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_quick_return_header, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mValues = getResources().getStringArray(R.array.countries);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item, R.id.item_tv, mValues);

        mListView.setAdapter(adapter);

        int headerHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.header_height2);
        mListView.setOnScrollListener(new QuickReturnListViewOnScrollListener(QuickReturnType.HEADER, mQuickReturnTextView, -headerHeight, null, 0));

//        mListView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch(event.getAction())
//                {
//
//                    case MotionEvent.ACTION_DOWN:
//                        isActionUp = false;
//                        Log.d("QuickReturnFooterListFragment", "onTouch() : ACTION_DOWN");
////                        return true;
//                    case MotionEvent.ACTION_CANCEL:
////                        Log.d("QuickReturnFooterListFragment", "onTouch() : ACTION_CANCEL");
//                    case MotionEvent.ACTION_OUTSIDE:
////                        Log.d("QuickReturnFooterListFragment", "onTouch() : ACTION_OUTSIDE");
////                        return true;
//                    case MotionEvent.ACTION_UP:
//                        if(isActionUp){
//                            Log.d("QuickReturnFooterListFragment", "onTouch() : ACTION_UP : mDiffTotal - "+mDiffTotal);
//                            Log.d("QuickReturnFooterListFragment", "onTouch() : mHeaderHeight - "+mHeaderHeight);
//
//
//                            if(-mDiffTotal >= mHeaderHeight/2){
//                                Log.d("QuickReturnFooterListFragment", "onTouch() : slide up");
//
////                                mAnim = new TranslateAnimation(0, 0, mHeaderHeight-mDiffTotal,
////                                        0);
//
//                                mAnim = new TranslateAnimation(0, 0, 0, mDiffTotal);
//
//                                mAnim.setAnimationListener(new Animation.AnimationListener() {
//                                    @Override
//                                    public void onAnimationStart(Animation animation) {
//
//                                    }
//
//                                    @Override
//                                    public void onAnimationEnd(Animation animation) {
//                                        RelativeLayout parent = (RelativeLayout) mQuickReturnTextView.getParent();
////                                    mQuickReturnTextView.layout(0, parent.getHeight()-mQuickReturnTextView.getHeight(), mQuickReturnTextView.getWidth() , mQuickReturnTextView.getHeight());
//
////                                    mQuickReturnTextView.layout(0, parent.getHeight()-QuickReturnUtils.dp2px(getActivity(), 80), mQuickReturnTextView.getWidth() , QuickReturnUtils.dp2px(getActivity(), 80));
//
////                                        mQuickReturnTextView.layout(0, 0, mQuickReturnTextView.getWidth() , QuickReturnUtils.dp2px(getActivity(), 80));
//
////                                        mQuickReturnTextView.layout(0, 0, mQuickReturnTextView.getWidth() , 0);
//
//                                        mQuickReturnTextView.setTranslationY(-mHeaderHeight);
//                                        mDiffTotal = -mHeaderHeight;
//                                    }
//
//                                    @Override
//                                    public void onAnimationRepeat(Animation animation) {
//
//                                    }
//                                });
//                                mAnim.setDuration(300);
//                                mQuickReturnTextView.startAnimation(mAnim);
//
////                                mQuickReturnTextView.setTranslationY(-mHeaderHeight);
//
//
//
//                            } else {
////                            mQuickReturnTextView.setTranslationY(mFooterHeight);
//
//                                mAnim = new TranslateAnimation(0, 0, 0, -mDiffTotal);
//
//                                mAnim.setAnimationListener(new Animation.AnimationListener() {
//                                    @Override
//                                    public void onAnimationStart(Animation animation) {
//
//                                    }
//
//                                    @Override
//                                    public void onAnimationEnd(Animation animation) {
//                                        RelativeLayout parent = (RelativeLayout) mQuickReturnTextView.getParent();
////                                    mQuickReturnTextView.layout(0, parent.getHeight()-mQuickReturnTextView.getHeight(), mQuickReturnTextView.getWidth() , mQuickReturnTextView.getHeight());
//
////                                    mQuickReturnTextView.layout(0, parent.getHeight()-QuickReturnUtils.dp2px(getActivity(), 80), mQuickReturnTextView.getWidth() , QuickReturnUtils.dp2px(getActivity(), 80));
//
////                                        mQuickReturnTextView.layout(0, 0, mQuickReturnTextView.getWidth() , QuickReturnUtils.dp2px(getActivity(), 80));
//
////                                        mQuickReturnTextView.layout(0, 0, mQuickReturnTextView.getWidth() , 0);
//
//                                        mQuickReturnTextView.setTranslationY(0);
//                                    }
//
//                                    @Override
//                                    public void onAnimationRepeat(Animation animation) {
//
//                                    }
//                                });
//                                mAnim.setDuration(300);
//                                mQuickReturnTextView.startAnimation(mAnim);
//
//                                Log.d("QuickReturnFooterListFragment", "onTouch() : slide down");
//
//                            }
//
////                        v.setBackgroundDrawable(null);
////                        Intent myIntent = new Intent(v.getContext(), SearchActivity.class);
////                        startActivity(myIntent);
//
////                            return true;
//                        }
//
//                        isActionUp = true;
//
//                }
//                return false;
//            }
//
//
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    // endregion
}
