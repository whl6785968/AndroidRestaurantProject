package com.sandalen.step2project.biz;

import com.sandalen.step2project.bean.Product;
import com.sandalen.step2project.config.Config;
import com.sandalen.step2project.net.CommonCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

public class ProductBiz {
    public void listByPage(int currentPage, CommonCallBack<List<Product>> commonCallBack){
        OkHttpUtils.post()
                .url(Config.baseUrl + "product_find")
                .addParams("currentPage",currentPage + "")
                .tag(this)
                .build()
                .execute(commonCallBack);
    }

    public void onDestroy(){
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
