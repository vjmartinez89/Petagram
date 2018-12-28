package com.vjmartinez.petagram.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Class tha contains utilities for files usage
 * @author Victor Martinez
 * @since 24/12/2018
 */
public class FileUtils {

    private static String PREFERENCES_FILE_NAME = "petagramPreferences";

    /**
     * Creates a simple text file
     * @param activity The activity tha creates the file
     * @param fileName The name of the file (must be contain .txt extension)
     * @param mode The mode of the file creation ( See Context.MODE_PRIVATE and others)
     */
    public static boolean createSimpleTextFile(Activity activity, String fileName,
                                            String content, Integer mode){
        FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = activity.openFileOutput(StringUtils.isEmpty(fileName)
                    ? "simpleFile.txt" : fileName, mode != null ? mode : Context.MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
            return true;
        }catch(IOException io){
            Log.e("ERROR", io.getMessage(), io);
            MessageUtil.showToast(activity.getBaseContext(), io.getMessage(),
                    Toast.LENGTH_SHORT);
            return false;
        }finally {
            if(fileOutputStream != null){
                try{
                    fileOutputStream.close();
                }catch(Exception ex){
                    MessageUtil.showToast(activity.getBaseContext(),
                            "Error creando el archivo",
                            Toast.LENGTH_SHORT);
                    Log.e("ERROR", ex.getMessage(), ex);
                }
            }
        }
    }

    /**
     * Create a list of shared preferences
     * @param preferences The map of preferences
     * @param activity The activity
     * @return true: The shared preference was created successful, false: otherwise
     */
    public static boolean createSharedPreferences(Activity activity, Map<String,
            String> preferences)
    {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(PREFERENCES_FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (Map.Entry<String, String> entry : preferences.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.commit();
        return true;
    }

    /**
     * Obtain the complete map of preferences
     * @param activity The activity
     * @return A map with all preferences
     */
    public static Map<String, String> getSharedPreferences(Activity activity)
    {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(PREFERENCES_FILE_NAME,
                Context.MODE_PRIVATE);
        return (Map<String, String>) sharedPreferences.getAll();
    }
}
