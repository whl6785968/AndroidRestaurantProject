package com.sandalen.step2project.net;

import okhttp3.Call;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class CommonCallBack <T> extends StringCallback{

    private Type mType;

    public CommonCallBack(){
        Class<? extends CommonCallBack> clazz = getClass();

        //拿到的是参数化类型，即有可能是CommonCallBack<T>或者T
        Type genericSuperclass = clazz.getGenericSuperclass();

        if(genericSuperclass instanceof Class){
            throw new RuntimeException("miss type");
        }

        //参数化类型
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        mType = parameterizedType.getActualTypeArguments()[0];
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        onError(e);
    }

    @Override
    public void onResponse(String response, int id) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int resultCode = jsonObject.getInt("resultCode");

            if(resultCode == 1){
                String data = jsonObject.getString("data");
                Gson gson = new Gson();
                onSuccess((T)gson.fromJson(data,mType));
            }
            else{
                onError(new RuntimeException(jsonObject.getString("resultMessage")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public abstract void onError(Exception e);
    public abstract void onSuccess(T response);
}
