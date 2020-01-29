package com.mpc_group17.CanteenApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.FileNotFoundException;

public class SavedCanteensActivity extends AppCompatActivity {

    private JSONObject savedCanteens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_canteens);

        try {
            String savedCanteenData = FileSystem.readFile(this,
                    getResources().getString(R.string.save_data_file));
        } catch (FileNotFoundException e) {
            Log.i("SavedCanteen readfile", "File not found");
        } catch (Exception e) {
            Log.e("SavedCanteen readfile",
                    e.getMessage() != null ? e.getMessage() : "Unknown exception");
        }
    }
}
