package com.etiennelawlor.quickreturn.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.vending.billing.IInAppBillingService;
import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.fragments.QuickReturnFacebookFragment;
import com.etiennelawlor.quickreturn.fragments.QuickReturnFooterListFragment;
import com.etiennelawlor.quickreturn.fragments.QuickReturnFragment;
import com.etiennelawlor.quickreturn.fragments.QuickReturnHeaderListFragment;
import com.etiennelawlor.quickreturn.utils.QuickReturnUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class QuickReturnActivity extends Activity implements ActionBar.TabListener {

    // region Constants
    private static final int BILLING_RESPONSE_RESULT_OK = 0;
    private static final int BUY_REQUEST_CODE = 4;
    private static final String ITEM_TYPE_INAPP = "inapp";
    // endregion

    // region Member Variables
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private IInAppBillingService mService;

    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
        }
    };
    // endregion

    // region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickreturn);

        bindService(new Intent("com.android.vending.billing.InAppBillingService.BIND"),
                mServiceConn, Context.BIND_AUTO_CREATE);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Crouton.cancelAllCroutons();
    }
    // endregion

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BUY_REQUEST_CODE) {
            int responseCode;

            switch (resultCode){
                case RESULT_OK:
                    Log.d(getClass().getSimpleName(), "onActivityResult() : RESULT_OK");

                    responseCode = data.getIntExtra("RESPONSE_CODE", -5);
                    String purchaseData = data.getStringExtra("RESPONSE_INAPP_PURCHASE_DATA");
                    String signature = data.getStringExtra("RESPONSE_INAPP_SIGNATURE");

                    // handle purchase here (for a permanent item like a premium upgrade,
                    // this means dispensing the benefits of the upgrade; for a consumable
                    // item like "X gold coins", typically the application would initiate
                    // consumption of the purchase here)
                    break;
                case RESULT_CANCELED:
                    Log.d(getClass().getSimpleName(), "onActivityResult() : RESULT_CANCELED");

                    responseCode = data.getIntExtra("RESPONSE_CODE", -5);

                    Style croutonStyle = new Style.Builder()
                            .setHeight(QuickReturnUtils.dp2px(this, 50))
//                                .setTextColor(getResources().getColor(R.color.white))
                            .setGravity(Gravity.CENTER)
                            .setBackgroundColor(R.color.steel_blue)
                            .build();

                    Crouton.makeText(this, R.string.result_canceled, croutonStyle)
                            .setConfiguration(new Configuration.Builder()
                                    .setDuration(Configuration.DURATION_SHORT)
                                    .setInAnimation(R.anim.crouton_in_delayed)
                                    .setOutAnimation(R.anim.crouton_out)
                                    .build())
                            .show();

                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quick_return, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_github:
                openWebPage("https://github.com/lawloretienne/QuickReturn");
                return true;
            case R.id.buy_one_beer:
                donate(getString(R.string.buy_one_beer));
                return true;
            case R.id.buy_two_beers:
                donate(getString(R.string.buy_two_beers));
                return true;
            case R.id.buy_four_beers:
                donate(getString(R.string.buy_four_beers));
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // region ActionBar.TabListener Methods
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
    // endregion

    // region Helper Methods
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void donate(String productSku) {
        ArrayList<String> skuList = new ArrayList<String> ();
        skuList.add("buy_one_beer");
        skuList.add("buy_two_beers");
        skuList.add("buy_four_beers");
        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

        try {
            Bundle skuDetails = mService.getSkuDetails(3,
                    getPackageName(), ITEM_TYPE_INAPP, querySkus);

            int response = skuDetails.getInt("RESPONSE_CODE");
            if (response == BILLING_RESPONSE_RESULT_OK) {
                ArrayList<String> responseList
                        = skuDetails.getStringArrayList("DETAILS_LIST");

                for (String thisResponse : responseList) {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(thisResponse);
                        String sku = object.getString("productId");
                        String price = object.getString("price");

                        if (sku.equals("buy_one_beer")) {
                            Log.d(getClass().getSimpleName(), "price - "+price);
//                            mPremiumUpgradePrice = price;
                        } else if (sku.equals("buy_two_beers")) {
                            Log.d(getClass().getSimpleName(), "price - "+price);
//                            mGasPrice = price;
                        } else if (sku.equals("buy_four_beers")) {
                            Log.d(getClass().getSimpleName(), "price - "+price);
//                            mGasPrice = price;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            String developerPayload = "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ";

            Bundle bundle = mService.getBuyIntent(3, getPackageName(),
                    productSku, ITEM_TYPE_INAPP, developerPayload);

            PendingIntent pendingIntent = bundle.getParcelable("BUY_INTENT");
            if (bundle.getInt("RESPONSE_CODE") == BILLING_RESPONSE_RESULT_OK) {
                // Start purchase flow (this brings up the Google Play UI).
                // Result will be delivered through onActivityResult().
                startIntentSenderForResult(pendingIntent.getIntentSender(), BUY_REQUEST_CODE, new Intent(),
                        Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }
    // endregion

    // region Inner Classes

    /**
     * A {@link android.support.v13.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            switch (position) {
                case 0:
                    bundle.putString(getString(R.string.quick_return_type),
                            QuickReturnFragment.QuickReturnType.HEADER.name());
                    return QuickReturnFragment.newInstance(bundle);
                case 1:
                    bundle.putString(getString(R.string.quick_return_type),
                            QuickReturnFragment.QuickReturnType.FOOTER.name());
                    return QuickReturnFragment.newInstance(bundle);
                case 2:
                    bundle.putString(getString(R.string.quick_return_type),
                            QuickReturnFragment.QuickReturnType.BOTH.name());
                    return QuickReturnFragment.newInstance(bundle);
                case 3:
                    return QuickReturnHeaderListFragment.newInstance();
                case 4:
                    return QuickReturnFooterListFragment.newInstance();
                case 5:
                    return QuickReturnFacebookFragment.newInstance();
                default:
                    bundle.putString(getString(R.string.quick_return_type),
                            QuickReturnFragment.QuickReturnType.HEADER.name());
                    return QuickReturnFragment.newInstance(bundle);
            }
        }



        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.header);
                case 1:
                    return getString(R.string.footer);
                case 2:
                    return getString(R.string.header_footer);
                case 3:
                    return getString(R.string.header_list);
                case 4:
                    return getString(R.string.footer_list);
                case 5:
                    return getString(R.string.facebook);
            }
            return null;
        }
    }

    // endregion

}
