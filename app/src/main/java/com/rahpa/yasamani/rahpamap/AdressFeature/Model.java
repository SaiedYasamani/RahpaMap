package com.rahpa.yasamani.rahpamap.AdressFeature;

import android.util.Log;

import com.rahpa.yasamani.rahpamap.Entities.GoogleGeocode.Address;
import com.rahpa.yasamani.rahpamap.Utils.Constants;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Saeed on 4/5/2018.
 */

public class Model implements AddressContract.Model {

    public AddressContract.Presenter presenter;
    String[] addresses = new String[2];

    @Override
    public void onAttachPresenter(AddressContract.Presenter p) {
        this.presenter = p;
    }

    @Override
    public void RequestAddress(Double[] coordinates) {



        String originAdd = coordinates[0] + "," + coordinates[1];
        String destinationAdd = coordinates[2] + "," + coordinates[3];

        Constants.webServiceRequests.getAddress(originAdd,Constants.MAP_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onResponse);

        Constants.webServiceRequests.getAddress(destinationAdd,Constants.MAP_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onResponse2);

        presenter.onAddressLoaded(addresses);
    }

    private void onResponse(Address address) {
        try {
            addresses[0] = address.getResults().get(0).getFormattedAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onResponse2(Address address) {
        try {
            addresses[1] = address.getResults().get(0).getFormattedAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
