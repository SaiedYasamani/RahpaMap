package com.rahpa.yasamani.rahpamap;

import android.app.Activity;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_base)
public class BaseActivity extends AppCompatActivity {

    Context mContext = this;
    Activity mActivity = this;
}
