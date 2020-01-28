package com.mpc_group17.CanteenApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {

    public static final int SCAN_QR_CODE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * View saved canteens
     */
    public void viewSavedCanteens(View view) {
        Intent intent = new Intent(this, SavedCanteensActivity.class);
        startActivity(intent);
    }

    /**
     * Add a new canteen
     */
    public void addCanteen(View view) {
        Intent intent = new Intent(this, AddCanteenActivity.class);
        startActivityForResult(intent, SCAN_QR_CODE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == SCAN_QR_CODE_REQUEST){
            if(resultCode == CommonStatusCodes.SUCCESS) {
                if(intent != null) {
                    String canteenURL = intent.getExtras().getString(
                            getResources().getString(R.string.qrcode)
                    );
                    TextView textView = (TextView) findViewById(R.id.scan_result);
                    textView.setText(canteenURL != null ? canteenURL : "Could not find canteen url");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
}
