package com.rahpa.yasamani.rahpamap.Utils;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Saeed on 4/5/2018.
 */

public class LocationService extends Service {

    LocationServiceResult lsr = new LocationServiceResult();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Location Service Started", Toast.LENGTH_SHORT).show();
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lsr.setServiceLocation(new LatLng(location.getLatitude(),location.getLongitude()));

        EventBus.getDefault().post(lsr);
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30 * 1000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                lsr.setServiceLocation(new LatLng(location.getLatitude(),location.getLongitude()));
                //pass current location to subscribed component:
                EventBus.getDefault().post(lsr);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
        return super.onStartCommand(intent, flags, startId);
    }
}
