package com.krintos.timetrackerai.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by root on 7/8/18.
 */
@Table(name = "weeks")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weeks extends Model {
    @Column(name = "days")
    private String days;
    @Column(name = "task")
    private String task;
    @Column(name = "stime")
    private String sTime;
    @Column(name = "ftime")
    private String fTime;
    @Column(name = "icon")
    private String icon;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String getfTime() {
        return fTime;
    }

    public void setfTime(String fTime) {
        this.fTime = fTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
