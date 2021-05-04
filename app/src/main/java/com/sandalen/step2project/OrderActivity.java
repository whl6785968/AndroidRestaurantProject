package com.sandalen.step2project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandalen.step2project.bean.Order;
import com.sandalen.step2project.bean.User;
import com.sandalen.step2project.biz.OrderBiz;
import com.sandalen.step2project.holder.UserInfoHolder;
import com.sandalen.step2project.net.CommonCallBack;
import com.sandalen.step2project.ui.adapter.OrderAdapter;
import com.sandalen.step2project.ui.view.CircleTransform;
import com.sandalen.step2project.ui.view.refresh.SwipeRefresh;
import com.sandalen.step2project.ui.view.refresh.SwipeRefreshLayout;
import com.sandalen.step2project.utils.T;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends BaseActivity {
    private static final String TAG = "OrderActivity";

    private Button btnTakeOrder;
    private ImageView ivIcon;
    private TextView mTvName;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private OrderBiz orderBiz = new OrderBiz();
    private List<Order> mDatas = new ArrayList<>();
    private int mCurrentPage;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initView();
        initEvent();
    }

    private void initEvent() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        mRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                loadMore();
            }
        });

        btnTakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(OrderActivity.this,ProductListActitviy.class),1001);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1001 && resultCode == RESULT_OK){
            loadData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLoadingProgress();
        loadData();
    }

    private void initView() {
        btnTakeOrder = findViewById(R.id.id_btn_take_order);
        ivIcon = findViewById(R.id.id_iv_icon);
        mTvName = findViewById(R.id.id_tv_name);
        mRecyclerView = findViewById(R.id.id_recycer_view);
        mRefreshLayout = findViewById(R.id.id_refresh_layout);
        orderAdapter = new OrderAdapter(this,mDatas);

        User user = UserInfoHolder.getInstance().getUser();
        if (user != null){
            mTvName.setText(user.getUsername());
        }

        //设置开关
        mRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        //设置颜色
        mRefreshLayout.setColorSchemeColors(Color.RED, Color.BLACK, Color.BLUE, Color.GRAY);
        //设置事件

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(orderAdapter);

        //将头像变为圆形
        Picasso.get().load(R.drawable.icon).placeholder(R.drawable.pictures_no)
                .transform(new CircleTransform()).into(ivIcon);

    }

    private void loadMore() {
        Log.e(TAG, "loadMore: " );
        orderBiz.listByPage(++mCurrentPage, new CommonCallBack<List<Order>>() {
            @Override
            public void onError(Exception e) {
                Log.e(TAG, "onError: " );
                stopLoadingProgress();
                T.showToast(e.getMessage());
                mRefreshLayout.setPullUpRefreshing(false);
                mCurrentPage--;

                String message = e.getMessage();
                if (message.contains("用户未登录")) {
                    toLoginActivity();
                }
            }

            @Override
            public void onSuccess(List<Order> response) {
                Log.e(TAG, "onSuccess: " );
                stopLoadingProgress();
                if (response.size() == 0) {
                    T.showToast("木有历史订单啦~~~");
                    mRefreshLayout.setPullUpRefreshing(false);
                    return;
                }
                T.showToast("更新订单成功~~~");
                Log.e(TAG, "onSuccess: " + response.get(0).toString() );
                mDatas.addAll(response);
                orderAdapter.notifyDataSetChanged();
                mRefreshLayout.setPullUpRefreshing(false);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                return true;
            }
            catch (Exception e){

            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void loadData() {
        orderBiz.listByPage(0, new CommonCallBack<List<Order>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
                if (e.getMessage().contains("用户未登录")) {
                    toLoginActivity();
                }
            }

            @Override
            public void onSuccess(List<Order> response) {
                stopLoadingProgress();
                T.showToast("更新订单成功~~~");
                mDatas.clear();
                mDatas.addAll(response);
                orderAdapter.notifyDataSetChanged();
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
}