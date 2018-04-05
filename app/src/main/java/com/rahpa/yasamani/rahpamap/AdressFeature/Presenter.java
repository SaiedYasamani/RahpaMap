package com.rahpa.yasamani.rahpamap.AdressFeature;

/**
 * Created by Saeed on 4/5/2018.
 */

public class Presenter implements AddressContract.Presenter {

    public AddressContract.View view;
    public AddressContract.Model  model= new Model();

    @Override
    public void onAttachView(AddressContract.View v) {
        this.view = v;
        model.onAttachPresenter(this);
    }

    @Override
    public void onAddressRequest(Double[] coordinates) {
        model.RequestAddress(coordinates);
    }

    @Override
    public void onAddressLoaded(String[] adds) {
        view.getAddress(adds);
    }
}
