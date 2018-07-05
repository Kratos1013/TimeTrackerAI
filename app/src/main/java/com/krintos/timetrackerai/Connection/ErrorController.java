package com.krintos.timetrackerai.Connection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.krintos.timetrackerai.R;

public class ErrorController extends AppCompatActivity {
    public static final String EXTRA_EDITOR = "error_type";
    private Button retry;
    private ImageView imagetype;
    private FrameLayout conn_error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_controller);
        retry = findViewById(R.id.retry);
        imagetype = findViewById(R.id.imageType);
        conn_error = findViewById(R.id.connectionerror);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EXTRA_EDITOR.equals("noConnection")){

                }
            }
        });
    }
}
