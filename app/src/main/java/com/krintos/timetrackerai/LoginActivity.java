package com.krintos.timetrackerai;

import android.app.ActionBar;
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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hbb20.CountryCodePicker;
import com.krintos.timetrackerai.Connection.Connection;
import com.krintos.timetrackerai.Helper.AppConfig;
import com.krintos.timetrackerai.Helper.AppController;
import com.krintos.timetrackerai.Services.UserService;
import com.krintos.timetrackerai.SessionManager.SessionManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private SessionManager session;
    private EditText phonenumber, pincode;
    private Button send, confrim;
    private ProgressDialog pDialog;
    private String phone, pin;
    private Connection connection;
    private UserService userService;
    private CountryCodePicker ccp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        userService = new UserService();
        connection = new Connection(getApplicationContext());
        phonenumber = findViewById(R.id.phonenumber);
        pincode = findViewById(R.id.pincode);
        send = findViewById(R.id.send);
        ccp = findViewById(R.id.countryCodePicker);
        confrim = findViewById(R.id.confirm);
        session = new SessionManager(getApplicationContext());
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        ccp.registerCarrierNumberEditText(phonenumber);
        ccp.isValidFullNumber();

        if (session.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phone.length()< 4){
                    Toast.makeText(LoginActivity.this, ""+getString(R.string.emptyphone), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(LoginActivity.this, ""+getString(R.string.emptypin), Toast.LENGTH_SHORT).show();
                }else {
                    confirm();

                }
            }
        });
        ccp.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                phone = ccp.getFullNumberWithPlus();
                hideKeyboard(LoginActivity.this);
            }
        });
    }
    private void confirm() {
        connection.checconnectivity(this);
        showDialog();
        pDialog.setMessage(""+getString(R.string.logininproccess));
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
                    userService.saveUser(phone,token);
                    login();
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
        connection.checconnectivity(this);
        String tag_string_req = "request_pin";
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
                    ccp.setVisibility(View.GONE);
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
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
