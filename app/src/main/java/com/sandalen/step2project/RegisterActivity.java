package com.sandalen.step2project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sandalen.step2project.bean.User;
import com.sandalen.step2project.biz.UserBiz;
import com.sandalen.step2project.holder.UserInfoHolder;
import com.sandalen.step2project.net.CommonCallBack;
import com.sandalen.step2project.utils.T;

public class RegisterActivity extends BaseActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtRepassword;
    private Button registerBtn;
    private UserBiz userBiz = new UserBiz();
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initEvent();

        setUpToolBar();

        setTitle("注册");
    }

    private void initEvent() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtRepassword.getText().toString();
                String repassword = edtRepassword.getText().toString();

                Log.e(TAG, "onClick: " + username + " : " + password );
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    T.showToast("用户名或密码不能为空");
                    return;
                }

                if(!password.equals(repassword)){
                    T.showToast("两次密码输入不一致");
                    return;
                }

                startLoadingProgress();
                userBiz.register(username, password, new CommonCallBack<User>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                        Log.e(TAG, "onError: " + e.getMessage() );
                    }

                    @Override
                    public void onSuccess(User response) {
                        stopLoadingProgress();
                        T.showToast("注册成功");
                        LoginActivity.launch(RegisterActivity.this,response.getUsername(),
                                response.getPassword());
                        finish();
                    }
                });
            }
        });
    }

    private void initView() {
        edtUsername = findViewById(R.id.id_et_username);
        edtPassword = findViewById(R.id.id_et_password);
        edtRepassword = findViewById(R.id.id_et_repassword);
        registerBtn = findViewById(R.id.id_btn_register);
    }


}