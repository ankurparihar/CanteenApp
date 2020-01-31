package com.mpc_group17.CanteenApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.CompoundButton;
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
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        startActivityForResult(intent, SCAN_QR_CODE_REQUEST);
//        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == SCAN_QR_CODE_REQUEST) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (intent != null) {
                    String canteenURL;
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        canteenURL = intent.getExtras().getString(
                                getResources().getString(R.string.qrcode)
                        );
                    } else {
                        Log.e("Main.ActivityResult", "Invalid or NULL data");
                        return;
                    }
                    if (URLUtil.isValidUrl(canteenURL)) {
                        Intent placeOrderActivityIntent = new Intent(
                                this,
                                PlaceOrderActivity.class
                        );
                        placeOrderActivityIntent.putExtra(
                                getResources().getString(R.string.canteenURL),
                                canteenURL
                        );
                        startActivity(placeOrderActivityIntent);
                    } else {
                        Log.e("Main.ValidateURL", "Invalid URL");
                    }
                }
            }
        }
    }
}
