package com.rahpa.yasamani.rahpamap.MapFeature;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saeed on 4/3/2018.
 */

public interface MapContract {

    interface View{
        public void getLocation(Location location);
        public void drawRoute(List<LatLng> route);
    }

    interface Presenter{
        public void onAttachView(View v);
        public void onLocationRequest(Context context);
        public void onLocationLoaded(Location location);
        public void onDirectionRouteRequest(ArrayList<LatLng> points);
        public void onLoadDirectionRoute(List<LatLng> route);

        public void onStart();
        public void onStop();
    }

    interface Model{
        public void onAttachPresenter(Presenter p);
        public void RequestLocation(Context context);
        public void RequestDirectionRoute(ArrayList<LatLng> points);
    }
}
