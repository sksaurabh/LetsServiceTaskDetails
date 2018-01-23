package com.telloquent.letsservicetask.support;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import com.telloquent.letsservicetask.utils.ApplicationConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Telloquent-DM6M on 9/11/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = ApplicationConstants.Db_Name;
    //public String databasePath = "//data//user//0//com.telloquent.vms//databases//Vms";
    public String databasePath ="";
    public DBHelper(Context context)
    {
     super(context, DB_NAME, null, DB_VERSION);
        databasePath = context.getDatabasePath("Vms").getPath();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDepartmentSQL= LocationDB.createLocationSQL();
        db.execSQL(createDepartmentSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
    //TODO copy the database in sqlite magic you can see the data is persent or not

    public static boolean exportDB(Context context) {
        String databasePath = context.getDatabasePath(DB_NAME).getPath();
        String inFileName = databasePath;
        try {
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);
            String outFileName = Environment.getExternalStorageDirectory() + "/" + DB_NAME;
            OutputStream output = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            //Close the streams
            output.flush();
            output.close();
            fis.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
