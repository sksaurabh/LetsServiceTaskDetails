package com.telloquent.letsservicetask.utils;

import android.Manifest;
import android.os.Environment;

import java.text.SimpleDateFormat;

public interface ApplicationConstants
{

    String Db_Name="LetsService";
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    int MULTIPLE_PERMISSIONS = 211;
    String[] mPermissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    SimpleDateFormat mDateTimeFormat=new SimpleDateFormat("dd-MM-yyyy hh:mm aaa");
    int CAMERA_PIC_REQUEST = 0;
    int mOneday=86400000;

}
