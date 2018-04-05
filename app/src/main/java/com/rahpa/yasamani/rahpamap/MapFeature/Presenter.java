package com.rahpa.yasamani.rahpamap.MapFeature;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saeed on 4/3/2018.
 */

public class Presenter implements MapContract.Presenter {

    public MapContract.View view;
    public MapContract.Model model = new Model();

    @Override
    public void onAttachView(MapContract.View v) {

        this.view = v;
        model.onAttachPresenter(this);
    }

    @Override
    public void onLocationRequest(Context context) {
        model.RequestLocation(context);
    }

    @Override
    public void onLocationLoaded(Location location) {
        if (location != null) {
            view.getLocation(location);
        }
    }

    @Override
    public void onDirectionRouteRequest(ArrayList<LatLng> points) {
        model.RequestDirectionRoute(points);
    }

    @Override
    public void onLoadDirectionRoute(List<LatLng> route) {
        view.drawRoute(route);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this.view);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this.view);
    }
}
