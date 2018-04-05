package com.rahpa.yasamani.rahpamap.MapFeature;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.rahpa.yasamani.rahpamap.Entities.Direction;
import com.rahpa.yasamani.rahpamap.Entities.Route;
import com.rahpa.yasamani.rahpamap.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Saeed on 4/3/2018.
 */

public class Model implements MapContract.Model {

    public MapContract.Presenter presenter;

    @Override
    public void onAttachPresenter(MapContract.Presenter p) {
        this.presenter = p;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void RequestLocation(Context context) {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = null;

        if (locationManager != null) {
            location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria,false));
        }

        presenter.onLocationLoaded(location);
    }

    @Override
    public void RequestDirectionRoute(ArrayList<LatLng> points) {

        String origin = points.get(0).latitude + "," + points.get(0).longitude;
        String destination = points.get(1).latitude + "," + points.get(1).longitude;

        Constants.webServiceRequests.getDirection(origin,destination,Constants.MAP_KEY).enqueue(new Callback<Direction>() {
            @Override
            public void onResponse(Call<Direction> call, Response<Direction> response) {

                String result = null;
                try {
                    result = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<LatLng> resultRoute = null;
                try {
                    resultRoute = PolyUtil.decode(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                presenter.onLoadDirectionRoute(resultRoute);
            }

            @Override
            public void onFailure(Call<Direction> call, Throwable t) {

            }
        });

    }
}
