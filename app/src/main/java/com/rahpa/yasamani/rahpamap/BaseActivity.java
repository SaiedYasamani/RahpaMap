package com.rahpa.yasamani.rahpamap;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_base)
public class BaseActivity extends AppCompatActivity {

    Context mContext = this;
    Activity mActivity = this;
}
