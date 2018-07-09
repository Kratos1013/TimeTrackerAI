package com.krintos.timetrackerai.Fragments.Helper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.krintos.timetrackerai.Helper.Helper;
import com.krintos.timetrackerai.R;

import java.util.ArrayList;
import java.util.List;

import in.goodiebag.carouselpicker.CarouselPicker;

public class add_to_planner extends Fragment implements View.OnClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private TextView day,stime,ftime;
    private EditText taskname;
    private Spinner minutePicker;
    private int pickedIcon,pickedMinute;
    private Button cancel, save;
    private CarouselPicker iconPicker;
    private Helper helper;
    private Switch notifier;
    private List<CarouselPicker.PickerItem> images = new ArrayList<>();
    public add_to_planner() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_add_to_planner, container, false);
        final int  position = getArguments().getInt(ARG_SECTION_NUMBER);
        helper = new Helper(getContext());
        minutePicker = rootView.findViewById(R.id.minutepicker);
        day = rootView.findViewById(R.id.day);
        iconPicker = rootView.findViewById(R.id.iconpicker);
        stime = rootView.findViewById(R.id.stime);
        ftime = rootView.findViewById(R.id.ftime);
        taskname = rootView.findViewById(R.id.taskname);
        notifier = rootView.findViewById(R.id.notifier);
        cancel = rootView.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        save = rootView.findViewById(R.id.save);
        save.setOnClickListener(this);
        day.setText(helper.getday(position));
        iconPicker =  rootView.findViewById(R.id.iconpicker);
        CarouselPicker.CarouselViewAdapter iconadapter = new CarouselPicker.CarouselViewAdapter(getActivity(), helper.getIcons() ,0);
        iconPicker.setAdapter(iconadapter);
        ArrayAdapter<String> minutes = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,getContext().getResources().getStringArray(R.array.minutes));
        minutes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minutePicker.setAdapter(minutes);
        iconPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pickedIcon = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        minutePicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int positions, long l) {
                setminutes(positions);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                setminutes(0);

            }
        });
        return rootView;
    }

    private void setminutes(int position) {
        if (position==0){
            pickedMinute =0;
        }else if (position==1){
            pickedMinute =5;
        }else if (position==2){
            pickedMinute =10;
        }else if (position==3){
            pickedMinute =15;
        }else if (position==4){
            pickedMinute =20;
        }else if (position==5){
            pickedMinute =25;
        }else if (position==6){
            pickedMinute =30;
        }else if (position==7){
            //custom
            timepicker();
        }
    }

    private void timepicker() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                getActivity().onBackPressed();
                break;
        }
    }

}
