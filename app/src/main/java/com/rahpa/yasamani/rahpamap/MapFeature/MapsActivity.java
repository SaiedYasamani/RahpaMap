package com.rahpa.yasamani.rahpamap.MapFeature;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.rahpa.yasamani.rahpamap.AdressFeature.AddressActivity;
import com.rahpa.yasamani.rahpamap.AdressFeature.AddressActivity_;
import com.rahpa.yasamani.rahpamap.BaseActivity;
import com.rahpa.yasamani.rahpamap.R;
import com.rahpa.yasamani.rahpamap.Utils.LocationService;
import com.rahpa.yasamani.rahpamap.Utils.LocationServiceResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

@EActivity(R.layout.activity_maps)
public class MapsActivity extends BaseActivity implements OnMapReadyCallback,MapContract.View {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private Location location;
    private boolean isDirectionDetermined = false;
    private ArrayList<LatLng> Points = new ArrayList<>();
    private Circle CurrentMarker;


    public Presenter presenter = new Presenter();

    @ViewById
    ImageView center_indicator;

    @ViewById
    ImageView clear;

    @Click
    public void center_indicator(){
        
        LatLng mapCenter = mMap.getCameraPosition().target;
        Points.add(mapCenter);

        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(mapCenter);
        markerOption.title(String.valueOf(R.string.map_marker_origin));
        mMap.addMarker(markerOption);
        
        if(isDirectionDetermined){

            Intent gotoAddress = new Intent(this, AddressActivity_.class);
            gotoAddress.putExtra("OriginLat",Points.get(0).latitude);
            gotoAddress.putExtra("OriginLon",Points.get(0).longitude);
            gotoAddress.putExtra("DestinationLat",Points.get(1).latitude);
            gotoAddress.putExtra("DestinationLon",Points.get(1).longitude);
            startActivityForResult(gotoAddress,200);

        }
        else {
            isDirectionDetermined = true;
            center_indicator.setImageResource(R.drawable.destination_indicator);
        }
    }

    @Click
    public void clear(){
        mMap.clear();
        center_indicator.setImageResource(R.drawable.origin_indicator);
        isDirectionDetermined = false;
        Points.clear();
    }

    @AfterViews
    public void initView() {

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
        else {

            mapFragment.getMapAsync(this);
        }

        //pass an instance of view to presenter:
        presenter.onAttachView(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        configureMap();

        //request for get current location through presenter:
        presenter.onLocationRequest(this);

        LatLng CurrentLocation = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrentLocation,15.0f));

    }

    @SuppressLint("MissingPermission")
    public void configureMap(){
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if((requestCode == 100) && (grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            mapFragment.getMapAsync(this);
        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == 200) && (resultCode == 201)){

            center_indicator.setVisibility(View.INVISIBLE);
            clear.setVisibility(View.INVISIBLE);
            presenter.onDirectionRouteRequest(Points);
            mMap.setMyLocationEnabled(false);
            Intent gotoService = new Intent(this, LocationService.class);
            startService(gotoService);
        }
    }

    @Override
    public void getLocation(Location location) {

        //view location variable loaded by presenter:
        this.location = location;
    }

    @Override
    public void drawRoute(List<LatLng> route) {
        mMap.addPolyline(new PolylineOptions()
        .color(R.color.colorPrimaryDark)
        .width(7)
        .addAll(route));
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LocationServiceResult event) {
        if(CurrentMarker != null){
            CurrentMarker.remove();
        }
        LatLng c1 = event.getServiceLocation();
        CurrentMarker = mMap.addCircle(new CircleOptions()
        .center(c1));
    };
}
