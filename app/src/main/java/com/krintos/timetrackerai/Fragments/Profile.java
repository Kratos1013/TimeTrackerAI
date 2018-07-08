package com.krintos.timetrackerai.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.krintos.timetrackerai.R;
import com.krintos.timetrackerai.Services.UserService;
import com.krintos.timetrackerai.SessionManager.UserSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment implements View.OnClickListener {
    private final int PICK_IMAGE_REQUEST = 1;
    private ImageView name_edit,username_edit;
    private UserSession us;
    private TextView name, username, phonenumber, edit_enable;
    private Button logout;
    private SwipeRefreshLayout refresh;
    private CircleImageView profilepicture;
    private UserService userService;
    private UserSession client;
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
        client = new UserSession(getContext());
        us = new UserSession(getContext());
        refresh = rootView.findViewById(R.id.refresh);
        logout = rootView.findViewById(R.id.logout);
        name = rootView.findViewById(R.id.name);
        profilepicture = rootView.findViewById(R.id.profile_photo);
        profilepicture.setOnClickListener(this);
        username = rootView.findViewById(R.id.username);
        phonenumber = rootView.findViewById(R.id.phonenumber);
        edit_enable = rootView.findViewById(R.id.edit_enable);
        edit_enable.setOnClickListener(this);
        name_edit = rootView.findViewById(R.id.name_edit);
        name_edit.setOnClickListener(this);
        username_edit = rootView.findViewById(R.id.username_edit);
        username_edit.setOnClickListener(this);
        refreshed();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                us.logoutUser(getActivity());
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshed();
            }
        });
        return rootView;
    }
    private void refreshed() {
        client.getUser(userService.getUser().getToken(),refresh);
        final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 1000ms
                        name.setText(userService.getUser().getName());
                        phonenumber.setText(userService.getUser().getPhoneNumber());
                        username.setText(userService.getUser().getusername());
                    }
                }, 1000);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edit_enable:
                see_all();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        close_all();
                    }
                }, 5000);
                break;
            case R.id.name_edit:
                edit(userService.getUser().getName(),"null",1);
                break;
            case R.id.username_edit:
                edit("null",userService.getUser().getusername(),2);
                break;
            case R.id.profile_photo:
                profile_pic_menu();
                break;
        }
    }
    private void profile_pic_menu() {
        PopupMenu popup = new PopupMenu(getActivity().getApplicationContext(), profilepicture);
        popup.getMenuInflater()
                .inflate(R.menu.profile_pic_picture, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.open:
                        /*Intent intent = new Intent(getActivity().getApplicationContext(), ImageViewer.class);
                        intent.putExtra(ImageViewer.EXTRA_IMAGE , image_url);
                        startActivity(intent);*/
                        return true;
                    case R.id.upload:
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picure"), PICK_IMAGE_REQUEST);
                        return true;
                }
                return false;
            }
        });

        popup.show(); //showing popup menu
    }


    private void edit(final String name, final String username, final int type) {
        String title = null;
        String hint = null;
        String data = null;
        if (type==1){
            data = name;
            title = getString(R.string.name);
            hint = getString(R.string.edit_name);
        }else if (type == 2){
            data = username;
            title = getString(R.string.name);
            hint = getString(R.string.edit_username);
        }
        AlertDialog dialog = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme).create();
        final EditText editText = new EditText(getActivity());
        dialog.setTitle(title);
        dialog.setView(editText);
        editText.setHint(hint);
        editText.setText(data);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, ""+getText(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (type==1){
                    String newname = editText.getText().toString().trim();
                    if (!newname.isEmpty()){
                        client.updateUser(userService.getUser().getToken(),newname,username,"null",refresh);
                    }else {
                        Toast.makeText(getActivity(), ""+getString(R.string.emptyname), Toast.LENGTH_SHORT).show();
                    }
                }if (type == 2){
                    String newusername = editText.getText().toString().trim();
                    if (!newusername.equals("")){
                       client.updateUser(userService.getUser().getToken(),name,newusername,"null",refresh);
                    }else {
                        Toast.makeText(getActivity(), ""+getString(R.string.emptyusername), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, ""+getText(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    private void close_all() {
        name_edit.setVisibility(View.GONE);
        username_edit.setVisibility(View.GONE);
    }
    private void see_all() {
        name_edit.setVisibility(View.VISIBLE);
        username_edit.setVisibility(View.VISIBLE);
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
                profilepicture.setImageBitmap(bitmap);
                us.updateUser(userService.getUser().getToken(),"null",
                        "null",getStringImage(bitmap),refresh);

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
