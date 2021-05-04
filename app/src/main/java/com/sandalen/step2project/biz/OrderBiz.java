package com.sandalen.step2project.biz;

import com.sandalen.step2project.bean.Order;
import com.sandalen.step2project.bean.Product;
import com.sandalen.step2project.config.Config;
import com.sandalen.step2project.net.CommonCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderBiz {
    public void listByPage(int currentPage, CommonCallBack<List<Order>> commonCallBack){
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "order_find")
                .tag(this)
                .addParams("currentPage",currentPage + "")
                .build()
                .execute(commonCallBack);
    }

    public void add(Order order,CommonCallBack callBack){
        Map<Product,Integer> map = order.productsMap;
        StringBuilder sb = new StringBuilder();
        for(Product p : map.keySet()){
            sb.append(p.getId() + "_" + map.get(p));
            sb.append("|");
        }

        sb = sb.deleteCharAt(sb.length() - 1);

        OkHttpUtils
                .post()
                .url(Config.baseUrl + "order_add")
                .addParams("res_id", order.getRestaurant().getId() + "")
                .addParams("product_str", sb.toString())
                .addParams("count", order.getCount() + "")
                .addParams("price", order.getPrice() + "")
                .tag(this)
                .build()
                .execute(callBack);
    }
}
