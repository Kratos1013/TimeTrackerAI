package com.krintos.timetrackerai.Services;


import com.activeandroid.query.Select;
import com.krintos.timetrackerai.Models.Weeks;

import java.util.List;

/**
 * Created by root on 7/8/18.
 */

public class PlannerService {

    public List<Weeks> getByDay(String day){
        return new Select()
                .from(Weeks.class)
                .where("days = ?", day)
                .execute();
    }

    public void save(String day,String task,String stime ,String ftime,String icon,String notification,
                     String ntime){
        Weeks weeks = new Weeks();
        weeks.setDays(day);
        weeks.setTask(task);
        weeks.setsTime(stime);
        weeks.setfTime(ftime);
        weeks.setIcon(icon);
        weeks.setNotification(notification);
        weeks.setNtime(ntime);
        weeks.save();
    }
    public void update(String day,String task,String stime ,String ftime,String icon,String notification,
                       String ntime,int id){

    }
    public void deleter(int id){

    }

}
