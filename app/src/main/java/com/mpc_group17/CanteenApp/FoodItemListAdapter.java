package com.mpc_group17.CanteenApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Custom view adapter
 * Source: https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
 */
public class FoodItemListAdapter extends ArrayAdapter<FoodItem> implements View.OnClickListener {

    public ArrayList<FoodItem> foodItems;
    Context context;

    private static class ViewHolder {
        TextView foodName;
        TextView foodDescription;
        TextView foodPrice;
        EditText foodQuantity;
    }

    public FoodItemListAdapter(ArrayList<FoodItem> foodItems, Context context) {
        super(context, R.layout.item, foodItems);
        this.foodItems = foodItems;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        FoodItem foodItem = getItem(position);
        if (v.getId() == R.id.food_quantity) {
            // do something here
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        FoodItem foodItem = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item, parent, false);
            viewHolder.foodName = (TextView) convertView.findViewById(R.id.food_name);
            viewHolder.foodDescription = (TextView) convertView.findViewById(R.id.food_description);
            viewHolder.foodPrice = (TextView) convertView.findViewById(R.id.food_price);
            viewHolder.foodQuantity = (EditText) convertView.findViewById(R.id.food_quantity);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.foodName.setText(foodItem.getName());
        viewHolder.foodDescription.setText(foodItem.getDescription());
        viewHolder.foodPrice.setText(Integer.toString(foodItem.getPrice()));

        return convertView;
    }
}
