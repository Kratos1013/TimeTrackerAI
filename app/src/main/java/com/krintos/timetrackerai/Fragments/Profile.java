package com.krintos.timetrackerai.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.krintos.timetrackerai.LoginActivity;
import com.krintos.timetrackerai.MainActivity;
import com.krintos.timetrackerai.Models.User;
import com.krintos.timetrackerai.R;
import com.krintos.timetrackerai.Services.UserService;
import com.krintos.timetrackerai.SessionManager.SessionManager;
import com.krintos.timetrackerai.SessionManager.UserSession;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
    private UserSession us;
    private Button logout, namechange, usernamechange;
    private EditText name, username;
    private UserService userService;
    public Profile() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        userService = new UserService();
        us = new UserSession(getContext());
        logout = rootView.findViewById(R.id.logout);
        name = rootView.findViewById(R.id.name);
        username = rootView.findViewById(R.id.username);
        namechange = rootView.findViewById(R.id.namechange);
        usernamechange = rootView.findViewById(R.id.usernamechange);
        namechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  gname = name.getText().toString().trim();
                String  guname = "null";
                User user = userService.getUser();
                us.updateUser(user.getToken(),gname,guname);
            }
        });
        usernamechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gname = "null";
                String gusername = username.getText().toString().trim();
                User user = userService.getUser();
                us.updateUser(user.getToken(),gname,gusername);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                us.logoutUser();
            }
        });

        return rootView;
    }




}
