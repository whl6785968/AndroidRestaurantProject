package com.sandalen.step2project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandalen.step2project.bean.Order;
import com.sandalen.step2project.config.Config;
import com.sandalen.step2project.ui.vo.ProductItem;
import com.sandalen.step2project.utils.T;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDetailActivity extends BaseActivity {

    public static final String KEY_PRODUCT = "key_product";
    private ImageView imageView;
    private TextView title;
    private TextView desc;
    private TextView price;

    Order mOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        setUpToolBar();
        setTitle("订单详情");

        initView();
    }

    public static void launch(Context context, Order order){
        Intent intent = new Intent(context,OrderDetailActivity.class);
        intent.putExtra(KEY_PRODUCT,order);
        context.startActivity(intent);
    }

    private void initView() {
        imageView = findViewById(R.id.id_iv_image);
        title = findViewById(R.id.id_tv_title);
        desc = findViewById(R.id.id_tv_desc);
        price = findViewById(R.id.id_tv_price);

        Intent intent = getIntent();
        mOrder = (Order) intent.getSerializableExtra(KEY_PRODUCT);

        if(mOrder != null){
            List<Order.ProductVo> ps = mOrder.getPs();

            Picasso.get().load(Config.baseUrl + mOrder.getRestaurant().getIcon())
                    .placeholder(R.drawable.pictures_no).into(imageView);
            title.setText(mOrder.getRestaurant().getName());
            StringBuilder sb = new StringBuilder();
            for(Order.ProductVo p : ps){
                sb.append(p.product.getName())
                        .append(" * ")
                        .append(p.count)
                        .append("\n");
            }
            desc.setText(sb.toString());
            price.setText("共消费" + mOrder.getPrice() + "元");
        }
        else {
            T.showToast("该产品详细内容不存在");
            return;
        }
    }
}