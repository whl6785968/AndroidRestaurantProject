package com.sandalen.step2project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sandalen.step2project.bean.User;
import com.sandalen.step2project.biz.UserBiz;
import com.sandalen.step2project.holder.UserInfoHolder;
import com.sandalen.step2project.net.CommonCallBack;
import com.sandalen.step2project.utils.T;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;

import okhttp3.CookieJar;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    public static final String KEY_USERNAME = "key_username";
    public static final String KEY_PASSWORD = "key_password";

    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private Button mLoginBtn;
    private TextView mRegisterBtn;
    private UserBiz userBiz = new UserBiz();

    @Override
    protected void onResume() {
        super.onResume();
        CookieJarImpl cookieJar = (CookieJarImpl) OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        cookieJar.getCookieStore().removeAll();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initIntent(intent);
    }

    //在此写该方法的原因是怕key不一致？
    public static void launch(Context context, String username, String password) {
        Intent intent = new Intent(context,LoginActivity.class);
        //防止两次back才能退出
        //设置LoginActivity的启动模式为Single_TOP
//        如果ActivityA在栈顶,且现在要再启动ActivityA，这时会调用onNewIntent()方法 ，生命周期顺序为：
//        onCreate--->onStart--->onResume---onPause--->onNewIntent--->onResume
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(KEY_USERNAME,username);
        intent.putExtra(KEY_PASSWORD,password);
        context.startActivity(intent);
    }

    private void initIntent(Intent intent){
        if(intent != null){
            String username = intent.getStringExtra(KEY_USERNAME);
            String password = intent.getStringExtra(KEY_PASSWORD);

            if(username != null){
                mEdtUsername.setText(username);
            }

            if(password != null){
                mEdtPassword.setText(password);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();

        initIntent(getIntent());
    }

    private void initEvent() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEdtUsername.getText().toString();
                String password = mEdtPassword.getText().toString();
                Log.e(TAG, "onClick: " + username + " : " + password );
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    T.showToast("用户名或密码不能为空");
                    return;
                }

                startLoadingProgress();
                userBiz.login(username, password, new CommonCallBack<User>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                        Log.e(TAG, "onError: " + e.getMessage() );
                    }

                    @Override
                    public void onSuccess(User response) {
                        stopLoadingProgress();
                        T.showToast("登录成功");
                        UserInfoHolder.getInstance().setUser(response);
                        Intent intent = new Intent(LoginActivity.this, OrderActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });


            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mEdtUsername = findViewById(R.id.id_et_username);
        mEdtPassword = findViewById(R.id.id_et_password);
        mLoginBtn = findViewById(R.id.login_btn);
        mRegisterBtn = findViewById(R.id.register_btn);
    }
}