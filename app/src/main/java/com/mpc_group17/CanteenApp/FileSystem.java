package com.mpc_group17.CanteenApp;

import android.content.Context;
import android.util.Log;

import java.io.*;
import java.nio.Buffer;

public class FileSystem {

    /**
     * Read from file
     */
    public static String readFile(Context context, String fileName) throws FileNotFoundException {

        StringBuilder content = new StringBuilder();
        FileInputStream fileInputStream;
        BufferedReader bufferedReader;

        try {
            fileInputStream = context.openFileInput(fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append('\n');
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (Exception e) {
            Log.e("FileSystem.readFile",
                    (e.getMessage() != null) ? e.getMessage() : "Unknown exception");
        }
        return content.toString();
    }

    /**
     * Write to file
     */
    public static void writeFile(Context context, String fileName, String data)
            throws FileNotFoundException {

        FileOutputStream fileOutputStream;

        try {
            fileOutputStream = context.openFileOutput(fileName, context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (Exception e) {
            Log.e("FileSystem.writeFile",
                    (e.getMessage() != null) ? e.getMessage() : "Unknown exception");
        }
    }

}
