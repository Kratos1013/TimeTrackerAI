package com.krintos.timetrackerai.Connection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.krintos.timetrackerai.LoginActivity;

/**
 * Created by root on 7/4/18.
 */

public class Connection {
    private Context context;
    public Connection(Context context){
        this.context = context;

    }
    public void checconnectivity(Activity activity) {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        assert connectivityManager != null;
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        if (!connected) {
            Intent intent = new Intent(context, ErrorController.class);
            intent.putExtra(ErrorController.EXTRA_EDITOR , "noConnection");
            context.startActivity(intent);
            activity.finish();
        }
    }
}
