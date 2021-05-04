package com.sandalen.step2project.holder;

import android.text.TextUtils;

import com.sandalen.step2project.bean.User;
import com.sandalen.step2project.utils.SPUtils;

public class UserInfoHolder {

    private static UserInfoHolder userInfoHolder = new UserInfoHolder();
    private User mUser;

    private UserInfoHolder(){

    }

    private static final String KEY_USERNAME = "key_username";

    public static UserInfoHolder getInstance(){
        return userInfoHolder;
    }

    public void setUser(User user){
        mUser = user;
        if(mUser != null){
            SPUtils.getInstance().put(KEY_USERNAME,user.getUsername());
        }
    }

    public User getUser(){
        User u = mUser;
        if(u == null){
            String username = (String) SPUtils.getInstance().get(KEY_USERNAME,"");
            if(!TextUtils.isEmpty(username)){
                u = new User();
                u.setUsername(username);
            }
        }

        mUser = u;
        return u;
    }
}
