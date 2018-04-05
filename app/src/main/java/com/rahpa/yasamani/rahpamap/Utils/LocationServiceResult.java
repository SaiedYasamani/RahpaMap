package com.rahpa.yasamani.rahpamap.Utils;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Saeed on 4/5/2018.
 */

public class LocationServiceResult {

    LatLng serviceLocation;

    public LatLng getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(LatLng serviceLocation) {
        this.serviceLocation = serviceLocation;
    }
}
