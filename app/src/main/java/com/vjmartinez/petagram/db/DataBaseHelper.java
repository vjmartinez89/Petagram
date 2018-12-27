package com.vjmartinez.petagram.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.vjmartinez.petagram.dto.Contact;
import com.vjmartinez.petagram.utils.PropertiesManager;
import com.vjmartinez.petagram.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String propertiesFileName = "sql.properties";


    public DataBaseHelper(@Nullable Context context) {
        super(context, DatabaseConstants.DB_NAME, null, DatabaseConstants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create contacts table
        db.execSQL(getSqlProperty("create.table.contacts"));
        //Creates contact photos table
        db.execSQL(getSqlProperty("create.table.contacts_photo"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(getSqlProperty("drop.table.contacts_photo"));
        db.execSQL(getSqlProperty("drop.table.contacts"));
    }

    /**
     * Get a property from sql.properties file
     * @param key Thee property key
     * @return The property value
     */
    public String getSqlProperty(String key){
        return PropertiesManager.getProperty(context,
                key, propertiesFileName);
    }
}
