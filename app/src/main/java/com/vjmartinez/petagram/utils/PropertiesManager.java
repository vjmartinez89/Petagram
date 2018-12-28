package com.vjmartinez.petagram.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {

    private static Properties prop = new Properties();
    private static InputStream inputStream;
    private static Context context;
    private static AssetManager assetManager;


    /**
     * Get a property value from file
     * @param appContext The context
     * @param key The property key
     * @param fileName The .properties file name
     * @return The property value
     */
    public static String getProperty(Context appContext, String key, String fileName) {
        try {
            context = appContext;
            assetManager = context.getAssets();
            inputStream = assetManager.open(fileName);
            // load a properties file
            prop.load(inputStream);
            return prop.getProperty(key);
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage(), ex );
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Log.e("ERROR", ex.getMessage(), ex );
                }
            }
        }
        return null;
    }
}
