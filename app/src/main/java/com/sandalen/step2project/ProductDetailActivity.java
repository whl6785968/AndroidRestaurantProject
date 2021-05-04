package com.sandalen.step2project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandalen.step2project.bean.Product;
import com.sandalen.step2project.config.Config;
import com.sandalen.step2project.ui.vo.ProductItem;
import com.sandalen.step2project.utils.T;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends BaseActivity {

    public static final String KEY_PRODUCT = "key_product";
    private ImageView imageView;
    private TextView title;
    private TextView desc;
    private TextView price;

    ProductItem mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        setUpToolBar();
        setTitle("产品详情");

        initView();
    }

    public static void launch(Context context, ProductItem product){
        Intent intent = new Intent(context,ProductDetailActivity.class);
        intent.putExtra(KEY_PRODUCT,product);
        context.startActivity(intent);
    }

    private void initView() {
        imageView = findViewById(R.id.id_iv_image);
        title = findViewById(R.id.id_tv_title);
        desc = findViewById(R.id.id_tv_desc);
        price = findViewById(R.id.id_tv_price);

        Intent intent = getIntent();
        mProduct = (ProductItem) intent.getSerializableExtra(KEY_PRODUCT);

        if(mProduct != null){
            Picasso.get().load(Config.baseUrl + mProduct.getIcon())
                    .placeholder(R.drawable.pictures_no).into(imageView);
            title.setText(mProduct.getName());
            desc.setText(mProduct.getDescription());
            price.setText(mProduct.getPrice() + "元/份");
        }
        else {
            T.showToast("该产品详细内容不存在");
            return;
        }
    }
}