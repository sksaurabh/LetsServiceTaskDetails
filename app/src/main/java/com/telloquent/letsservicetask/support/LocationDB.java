package com.telloquent.letsservicetask.support;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.telloquent.letsservicetask.model.Locationbeans;


public class LocationDB {

    //Table Name
    public static final String Location = "location";
    //Table Field Namese
    private static final String ID = "id";
    private static final String Latitute = "latitute";
    private static final String Longitute = "longitute";
    private static final String Time = "time";
    private DBHelper dbh;
    private Context ctx;

    public LocationDB(Context ctx) {
        this.ctx = ctx;
        dbh = new DBHelper(ctx);
    }

    //DB Related Functions
    public static String createLocationSQL() {
        return String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT,%s TEXT,%s TEXT)", Location, ID, Latitute,Longitute,Time);
    }

    public long insertLocations(Locationbeans locationbeans) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        long mTotalInsertedValues = 0;
        try {
            db.execSQL("DELETE FROM Location");
            Log.i("TAG", "Done");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "Error");
        }
        try {
            if (locationbeans != null) {
                ContentValues values = new ContentValues();
                    values.put(Latitute, locationbeans.getmLatitute());
                    values.put(Longitute, locationbeans.getmLongitute());
                    values.put(Time, locationbeans.getmTime());
                    long rowId = db.insert(Location, null, values);
                    if (rowId != 0) {
                        mTotalInsertedValues++;
                    }
            } else {
                Log.i("TAG", "value Null or Empty");
            }
            Log.i("TAG", "Rows " + mTotalInsertedValues);
        } catch (Exception ex) {
        } finally {
            dbh.exportDB(ctx);
            db.close();
        }
        return mTotalInsertedValues;
    }


    public Locationbeans getLocationbeansDetails() {
        Locationbeans locationDetails = null;
        SQLiteDatabase db = dbh.getReadableDatabase();
        try {
            String selectQuery = String.format("SELECT  * FROM Location");
            Cursor c = db.rawQuery(selectQuery, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    locationDetails = new Locationbeans();
                    locationDetails.setmLatitute(c.getString(c.getColumnIndex(Latitute)));
                    locationDetails.setmLongitute(c.getString(c.getColumnIndex(Longitute)));
                    locationDetails.setmTime(c.getString(c.getColumnIndex(Time)));
                    c.moveToNext();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            dbh.closeDB();
        }
        return locationDetails;
    }
}





