package com.krintos.timetrackerai.Helper;

import android.content.Context;

import com.krintos.timetrackerai.R;

import java.util.ArrayList;
import java.util.List;

import in.goodiebag.carouselpicker.CarouselPicker;

/**
 * Created by root on 7/9/18.
 */

public class Helper {
    private List<CarouselPicker.PickerItem> images = new ArrayList<>();
    private Context context;
    public Helper(Context context){
        this.context = context;
    }
    public List<CarouselPicker.PickerItem> getIconsForCarousel(){
        for (int i = 0; i<3; i++){
            int id = context.getResources().getIdentifier("icon"+i,"drawable",context.getPackageName());
            images.add(new CarouselPicker.DrawableItem(id));
        }
        return images;
    }
    public String  getday(int position) {
        String day = null;
        if (position==0){
            day = context.getResources().getString(R.string.mon);
        }else if (position==1){
            day = context.getResources().getString(R.string.tue);
        }else if (position==2){
            day = context.getResources().getString(R.string.wed);
        }else if (position==3){
            day = context.getResources().getString(R.string.thu);
        }else if (position==4){
            day = context.getResources().getString(R.string.fri);
        }else if (position==5){
            day = context.getResources().getString(R.string.sat);
        }else {
            day = context.getResources().getString(R.string.sun);
        }
        return day;
    }
}
