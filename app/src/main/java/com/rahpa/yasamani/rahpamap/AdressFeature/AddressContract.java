package com.rahpa.yasamani.rahpamap.AdressFeature;

/**
 * Created by Saeed on 4/5/2018.
 */

public interface AddressContract {

    interface View {
        public void getAddress(String[] adds);
    }

    interface Presenter{
        public void onAttachView(View v);
        public void onAddressRequest(Double[] coordinates);
        public void onAddressLoaded(String[] adds);
    }
    interface Model{
        public void onAttachPresenter(Presenter p);
        public void RequestAddress(Double[] coordinates);
    }
}
