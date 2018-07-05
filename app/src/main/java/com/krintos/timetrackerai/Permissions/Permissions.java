package com.krintos.timetrackerai.Permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.krintos.timetrackerai.LoginActivity;
import com.krintos.timetrackerai.MainActivity;
import com.krintos.timetrackerai.R;

import java.util.Map;

public class Permissions extends Activity {
    private HorizontalScrollView horizontalScrollView;
    private Button b,b1,b2,b3,b4;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int MY_PERMISSIONS_REQUEST_STORAGE = 98;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        horizontalScrollView = findViewById(R.id.horizontalScroll);
        b1 = findViewById(R.id.location);
        b = findViewById(R.id.storage);
        grantedall();
        autoSmoothScrollfirst(b);
        // Here, thisActivity is the current activity
        b1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_STORAGE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    grantedall();
                    autoSmoothScroll(b1);
                }else {
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    grantedall();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    public void grantedall(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Permissions.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    public void autoSmoothScroll(final Button button) {

        horizontalScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                int vLeft = button.getTop();
                int vRight = button.getBottom();
                int sWidth = horizontalScrollView.getWidth();
                horizontalScrollView.smoothScrollTo(((vLeft + vRight - sWidth) / 2), 0);
            }
        },100);
    }
    public void autoSmoothScrollfirst(final Button button) {

        horizontalScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                int vLeft = button.getLeft();
                int vRight = button.getRight();
                int sWidth = horizontalScrollView.getWidth();
                horizontalScrollView.smoothScrollTo(((vLeft + vRight - sWidth) / 2), 0);
            }
        },100);
    }


}
