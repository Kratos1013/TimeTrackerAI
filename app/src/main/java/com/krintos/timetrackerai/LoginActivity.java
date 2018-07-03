package com.krintos.timetrackerai;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.krintos.timetrackerai.Database.SQLiteHandler;
import com.krintos.timetrackerai.Helper.AppConfig;
import com.krintos.timetrackerai.Helper.AppController;
import com.krintos.timetrackerai.SessionManager.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private SessionManager session;
    private EditText phonenumber, pincode;
    private Button send, confrim;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private String phone, pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new SQLiteHandler(getApplicationContext());
        phonenumber = findViewById(R.id.phonenumber);
        pincode = findViewById(R.id.pincode);
        send = findViewById(R.id.send);
        confrim = findViewById(R.id.confirm);
        session = new SessionManager(getApplicationContext());
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = String .valueOf(phonenumber.getText().toString().trim());
                if (phone.equals("")){
                    //TODO what if phone number field is empty
                }else {
                    sendphonenumber(phone);
                }
            }
        });
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pin = String.valueOf(pincode.getText().toString().trim());
                if (pin.equals("")){
                    //TODO what if phone number field is empty

                }else {
                    confirm();

                }
            }
        });
    }

    private void confirm() {
        showDialog();
        pDialog.setMessage(""+getString(R.string.waitphone));
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONObject jObj = null;
                try {
                    hideDialog();
                    jObj = new JSONObject(response);
                    String token  = jObj.getString("token");
                    boolean store = db.storeuser(phone,token,SQLiteHandler.TABLE_USER);
                    if (store){
                        View view = getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        login();
                    }else {
                        Toast.makeText(LoginActivity.this, ""+getString(R.string.couldntstore), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String , String > params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }*/

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", phone);
                params.put("pincode",pin);
                return params;

            }



        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void sendphonenumber(final String phone) {
        String tag_string_req = "req_login";

        showDialog();
        pDialog.setMessage(""+getString(R.string.waitphone));

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SIGN_UP, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONObject jObj = null;
                hideDialog();
                try {
                    jObj = new JSONObject(response);
                    phonenumber.setVisibility(View.GONE);
                    send.setVisibility(View.GONE);
                    pincode.setVisibility(View.VISIBLE);
                    confrim.setVisibility(View.VISIBLE);
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    String pin = jObj.getString("pincode");
                    pincode.setText(pin);
                    Toast.makeText(LoginActivity.this, ""+pin, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            // Posting parameters to login url

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phoneNumber", phone);
                return params;

            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    private void login() {
        session.setLogin(true);
        // Launch main activity
        Intent intent = new Intent(LoginActivity.this,
                MainActivity.class);
        startActivity(intent);
        finish();

    }
    /*hideDialog();
                try {
        JSONObject jObj = new JSONObject(response);
        boolean error = jObj.getBoolean("error");
        // Check for error node in json

        if (!error) {
            phonenumber.setVisibility(View.GONE);
            send.setVisibility(View.GONE);
            pincode.setVisibility(View.VISIBLE);
            confrim.setVisibility(View.VISIBLE);
            //hide keyboard
            View view = getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            String pin = jObj.getString("pin");
            Toast.makeText(LoginActivity.this, ""+pin, Toast.LENGTH_LONG).show();
        } else {
            // Error in login. Get the error message
            String errorMsg = jObj.getString("error_msg");
            Toast.makeText(getApplicationContext(),
                    errorMsg, Toast.LENGTH_LONG).show();
        }
    } catch (JSONException e) {
        // JSON error
        e.printStackTrace();
        Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }

}*/
}
