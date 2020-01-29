package com.mpc_group17.CanteenApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class SubmitOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);

        TextView finalOrder = findViewById(R.id.final_order);
        TextView priceCalculated = findViewById(R.id.price_calculated);

        JSONObject item;
        JSONArray itemList;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            finalOrder.setText(bundle.getString(getResources().getString(R.string.final_order)));
            int price = 0;
            try {
                itemList = new JSONArray(bundle.getString(getResources().getString(R.string.final_order)));
                for (int i = 0; i < itemList.length(); ++i) {
                    item = itemList.getJSONObject(i);
                    price += item.getInt(getResources().getString(R.string.json_item_price)) * item.getInt(getResources().getString(R.string.json_item_quantity));
                }
            } catch (Exception e) {
                Log.e("SubmitOrder.itemList", e.getMessage());
            }
            priceCalculated.setText(Integer.toString(price));
        }
    }
}
