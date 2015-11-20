package com.etiennelawlor.quickreturn.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by etiennelawlor on 7/17/14.
 */
public class CountriesLinearLayoutAdapter extends RecyclerView.Adapter<CountriesLinearLayoutAdapter.ViewHolder> {

    // region Member Variables
    private Context mContext;
    private List<String> mCountries;
    // endregion

    // region Constructors
    public CountriesLinearLayoutAdapter(List<String> countries) {
        mCountries = countries;
    }
    // endregion

    @Override
    public CountriesLinearLayoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String country = mCountries.get(position);

        setUpCountry(holder.mCountryTextView, country);
    }

    @Override
    public int getItemCount() {
        return mCountries.size();
    }

    // region Helper Methods
    private void setUpCountry(TextView tv, String country){
        if (!TextUtils.isEmpty(country)) {
            tv.setText(country);
        }
    }
    // endregion

    // region Inner Classes

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.country_tv)
        TextView mCountryTextView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    // endregion
}
