QuickReturn
===========

QuickReturn is a library which showcases the QuickReturn UI pattern.  It can be used for header views, footer views, or both header views and footer views.

Support for ScrollView and ListView

Comes with examples of Twitter, Facebook, and Google+ QuickReturn views

Sample Usage
============

The example below shows how to set up a QuickReturn view as a header in a ListView

###### QuickReturnHeaderListFragment3.java
```java
public class QuickReturnHeaderListFragment3 extends ListFragment {

    // region Member Variables
    private String[] mValues;

    @InjectView(android.R.id.list) ListView mListView;
    @InjectView(R.id.quick_return_tv) TextView mQuickReturnTextView;
    // endregion

    // region Constructors
    public static QuickReturnHeaderListFragment3 newInstance() {
        QuickReturnHeaderListFragment3 fragment = new QuickReturnHeaderListFragment3();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnHeaderListFragment3() {
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

        // Set up the QuickReturn scroll listener
        int headerHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.header_height2);
        QuickReturnListViewOnScrollListener scrollListener = new QuickReturnListViewOnScrollListener(QuickReturnType.HEADER,
                mQuickReturnTextView, -headerHeight, null, 0);
        // Setting to true will slide the header and/or footer into view or slide out of view based 
        // on what is visible in the idle scroll state
        scrollListener.setCanSlideInIdleScrollState(true);
        mListView.setOnScrollListener(scrollListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    // endregion
}
```

###### fragment_list_quick_return_header.xml
```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ListView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@android:id/list"/>

    <com.etiennelawlor.quickreturn.views.CustomFontTextView
        android:id="@+id/quick_return_tv"
        android:layout_width="match_parent"
        android:layout_height="80dp"
        android:background="@color/transparent_black_ninty"
        android:textSize="22sp"
        android:text="@string/header"
        android:textColor="@android:color/white"
        android:gravity="center"
        app:textFont="Roboto_Bold"/>

</RelativeLayout>
```

Download the app by clicking on the badge below.

<a href="https://play.google.com/store/apps/details?id=com.etiennelawlor.quickreturn">
  <img alt="Get it on Google Play" src="/images/en_generic_rgb_wo_60.png" />
</a>

<img src="https://raw.githubusercontent.com/lawloretienne/QuickReturn/master/images/quick_return_demo.gif">

Developed By
============

* Etienne Lawlor 
 
&nbsp;&nbsp;&nbsp;**Email** - lawloretienne@gmail.com

&nbsp;&nbsp;&nbsp;**Website** - www.interapptiv3.com 

Projects/Apps using QuickReturn
===============================

- <a href="https://play.google.com/store/apps/details?id=com.tradehero.th">TradeHero</a>

Feel free to contact me to add yours to this list.

Todo
====
Convert library into an Android archive (.aar)

License
========

```
Copyright 2014 Etienne Lawlor

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
