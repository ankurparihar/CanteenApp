package com.mpc_group17.CanteenApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class SubmitOrderActivity extends AppCompatActivity {

    private JSONArray itemList;
    private String canteenRootURL;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);

        TextView finalOrder = findViewById(R.id.final_order_list);
        TextView priceCalculated = findViewById(R.id.order_total);
        submitButton = findViewById(R.id.btn_submit_order);

        JSONObject item;
        StringBuilder orderSummary = new StringBuilder();

        Bundle bundle = getIntent().getExtras();
        canteenRootURL = bundle.getString(getResources().getString(R.string.canteenURL));

        if (bundle != null) {

            int price = 0;
            int lPrice, lQuantity;
            try {
                itemList = new JSONArray(bundle.getString(getResources().getString(R.string.final_order)));
                for (int i = 0; i < itemList.length(); ++i) {
                    item = itemList.getJSONObject(i);
                    lQuantity = item.getInt(getResources().getString(R.string.json_item_quantity));
                    lPrice = item.getInt(getResources().getString(R.string.json_item_price));
                    price += lPrice * lQuantity;
                    orderSummary.append(String.format(Locale.US, "%-20s (%-4d) x%3d  = %5d \n", item.getString(getResources().getString(R.string.json_item_name)), lPrice, lQuantity, lPrice * lQuantity));
                }
            } catch (Exception e) {
                Log.e("SubmitOrder.itemList", e.getMessage());
            }
            finalOrder.setText(orderSummary.toString());
            priceCalculated.setText(String.format(Locale.UK, "Order Total: %d", price));
            if (price > 0) {
                submitButton.setVisibility(View.VISIBLE);
            }
        }
    }

    class sendJSONOArrayToURL extends AsyncTask<URL, Void, Pair<Integer, String>> {

        private Exception exception;

        protected void onPreExecute() {
        }

        protected Pair<Integer, String> doInBackground(URL... urls) {

            try {
                URL url = urls[0];
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
//                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//                urlConnection.setRequestProperty("Accept","application/json");
//                urlConnection.setDoOutput(true);
//                urlConnection.setDoInput(true);

                try {
                    DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                    String response;

                    dataOutputStream.writeBytes(itemList.toString());
                    dataOutputStream.flush();
                    dataOutputStream.close();

                    return new Pair<Integer, String>(urlConnection.getResponseCode(), urlConnection.getResponseMessage());
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(Pair<Integer, String> response) {
            TextView orderStatus = findViewById(R.id.order_status);
            orderStatus.setVisibility(View.VISIBLE);
            if (response.first == 200 && response.second.equals("OK")) {
                // success
                orderStatus.setText(getResources().getString(R.string.success_order));
                orderStatus.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.status_order_ok));
                submitButton.setVisibility(View.GONE);
            } else {
                // failure
                orderStatus.setText(getResources().getString(R.string.failed_order));
                orderStatus.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.status_order_fail));
            }
        }
    }

    /**
     * Place the order to canteen server
     */
    public void submitOrder(View view) {
        try {
            new sendJSONOArrayToURL().execute(new URL(
                    canteenRootURL + '/' + getResources().getString(R.string.canteenOrderURL)
            ));
        } catch (Exception ex) {
            Log.e("PlaceOrderA.placeOrder", ex.getMessage());
        }
    }
}
