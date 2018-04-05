package com.rahpa.yasamani.rahpamap.AdressFeature;

import com.rahpa.yasamani.rahpamap.Entities.GoogleGeocode.Address;
import com.rahpa.yasamani.rahpamap.Utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Saeed on 4/5/2018.
 */

public class Model implements AddressContract.Model {

    public AddressContract.Presenter presenter;

    @Override
    public void onAttachPresenter(AddressContract.Presenter p) {
        this.presenter = p;
    }

    @Override
    public void RequestAddress(Double[] coordinates) {

        final String[] addresses = new String[2];

        String originAdd = coordinates[0] + "," + coordinates[1];
        String destinationAdd = coordinates[2] + "," + coordinates[3];

        Constants.webServiceRequests.getAddress(originAdd,Constants.MAP_KEY).enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                try {
                    addresses[0] = response.body().getResults().get(0).getFormattedAddress();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {

            }
        });

        Constants.webServiceRequests.getAddress(destinationAdd,Constants.MAP_KEY).enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                try {
                    addresses[1] = response.body().getResults().get(0).getAddressComponents().get(0).getLongName();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {

            }
        });
        presenter.onAddressLoaded(addresses);
    }
}
