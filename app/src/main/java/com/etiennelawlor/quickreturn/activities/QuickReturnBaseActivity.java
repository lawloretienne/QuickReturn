package com.etiennelawlor.quickreturn.activities;

import android.app.ActionBar;
import android.app.Activity;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.vending.billing.IInAppBillingService;
import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.library.utils.QuickReturnUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class QuickReturnBaseActivity extends Activity {

    // region Constants

    // Server Response Codes (http://developer.android.com/google/play/billing/billing_reference.html)
    private static final int BILLING_RESPONSE_RESULT_OK = 0;
    private static final int BILLING_RESPONSE_RESULT_USER_CANCELED = 1;
    private static final int BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE = 3;
    private static final int BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE =	4;
    private static final int BILLING_RESPONSE_RESULT_DEVELOPER_ERROR = 5;
    private static final int BILLING_RESPONSE_RESULT_ERROR = 6;
    private static final int BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED	= 7;
    private static final int BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED	= 8;

    private static final int BUY_REQUEST_CODE = 4;

    private static final String ITEM_TYPE_INAPP = "inapp";

    private static final String RESPONSE_INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";
    private static final String RESPONSE_INAPP_SIGNATURE = "INAPP_DATA_SIGNATURE";
    private static final String RESPONSE_CODE = "RESPONSE_CODE";
    private static final String RESPONSE_GET_SKU_DETAILS_LIST = "DETAILS_LIST";
    private static final String RESPONSE_BUY_INTENT = "BUY_INTENT";
    private static final String GET_SKU_DETAILS_ITEM_LIST = "ITEM_ID_LIST";
    private static final String RESPONSE_INAPP_ITEM_LIST = "INAPP_PURCHASE_ITEM_LIST";
    private static final String RESPONSE_INAPP_PURCHASE_DATA_LIST = "INAPP_PURCHASE_DATA_LIST";
    private static final String RESPONSE_INAPP_SIGNATURE_LIST = "INAPP_DATA_SIGNATURE_LIST";
    private static final String RESPONSE_INAPP_PURCHASE_SIGNATURE_LIST = "INAPP_PURCHASE_SIGNATURE_LIST";
    private static final String INAPP_CONTINUATION_TOKEN = "INAPP_CONTINUATION_TOKEN";
    // endregion

    // region Member Variables
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

        bindService(new Intent("com.android.vending.billing.InAppBillingService.BIND"),
                mServiceConn, Context.BIND_AUTO_CREATE);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mService != null) {
            unbindService(mServiceConn);
        }

        Crouton.cancelAllCroutons();
    }
    // endregion

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BUY_REQUEST_CODE) {
            int responseCode;

            switch (resultCode){
                case RESULT_OK:
                    Log.d(getClass().getSimpleName(), "onActivityResult() : RESULT_OK");

                    responseCode = data.getIntExtra(RESPONSE_CODE, -5);

                    switch (responseCode){
                        case BILLING_RESPONSE_RESULT_OK:
                            String signature = data.getStringExtra(RESPONSE_INAPP_SIGNATURE);

                            String purchaseData = data.getStringExtra(RESPONSE_INAPP_PURCHASE_DATA);

                            JSONObject object = null;
                            try {
                                object = new JSONObject(purchaseData);

                                // sample data
                                // "purchaseToken" -> "inapp:com.etiennelawlor.quickreturn:android.test.purchased"
                                // "developerPayload" -> "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzK"
                                // "packageName" -> "com.etiennelawlor.quickreturn"
                                // "purchaseState" -> "0"
                                // "orderId" -> "transactionId.android.test.purchased"
                                // "purchaseTime" -> "0"
                                // "productId" -> "android.test.purchased"
                                //

                                String sku = object.getString("productId");

                                if(!TextUtils.isEmpty(sku)){
                                    if (sku.equals(getString(R.string.buy_one_beer))) {
                                        showCrouton(android.R.color.holo_green_light, getResources().getQuantityString(R.plurals.beer_cheers, 1, 1));
                                    } else if (sku.equals(getString(R.string.buy_two_beers))) {
                                        showCrouton(android.R.color.holo_green_light, getResources().getQuantityString(R.plurals.beer_cheers, 2, 2));
                                    } else if (sku.equals(getString(R.string.buy_four_beers))) {
                                        showCrouton(android.R.color.holo_green_light, getResources().getQuantityString(R.plurals.beer_cheers, 4, 4));
                                    } else if (sku.equals("android.test.purchased")) {
                                        showCrouton(android.R.color.holo_green_light, "Test Purchase completed");
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // handle purchase here (for a permanent item like a premium upgrade,
                            // this means dispensing the benefits of the upgrade; for a consumable
                            // item like "X gold coins", typically the application would initiate
                            // consumption of the purchase here)
                            break;
                        case BILLING_RESPONSE_RESULT_USER_CANCELED:
                            Log.d(getClass().getSimpleName(), "donate() : User pressed back or canceled a dialog");
                            break;
                        case BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE:
                            Log.d(getClass().getSimpleName(), "donate() : Billing API version is not supported for the type requested");
                            break;
                        case BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE:
                            Log.d(getClass().getSimpleName(), "donate() : Requested product is not available for purchase");
                            break;
                        case BILLING_RESPONSE_RESULT_DEVELOPER_ERROR:
                            Log.d(getClass().getSimpleName(), "donate() : Invalid arguments provided to the API. This error can also " +
                                    "indicate that the application was not correctly signed or properly set up for In-app Billing in " +
                                    "Google Play, or does not have the necessary permissions in its manifest");
                            break;
                        case BILLING_RESPONSE_RESULT_ERROR:
                            Log.d(getClass().getSimpleName(), "donate() : Fatal error during the API action");
                            break;
                        case BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED:
                            Log.d(getClass().getSimpleName(), "donate() : Failure to purchase since item is already owned");
                            showCrouton(android.R.color.holo_red_light, R.string.item_already_owned);
                            break;
                        case BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED:
                            Log.d(getClass().getSimpleName(), "donate() : Failure to consume since item is not owned");
                            break;
                        default:
                            break;
                    }


                    break;
                case RESULT_CANCELED:
                    Log.d(getClass().getSimpleName(), "onActivityResult() : RESULT_CANCELED");

                    responseCode = data.getIntExtra(RESPONSE_CODE, -5);

                    showCrouton(android.R.color.holo_red_light, R.string.beer_order_canceled);

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

    // region Helper Methods
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void donate(String productSku) {
        try {

//            getAllSkus();

//            getAllPurchases();

//            consumePurchase();

            String developerPayload = "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzK";

            Bundle bundle = mService.getBuyIntent(3, getPackageName(),
                    productSku, ITEM_TYPE_INAPP, developerPayload);

            // Test Item
//            Bundle bundle = mService.getBuyIntent(3, getPackageName(),
//                    "android.test.purchased", ITEM_TYPE_INAPP, developerPayload);

            PendingIntent pendingIntent = bundle.getParcelable(RESPONSE_BUY_INTENT);
            int responseCode = bundle.getInt(RESPONSE_CODE);

            switch (responseCode){
                case BILLING_RESPONSE_RESULT_OK:
                    startIntentSenderForResult(pendingIntent.getIntentSender(), BUY_REQUEST_CODE, new Intent(),
                            Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
                    break;
                case BILLING_RESPONSE_RESULT_USER_CANCELED:
                    Log.d(getClass().getSimpleName(), "donate() : User pressed back or canceled a dialog");
                    break;
                case BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE:
                    Log.d(getClass().getSimpleName(), "donate() : Billing API version is not supported for the type requested");
                    break;
                case BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE:
                    Log.d(getClass().getSimpleName(), "donate() : Requested product is not available for purchase");
                    break;
                case BILLING_RESPONSE_RESULT_DEVELOPER_ERROR:
                    Log.d(getClass().getSimpleName(), "donate() : Invalid arguments provided to the API. This error can also " +
                            "indicate that the application was not correctly signed or properly set up for In-app Billing in " +
                            "Google Play, or does not have the necessary permissions in its manifest");
                    break;
                case BILLING_RESPONSE_RESULT_ERROR:
                    Log.d(getClass().getSimpleName(), "donate() : Fatal error during the API action");
                    break;
                case BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED:
                    Log.d(getClass().getSimpleName(), "donate() : Failure to purchase since item is already owned");
                    showCrouton(android.R.color.holo_red_light, R.string.item_already_owned);
                    break;
                case BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED:
                    Log.d(getClass().getSimpleName(), "donate() : Failure to consume since item is not owned");
                    break;
                default:
                    break;
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void consumePurchase(){
        //            "purchaseToken": "inapp:com.etiennelawlor.quickreturn:android.test.purchased"

        String token = "inapp:com.etiennelawlor.quickreturn:android.test.purchased";
        try {
            int response = mService.consumePurchase(3, getPackageName(), token);
            Log.d(getClass().getSimpleName(), "consumePurchase() : response - "+response);

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private void getAllSkus(){
        ArrayList<String> skuList = new ArrayList<String> ();
        skuList.add("buy_one_beer");
        skuList.add("buy_two_beers");
        skuList.add("buy_four_beers");
        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList(GET_SKU_DETAILS_ITEM_LIST, skuList);

        try {
            Bundle skuDetails = mService.getSkuDetails(3,
                    getPackageName(), ITEM_TYPE_INAPP, querySkus);

            int response = skuDetails.getInt(RESPONSE_CODE);
            if (response == BILLING_RESPONSE_RESULT_OK) {
                ArrayList<String> responseList
                        = skuDetails.getStringArrayList(RESPONSE_GET_SKU_DETAILS_LIST);

                for (String thisResponse : responseList) {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(thisResponse);
                        String sku = object.getString("productId");
                        String price = object.getString("price");

                        if (sku.equals(getString(R.string.buy_one_beer))) {
                            Log.d(getClass().getSimpleName(), "price - "+price);
//                            mPremiumUpgradePrice = price;
                        } else if (sku.equals(getString(R.string.buy_two_beers))) {
                            Log.d(getClass().getSimpleName(), "price - "+price);
//                            mGasPrice = price;
                        } else if (sku.equals(getString(R.string.buy_four_beers))) {
                            Log.d(getClass().getSimpleName(), "price - "+price);
//                            mGasPrice = price;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void getAllPurchases(){
        try {
            Bundle purchases = mService.getPurchases(3, getPackageName(), ITEM_TYPE_INAPP, INAPP_CONTINUATION_TOKEN);
            if (purchases.getInt(RESPONSE_CODE) == BILLING_RESPONSE_RESULT_OK) {
                ArrayList mySkus, myPurchases, mySignatures;
                mySkus = purchases.getStringArrayList(RESPONSE_INAPP_ITEM_LIST);
                myPurchases = purchases.getStringArrayList(RESPONSE_INAPP_PURCHASE_DATA_LIST);
                mySignatures = purchases.getStringArrayList(RESPONSE_INAPP_PURCHASE_SIGNATURE_LIST);
                Log.d(getClass().getSimpleName(), "getAllPurchases() : purchases");

                // handle items here
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private void showCrouton(int colorRes, int messageRes){
        Style croutonStyle = new Style.Builder()
                .setHeight(QuickReturnUtils.dp2px(this, 50))
//                                .setTextColor(getResources().getColor(R.color.white))
                .setGravity(Gravity.CENTER)
                .setBackgroundColor(colorRes)
                .build();

        Crouton.makeText(this, messageRes, croutonStyle)
                .setConfiguration(new Configuration.Builder()
                        .setDuration(Configuration.DURATION_SHORT)
                        .setInAnimation(R.anim.crouton_in_delayed)
                        .setOutAnimation(R.anim.crouton_out)
                        .build())
                .show();
    }

    private void showCrouton(int colorRes, String message){
        Style croutonStyle = new Style.Builder()
                .setHeight(QuickReturnUtils.dp2px(this, 50))
//                                .setTextColor(getResources().getColor(R.color.white))
                .setGravity(Gravity.CENTER)
                .setBackgroundColor(colorRes)
                .build();

        Crouton.makeText(this, message, croutonStyle)
                .setConfiguration(new Configuration.Builder()
                        .setDuration(Configuration.DURATION_SHORT)
                        .setInAnimation(R.anim.crouton_in_delayed)
                        .setOutAnimation(R.anim.crouton_out)
                        .build())
                .show();
    }
    // endregion

}
