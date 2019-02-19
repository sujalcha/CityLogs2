package app.cl.CityLogs.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


import app.cl.CityLogs.helper.CityLogs;
import app.cl.CityLogs.helper.Utils;


public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "myCityLogs.db";

    // Setting table name
    private static final String TABLE_LOG_ENTRIES = "City";

    // Notice Table Columns names
    private static final String LOG_cityname = "Cityname";
    public static final String LOG_time = "Time";
    public static final String LOG_contact = "Contact";
    public static final String LOG_invoice = "Invoice";
    public static final String LOG_destination = "Destination";

    public static final String LOG_LOG_TYPE = "logType";
    public static final String LOG_CREATED_DATE = "created_date";
    public static final String LOG_UPDATED_DATE = "updated_date";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOG_ENTRIES_TABLE = "CREATE TABLE " + TABLE_LOG_ENTRIES + "("
                + LOG_cityname + " TEXT  ," + LOG_time + " TEXT   ,"
                + LOG_contact + " TEXT  ," + LOG_invoice +  " TEXT   ," + LOG_destination +  " )";
        db.execSQL(CREATE_LOG_ENTRIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+TABLE_LOG_ENTRIES);
        this.onCreate(db);
    }

    public void insertdata() {
        for (CityLogs citylog : Utils.tempcityLogsArrayList) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues content = new ContentValues();
            content.put(LOG_cityname, citylog.getCityname());
            content.put(LOG_time, citylog.getTime());
            content.put(LOG_contact, citylog.getContact());
            content.put(LOG_invoice, citylog.getInvoice());
            content.put(LOG_destination, citylog.getDestination());

            content.put(LOG_LOG_TYPE, citylog.getLogType());
            content.put(LOG_CREATED_DATE, citylog.getCreatedDate());
            content.put(LOG_UPDATED_DATE, citylog.getUpdatedDate());
            db.insert(TABLE_LOG_ENTRIES, null, content);
        }
    }


    public void dropAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_LOG_ENTRIES);
    }

    public ArrayList<CityLogs> getAllLogEntries(String logType) {
        ArrayList<CityLogs> arrayList = new ArrayList<CityLogs>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if (!logType.equals("*"))
            cursor = db.rawQuery("select * from " + TABLE_LOG_ENTRIES + " where " + LOG_LOG_TYPE + " = '" + logType + "'", null);
        else
            cursor = db.rawQuery("select * from " + TABLE_LOG_ENTRIES, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    CityLogs citylog = new CityLogs();
                    citylog.setCityname(cursor.getString(0));
                    citylog.setTime(cursor.getString(1));
                    citylog.setContact(cursor.getString(2));
                    citylog.setInvoice(cursor.getString(3));
                    citylog.setDestination(cursor.getString(4));
                    citylog.setLogType(cursor.getString(5));
                    citylog.setCreatedDate(cursor.getString(6));
                    citylog.setUpdatedDate(cursor.getString(7));

                    arrayList.add(citylog);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {

        }
        return arrayList;
    }
}

