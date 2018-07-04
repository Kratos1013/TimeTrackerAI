package com.krintos.timetrackerai;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.krintos.timetrackerai.Fragments.Dashboard;
import com.krintos.timetrackerai.Fragments.Profile;
import com.krintos.timetrackerai.Fragments.Statistics;
import com.krintos.timetrackerai.Permissions.Permissions;
import com.krintos.timetrackerai.SessionManager.SessionManager;
import com.krintos.timetrackerai.SessionManager.UserSession;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private SessionManager session;
    private UserSession us;
    private BottomNavigationView navigation;
    public ProgressDialog pDialog;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.dashboard:
                    if(fragmentManager.findFragmentByTag("dashbord") != null) {
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("dashboard")).commit();
                    } else {
                        fragmentManager.beginTransaction().add(R.id.content_main, new Dashboard(), "dashboard").commit();
                    }
                    if(fragmentManager.findFragmentByTag("statistics") != null){
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("statistics")).commit();
                    }
                    if(fragmentManager.findFragmentByTag("profile") != null){
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("profile")).commit();
                    }

                    return true;
                case R.id.statistics:
                    if(fragmentManager.findFragmentByTag("statistics") != null) {
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("statistics")).commit();
                    } else {
                        fragmentManager.beginTransaction().add(R.id.content_main, new Statistics(), "statistics").commit();
                    }
                    if(fragmentManager.findFragmentByTag("dashboard") != null){
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("dashboard")).commit();
                    }
                    if(fragmentManager.findFragmentByTag("profile") != null){
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("profile")).commit();
                    }
                    return true;
                case R.id.profile:
                    if(fragmentManager.findFragmentByTag("profile") != null) {
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("profile")).commit();
                    } else {
                        fragmentManager.beginTransaction().add(R.id.content_main, new Profile(), "profile").commit();
                    }
                    if(fragmentManager.findFragmentByTag("statistics") != null){
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("statistics")).commit();
                    }
                    if(fragmentManager.findFragmentByTag("dashboard") != null){
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("dashboard")).commit();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActiveAndroid.initialize(this);
        us = new UserSession(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        pDialog = new ProgressDialog(this);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (!session.isLoggedIn()) {
            us.logoutUser();
        }
        grantedall();
        pDialog.setCancelable(false);
    }

    @Override
    public void onBackPressed() {

            int count = getFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                super.onBackPressed();
                //additional code
            } else {
                getFragmentManager().popBackStack();
            }

    }
    public void runfirsfragment(){
        Fragment profile;
        profile = new Profile();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_main, profile,"profile");
        ft.commit();
        navigation.getMenu().findItem(R.id.profile).setChecked(true);

    }
    public void grantedall(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {

            //permission is granted
                runfirsfragment();

        }else {
            Intent intent = new Intent(MainActivity.this, Permissions.class);
            startActivity(intent);
            finish();
        }
    }
    public void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
