package com.telloquent.letsservicetask;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import com.telloquent.letsservicetask.activities.LocationActivity;
import com.telloquent.letsservicetask.utils.ApplicationConstants;
import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    Context context;
    //handler for splashscreen
    Handler mHandler;
    Runnable mLaunchRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        checkPermissionsAndShowPopup();
        intilizationView();

    }


    private void checkPermissionsAndShowPopup() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : ApplicationConstants.mPermissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), ApplicationConstants.MULTIPLE_PERMISSIONS);
            }
        } else{

        }

    }

    private void intilizationView() {
        mHandler = new Handler();
        mLaunchRunnable = new Runnable() {
            @Override
            public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, LocationActivity.class);
                    startActivity(intent);
                    finish();
                }
        };
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(mLaunchRunnable);
        super.onPause();
    }

    @Override
    protected void onResume() {
        mHandler.postDelayed(mLaunchRunnable, SPLASH_TIME_OUT);
        super.onResume();
    }

}
