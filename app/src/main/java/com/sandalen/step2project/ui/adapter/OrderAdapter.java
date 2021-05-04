package com.sandalen.step2project.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sandalen.step2project.OrderDetailActivity;
import com.sandalen.step2project.R;
import com.sandalen.step2project.bean.Order;
import com.sandalen.step2project.config.Config;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zhanghongyang01 on 16/10/18.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderItemViewHolder> {


    private Context mContext;
    private LayoutInflater mInflater;
    private List<Order> mDatas;

    public OrderAdapter(Context context, List<Order> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }


    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_order, parent, false);
        return new OrderItemViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(OrderItemViewHolder holder, int position) {

        Order order = mDatas.get(position);

        Picasso.get()
                .load(Config.baseUrl + order.getRestaurant().getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(holder.mIvImage);

        holder.mTvName.setText(order.getRestaurant().getName());
        if (order.ps.size() > 0){
            holder.mTvLabel.setText(order.ps.get(0).product.getName() + "等" + order.getCount() + "件商品");
        }
        else {
            holder.mTvLabel.setText("无消费");
        }

        holder.mTvPrice.setText("共消费：" + order.getPrice() + "元");

    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView mIvImage;
        public TextView mTvName;
        public TextView mTvLabel;
        public TextView mTvPrice;

        public OrderItemViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderDetailActivity.launch(mContext, mDatas.get(getLayoutPosition()));
                }
            });

            mIvImage = (ImageView) itemView.findViewById(R.id.id_iv_image);
            mTvName = (TextView) itemView.findViewById(R.id.id_tv_name);
            mTvLabel = (TextView) itemView.findViewById(R.id.id_tv_label);
            mTvPrice = (TextView) itemView.findViewById(R.id.id_tv_price);
        }


    }

}
