package com.sandalen.step2project.biz;

import com.sandalen.step2project.bean.User;
import com.sandalen.step2project.config.Config;
import com.sandalen.step2project.net.CommonCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

public class UserBiz {

    public void login(String username, String password,
                      CommonCallBack<User> commonCallBack){
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "user_login")
                .tag(this)
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(commonCallBack);
    }

    public void register(String username, String password,
                         CommonCallBack<User> commonCallBack){
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "user_register")
                .tag(this)
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(commonCallBack);
    }
}
