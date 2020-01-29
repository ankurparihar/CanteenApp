package com.mpc_group17.CanteenApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PlaceOrderActivity extends AppCompatActivity {

    private String canteenRootURL;
    private TextView canteenNameView;
    private JSONObject canteenData;
    ArrayList<FoodItem> foodItems;

    ListView listView;
    private static FoodItemListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        foodItems = new ArrayList<FoodItem>();
        setCanteenData();
    }

    /**
     * Display Canteen name and items
     */
    private void showLayout() {
        if (canteenData == null) return;

        // Display canteen name
        canteenNameView = findViewById(R.id.canteen_name_view);
        String canteenName = "<Unknown canteen>";
        try {
            canteenName = canteenData.get(getResources().getString(R.string.json_canteen_name)).toString();
        } catch (Exception e) {
            Log.e("PlaceOrder.CanteenName",
                    e.getMessage() != null ? e.getMessage() : "Unknown exception");
        } finally {
            canteenNameView.setText(canteenName);
        }

        // Parse JSON for food items

        JSONArray itemList;
        try {
            itemList = (JSONArray) canteenData.get(getResources().getString(R.string.json_item_list));
            // Iterate over itemList and convert to FoodItems
            for (int i = 0; i < itemList.length(); ++i) {
                JSONObject object = itemList.getJSONObject(i);
                foodItems.add(new FoodItem(
                        object.getString(getResources().getString(R.string.json_item_name)),
                        object.getString(getResources().getString(R.string.json_item_description)),
                        object.getString(getResources().getString(R.string.json_item_imageURL)),
                        object.getInt(getResources().getString(R.string.json_item_price)))
                );
            }
        } catch (Exception e) {
            Log.e("PlaceOrder.ItemList",
                    e.getMessage() != null ? e.getMessage() : "Unknown exception");
        }

        // Dynamically display food items
        listView = (ListView) findViewById(R.id.food_item_list);
        adapter = new FoodItemListAdapter(foodItems, getApplicationContext());

        listView.setAdapter(adapter);
    }

    /**
     * Get canteen data from canteen server in JSON format
     */
    private void setCanteenData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            canteenData = null;
            return;
        }
        canteenRootURL = bundle.getString(getResources().getString(R.string.canteenURL));
        try {
            new getJSONObjectFromURL().execute(
                    new URL(canteenRootURL + '/' + getResources().getString(R.string.canteenDataURL))
            );
        } catch (Exception e) {
            Log.e("PlaceOrder.setCanteen",
                    e.getMessage() != null ? e.getMessage() : "Unknown exception");
        }
    }

    class getJSONObjectFromURL extends AsyncTask<URL, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
        }

        protected String doInBackground(URL... urls) {

            try {
                URL url = urls[0];
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            try {
                canteenData = new JSONObject(response);
                showLayout();
            } catch (Exception e) {
                Log.e("PlaceOrder.onPostExe", e.getMessage());
            }
        }
    }

    /**
     * Place the order to canteen server
     */
    public void placeOrder(View view) {

        // Iterate through all items in list and update `foodItems`
        JSONArray selectedItems = new JSONArray();
        View v;
        EditText e;
        TextView name, price;
        int quantity;
        JSONArray itemList;
        JSONObject object;

        try {
            itemList = (JSONArray) canteenData.get(getResources().getString(R.string.json_item_list));

            for (int i = 0; i < listView.getCount(); ++i) {
                v = listView.getChildAt(i);
                e = (EditText) v.findViewById(R.id.food_quantity);
                name = (TextView) v.findViewById(R.id.food_name);
                price = (TextView) v.findViewById(R.id.food_price);
                quantity = Integer.parseInt(e.getText().toString());
                if (quantity > 0) {
                    selectedItems.put(new JSONObject()
                            .put(getResources().getString(R.string.json_item_name), name.getText().toString())
                            .put(getResources().getString(R.string.json_item_quantity), quantity)
                            .put(getResources().getString(R.string.json_item_price), Integer.parseInt(price.getText().toString()))
                    );
                }
            }
        } catch (Exception ex) {
            Log.e("PlaceOrderA.placeOrder", ex.getMessage());
        }

        Intent intent = new Intent(this, SubmitOrderActivity.class);
        intent.putExtra(getResources().getString(R.string.final_order), selectedItems.toString());
        startActivity(intent);
    }
}

