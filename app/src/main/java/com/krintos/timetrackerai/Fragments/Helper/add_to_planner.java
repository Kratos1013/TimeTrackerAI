package com.krintos.timetrackerai.Fragments.Helper;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.krintos.timetrackerai.Helper.Helper;
import com.krintos.timetrackerai.R;
import com.krintos.timetrackerai.Services.PlannerService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.goodiebag.carouselpicker.CarouselPicker;

public class add_to_planner extends Fragment implements View.OnClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private TextView day,stime,ftime,customtime;
    private EditText taskname;
    private Spinner minutePicker;
    private int pickedIcon=0,pickedMinute,notifyornot=0;
    private Button cancel, save;
    private CarouselPicker iconPicker;
    private Helper helper;
    private Switch notifier;
    private LinearLayout notification, setstime,setftime;
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
        stime.setOnClickListener(this);
        ftime = rootView.findViewById(R.id.ftime);
        ftime.setOnClickListener(this);
        customtime = rootView.findViewById(R.id.customtime);
        taskname = rootView.findViewById(R.id.taskname);
        notifier = rootView.findViewById(R.id.notifier);
        cancel = rootView.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        save = rootView.findViewById(R.id.save);
        save.setOnClickListener(this);
        notification = rootView.findViewById(R.id.notification);
        setstime = rootView.findViewById(R.id.setstime);
        setstime.setOnClickListener(this);
        setftime = rootView.findViewById(R.id.setftime);
        setftime.setOnClickListener(this);
        day.setText(helper.getday(position));
        iconPicker =  rootView.findViewById(R.id.iconpicker);
        CarouselPicker.CarouselViewAdapter iconadapter = new CarouselPicker.CarouselViewAdapter(getActivity(), helper.getIconsForCarousel() ,0);
        iconPicker.setAdapter(iconadapter);
        ArrayAdapter<String> minutes = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,getContext().getResources().getStringArray(R.array.minutes));
        minutes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minutePicker.setAdapter(minutes);
        generatelayout();
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
        notifier.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notificationshow();
                } else {
                    notificationshow();
                }
            }
        });
        minutePicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int positions, long l) {
                setminutes(positions);
                customtime.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                setminutes(0);
                customtime.setVisibility(View.GONE);
            }
        });
        return rootView;
    }

    private void generatelayout() {
        stime.setText("00 : 00");
        ftime.setText("00 : 00");
    }

    private void notificationshow() {
        if (notification.getVisibility()==View.VISIBLE){
            notification.setVisibility(View.GONE);
            notifyornot=0;
        }else {
            notification.setVisibility(View.VISIBLE);
            notifyornot=1;
        }
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
            timepicker(2);
        }
    }

    private void timepicker(int type) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker = null;
        if (type==0){
            mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String formattedHour = null;
                    String formattedMinutes = null;
                    if (selectedHour < 10) {
                        formattedHour = "0" + selectedHour;
                    }

                    if (selectedMinute < 10) {
                        formattedMinutes = "0" + selectedMinute;
                    }
                    stime.setText( formattedHour + ":" + formattedMinutes);
                }
            }, hour, minute, true);//Yes 24 hour time
        }else if (type==1){
            mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String formattedHour = null;
                    String formattedMinutes = null;
                    if (selectedHour < 10) {
                        formattedHour = "0" + selectedHour;
                    }

                    if (selectedMinute < 10) {
                        formattedMinutes = "0" + selectedMinute;
                    }
                    ftime.setText( formattedHour + ":" + formattedMinutes);
                }
            }, hour, minute, true);//Yes 24 hour time
        }else{
            mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    customtime.setVisibility(View.VISIBLE);String formattedHour = null;
                    String formattedMinutes = null;
                    if (selectedHour < 10) {
                        formattedHour = "0" + selectedHour;
                    }

                    if (selectedMinute < 10) {
                        formattedMinutes = "0" + selectedMinute;
                    }
                    customtime.setText( formattedHour + ":" + formattedMinutes);
                }
            },hour,minute,true);
        }
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                getActivity().onBackPressed();
                break;
            case R.id.setstime:
                timepicker(0);
                break;
            case R.id.setftime:
                timepicker(1);
                break;
            case R.id.save:
                savedatas();
                break;
        }
    }

    private void savedatas() {
        String getDay = day.getText().toString().trim();
        String getIcon = "icon"+pickedIcon;
        String getTask = taskname.getText().toString();
        String getStime = stime.getText().toString().trim();
        String getFtime = ftime.getText().toString().trim();
        if (!getTask.equals("")){
            String getNotification="no";
            String getntime = "no";
            if (notifyornot != 0){
                getNotification = "yes";
                if (customtime.getVisibility()==View.VISIBLE){
                    getntime = customtime.getText().toString().trim();
                }else {
                    getntime = ""+pickedMinute;
                }
            }else {
                getNotification = "no";
            }
            PlannerService plan = new PlannerService();
            plan.save(getDay,getTask,getStime,getFtime,getIcon,getNotification,getntime);
            getActivity().onBackPressed();
        }else {
            Toast.makeText(getActivity(), ""+getString(R.string.emptytask), Toast.LENGTH_SHORT).show();
        }
    }

}
