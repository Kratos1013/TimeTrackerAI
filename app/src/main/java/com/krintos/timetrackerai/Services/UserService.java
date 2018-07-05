package com.krintos.timetrackerai.Services;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.krintos.timetrackerai.Models.User;

/**
 * Created by root on 7/4/18.
 */

public class UserService {

    public UserService(){

    }

    public User getUser(){
        return (User) new Select().from(User.class).execute().get(0);
    }

    public void saveUser(String phone, String  token){
        User user  = new User();
        user.setPhoneNumber(phone);
        user.setToken(token);
        user.save();
    }
    public void updateUser(User userUpdated){
        User user = (User) new Select().from(User.class).execute().get(0);
        user.setUserName(userUpdated.getUserName());
        user.setName(userUpdated.getName());
        user.setPicName(userUpdated.getPicName());
        user.save();
    }

    public void deleteUser() {
        new Delete().from(User.class).execute();
    }
}
