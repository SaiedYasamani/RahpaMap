package com.rahpa.yasamani.rahpamap.AdressFeature;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.rahpa.yasamani.rahpamap.BaseActivity;
import com.rahpa.yasamani.rahpamap.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_address)
public class AddressActivity extends BaseActivity implements AddressContract.View {

    private Double[] coordinates = new Double[4];
    public AddressContract.Presenter presenter = new Presenter();

    @ViewById
    EditText origin_address;

    @ViewById
    EditText destination_address;

    @Click
    public void direction() {

        Intent i = new Intent();
        i.putExtra("isCallBackOk", true);
        setResult(201, i);
        finish();
    }

    @AfterViews
    public void initView() {

        presenter.onAttachView(this);

        Bundle bundle = getIntent().getExtras();
        coordinates[0] = bundle.getDouble("OriginLat");
        coordinates[1] = bundle.getDouble("OriginLon");
        coordinates[2] = bundle.getDouble("DestinationLat");
        coordinates[3] = bundle.getDouble("DestinationLon");

        presenter.onAddressRequest(coordinates);
    }

    @Override
    public void getAddress(String[] adds) {
        if (adds != null) {
            Toast.makeText(this, "address : " + adds[0], Toast.LENGTH_SHORT).show();
            origin_address.setText(adds[0]);
            destination_address.setText(adds[1]);
        }
        else {
            origin_address.setText(R.string.origin_text_failure);
            destination_address.setText(R.string.destination_text_failure);
        }
    }
}
