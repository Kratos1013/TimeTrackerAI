package com.krintos.timetrackerai.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.krintos.timetrackerai.Models.User;
import com.krintos.timetrackerai.R;
import com.krintos.timetrackerai.Services.UserService;
import com.krintos.timetrackerai.SessionManager.UserSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
    private final int PICK_IMAGE_REQUEST = 1;
    private UserSession us;
    private Button logout, namechange, usernamechange,select;
    private ImageView image;
    private EditText name, username;
    private UserService userService;
    private String imagePath;
    private int column_index;
    private Bitmap bitmap;

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
        select = rootView.findViewById(R.id.imagepicker);

        image = rootView.findViewById(R.id.pimage);
        namechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  gname = name.getText().toString().trim();
                String  guname = "null";
                String filePath = "null";
                User user = userService.getUser();
                us.updateUser(user.getToken(),gname,guname, filePath);
                Toast.makeText(getContext(), ""+userService.getUser().getPicName(), Toast.LENGTH_SHORT).show();

            }
        });
        usernamechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gname = "null";
                String filePath = "null";
                String gusername = username.getText().toString().trim();
                User user = userService.getUser();
                us.updateUser(user.getToken(),gname,gusername, filePath);
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picure"), PICK_IMAGE_REQUEST);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //uploading the image
                image.setImageBitmap(bitmap);
                String  guname = "null";
                String  gname = "null";
                us.updateUser(userService.getUser().getToken(),gname,guname,getStringImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}
