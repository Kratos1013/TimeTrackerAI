package com.krintos.timetrackerai.Connection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.krintos.timetrackerai.R;

public class ErrorController extends AppCompatActivity {
    public static final String EXTRA_EDITOR = "error_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_controller);
    }
}
