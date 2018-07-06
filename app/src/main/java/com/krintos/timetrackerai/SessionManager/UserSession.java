package com.krintos.timetrackerai.SessionManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krintos.timetrackerai.Fragments.Profile;
import com.krintos.timetrackerai.Helper.AppConfig;
import com.krintos.timetrackerai.Helper.AppController;
import com.krintos.timetrackerai.Models.User;
import com.krintos.timetrackerai.LoginActivity;
import com.krintos.timetrackerai.R;
import com.krintos.timetrackerai.Services.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 7/3/18.
 */

public class UserSession  {
    Context context;
    private Profile profile;
    private SessionManager session;
    private ProgressDialog pDialog;
    private UserService userService;
    private boolean status;
   public UserSession(Context context){
       this.context = context;
       this.profile = new Profile();
       this.session = new SessionManager(context);
       this.pDialog = new ProgressDialog(context);
       this.userService = new UserService();
   }
   public boolean getUser(final String token){
       final int[] ok = {0};
       String tag_string_req = "req_userdatas";

       StringRequest strReq = new StringRequest(Request.Method.POST,
               AppConfig.URL_GETUSER, new Response.Listener<String>() {

           @Override
           public void onResponse(String response) {
               try {
                   ObjectMapper  objectMapper = new ObjectMapper();
                   User userUpdated = objectMapper.readValue(response, User.class);
                   userService.updateUser(userUpdated);
                   ok[0] = 1;
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }, new Response.ErrorListener() {

           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(context,
                      ""+ error.getMessage(), Toast.LENGTH_LONG).show();
               ok[0] = 0;
           }
       }) {


           @Override
           protected Map<String, String> getParams() {
               Map<String, String> params = new HashMap<String, String>();
               params.put("token", token );
               return params;

           }
       };

       // Adding request to request queue
       AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
       if (ok[0]==1){
           return false;
       }else {
           return true;
       }
   }
    public void logoutUser(Activity activity) {
        session.setLogin(false);
        // Launching the login activity
        userService.deleteUser();
        Intent intent = new Intent(activity, LoginActivity.class);
        context.startActivity(intent);
        activity.finish();
    }
    public boolean updateUser(final String token, final String name, final String username, final String filePath){
        final int[] ok = {0};
        String tag_string_req = "req_update_user";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE_USER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
            getUser(userService.getUser().getToken());
                Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                ok[0] = 1;

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ok[0] = 0;

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token );
                if (!name.equals("null")){
                    params.put("name", name );
                }
                if (!username.equals("null")){
                    params.put("username", username );
                }
                if (!filePath.equals("null")){
                    params.put("imageBytes",filePath);
                }
                return params;

            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        if (ok[0]==1){
            return false;
        }else {
            return true;
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
    public void getUserPhoto(final String token){
        showDialog();
        pDialog.setMessage(""+ context.getResources().getString(R.string.waitphone));
        String tag_string_req = "req_userdatas";

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_GETUSERPHOTO+userService.getUser().getPicName(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "*************************"+ error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token );
                return params;

            }



        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

}
