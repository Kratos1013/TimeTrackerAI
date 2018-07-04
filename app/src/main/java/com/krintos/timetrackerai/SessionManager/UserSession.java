package com.krintos.timetrackerai.SessionManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
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
import com.krintos.timetrackerai.MainActivity;
import com.krintos.timetrackerai.R;
import com.krintos.timetrackerai.Services.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 7/3/18.
 */

public class UserSession  {
    Context context;
    private MainActivity mainActivity;
    private Profile profile;
    private SessionManager session;
    private ProgressDialog pDialog;
    private UserService userService;
   public UserSession(Context context){
       this.context = context;
       this.mainActivity = new MainActivity();
       this.profile = new Profile();
       this.session = new SessionManager(context);
       this.pDialog = new ProgressDialog(context);
       this.userService = new UserService();
   }
   public void getUser(final String token){
       showDialog();
       pDialog.setMessage(""+ context.getResources().getString(R.string.waitphone));
       String tag_string_req = "req_userdatas";

       StringRequest strReq = new StringRequest(Request.Method.POST,
               AppConfig.URL_GETUSER, new Response.Listener<String>() {

           @Override
           public void onResponse(String response) {
               try {
                   hideDialog();
                   ObjectMapper  objectMapper = new ObjectMapper();
                   User userUpdated = objectMapper.readValue(response, User.class);
                   userService.updateUser(userUpdated);
               } catch (IOException e) {
                   e.printStackTrace();
               }
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
    public void logoutUser() {
        session.setLogin(false);
        // Launching the login activity
        userService.deleteUser();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        mainActivity.finish();
    }
    public void updateUser(final String token, final String name, final String username){
        showDialog();
        pDialog.setMessage(""+ context.getResources().getString(R.string.waitphone));
        String tag_string_req = "req_update_user";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE_USER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
            getUser(userService.getUser().getToken());
                Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        ""+ error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();
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
                return params;

            }



        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

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
