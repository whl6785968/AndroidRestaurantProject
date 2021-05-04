package com.sandalen.step2project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sandalen.step2project.bean.Order;
import com.sandalen.step2project.bean.Product;
import com.sandalen.step2project.biz.OrderBiz;
import com.sandalen.step2project.biz.ProductBiz;
import com.sandalen.step2project.net.CommonCallBack;
import com.sandalen.step2project.ui.adapter.ProductListAdapter;
import com.sandalen.step2project.ui.view.refresh.SwipeRefresh;
import com.sandalen.step2project.ui.view.refresh.SwipeRefreshLayout;
import com.sandalen.step2project.ui.vo.ProductItem;
import com.sandalen.step2project.utils.T;

import java.util.ArrayList;
import java.util.List;

public class ProductListActitviy extends BaseActivity {
    private static final String TAG = "ProductListActitviy";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvCnt;
    private Button payBtn;
    private ProductBiz productBiz = new ProductBiz();
    private ProductListAdapter adapter;
    private List<ProductItem> productItems = new ArrayList<>();
    private int currentPage;
    private float totalPrice;
    private int totalCnt;
    private Order mOrder = new Order();
    private OrderBiz orderBiz = new OrderBiz();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_actitviy);

        setUpToolBar();
        setTitle("点餐");
        loadData();
        initView();
        initEvent();
    }

    private void initEvent() {
        swipeRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                loadMore();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalCnt == 0){
                    T.showToast("你还没选商品呢~~~");
                    return;
                }

                mOrder.setCount(totalCnt);
                mOrder.setPrice(totalPrice);
                mOrder.setRestaurant(productItems.get(0).getRestaurant());

                startLoadingProgress();
                orderBiz.add(mOrder, new CommonCallBack<String>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
//                        toLoginActivity();
                    }

                    @Override
                    public void onSuccess(String response) {
                        stopLoadingProgress();
                        T.showToast("订单生成成功");
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }
        });

        adapter.setOnProductListener(new ProductListAdapter.OnProductListener() {
            @Override
            public void onProductAdd(ProductItem productItem) {
                totalCnt++;
                tvCnt.setText("数量："+totalCnt);
                totalPrice += productItem.getPrice();
                mOrder.addProduct(productItem);
                payBtn.setText(totalPrice + "元 立即支付");
            }

            @Override
            public void onProductSub(ProductItem productItem) {
                totalCnt--;
                tvCnt.setText("数量："+totalCnt);
                totalPrice -= productItem.getPrice();
                mOrder.removeProduct(productItem);
                if(totalCnt == 0) totalPrice = 0;
                payBtn.setText(totalPrice + "元 立即支付");
            }
        });

        adapter.setOnProductClickListener(new ProductListAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(ProductItem productItem) {
                ProductDetailActivity.launch(ProductListActitviy.this,productItem);
            }
        });
    }

    private void loadData() {
        startLoadingProgress();
        productBiz.listByPage(0, new CommonCallBack<List<Product>>() {
            @Override
            public void onError(Exception e) {
                Log.e(TAG, "onError: ");
                stopLoadingProgress();
                T.showToast(e.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onSuccess(List<Product> response) {
                Log.e(TAG, "onSuccess: " );
                stopLoadingProgress();
                swipeRefreshLayout.setRefreshing(false);
                currentPage = 0;
                productItems.clear();
                Log.e(TAG, "onSuccess: " + response.size() );
                for(Product p : response){
                    productItems.add(new ProductItem(p));
                }

                adapter.notifyDataSetChanged();
                totalCnt = 0;
                totalPrice = 0;
            }
        });
    }

    private void loadMore() {

    }

    private void initView() {
        recyclerView = findViewById(R.id.id_recycer_view);
        swipeRefreshLayout = findViewById(R.id.swiper_refresh);
        tvCnt = findViewById(R.id.id_tv_text);
        payBtn = findViewById(R.id.id_btn_order);

        //设置开关
        swipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        //设置颜色
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLACK, Color.BLUE, Color.GRAY);

        adapter = new ProductListAdapter(this,productItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        productBiz.onDestroy();
    }
}