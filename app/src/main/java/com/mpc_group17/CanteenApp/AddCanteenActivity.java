package com.mpc_group17.CanteenApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;

import com.google.android.gms.common.api.CommonStatusCodes;

public class AddCanteenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_canteen);
        returnDummyURL();
    }

    private void returnDummyURL(){
        Intent intent = new Intent();
        intent.putExtra(getResources().getString(R.string.qrcode), "https://canteen-app-mpc2020.herokuapp.com");
        setResult(CommonStatusCodes.SUCCESS, intent);
        finish();
    }
}
