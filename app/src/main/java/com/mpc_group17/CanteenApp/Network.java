package com.mpc_group17.CanteenApp;

import android.util.Log;

import java.net.*;
import java.io.*;

public class Network {

    /**
     * Fetch data from a given URL
     */
    public static String fetchFromURL(String urlAddress) {

        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(urlAddress);
            URLConnection connection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append('\n');
            }
            return content.toString();
        } catch (Exception e) {
            Log.e("Network.fetchFromURL", (e.getMessage() != null) ? e.getMessage() : "Unknown exception");
        }
        return content.toString();
    }
}
