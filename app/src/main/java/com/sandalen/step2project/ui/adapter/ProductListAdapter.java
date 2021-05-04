package com.sandalen.step2project.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sandalen.step2project.R;
import com.sandalen.step2project.bean.Order;
import com.sandalen.step2project.bean.Product;
import com.sandalen.step2project.config.Config;
import com.sandalen.step2project.ui.vo.ProductItem;
import com.sandalen.step2project.utils.T;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zhanghongyang01 on 16/10/18.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.OrderItemViewHolder> {


    private Context mContext;
    private LayoutInflater mInflater;
    private List<ProductItem> mDatas;

    public ProductListAdapter(Context context, List<ProductItem> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }


    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_product, parent, false);
        return new OrderItemViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(OrderItemViewHolder holder, int position) {
        ProductItem productItem = mDatas.get(position);

        Picasso.get().load(Config.baseUrl + productItem.getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(holder.mIvImage);

        holder.mTvName.setText(productItem.getName());
        holder.cnt.setText(productItem.count + "");
        holder.mTvPrice.setText(productItem.getPrice() + "元/份");
        holder.mTvLabel.setText(productItem.getLabel());

    }

    public interface OnProductListener{
        void onProductAdd(ProductItem productItem);
        void onProductSub(ProductItem productItem);
    }

    private OnProductListener onProductListener;

    public void setOnProductListener(OnProductListener onProductListener) {
        this.onProductListener = onProductListener;
    }

    public interface OnProductClickListener {
        void onProductClick(ProductItem productItem);
    }

    private OnProductClickListener onProductClickListener;

    public void setOnProductClickListener(OnProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView mIvImage;
        public TextView mTvName;
        public TextView mTvLabel;
        public TextView mTvPrice;

        public ImageView add;
        public ImageView sub;
        public TextView cnt;

        public OrderItemViewHolder(View itemView) {
            super(itemView);

            mIvImage = itemView.findViewById(R.id.id_iv_image);
            mTvName = itemView.findViewById(R.id.id_tv_name);
            mTvLabel = itemView.findViewById(R.id.id_tv_label);
            mTvPrice= itemView.findViewById(R.id.id_tv_price);
            add = itemView.findViewById(R.id.id_iv_add);
            sub = itemView.findViewById(R.id.id_iv_sub);
            cnt = itemView.findViewById(R.id.id_iv_cnt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    ProductItem productItem = mDatas.get(position);
                    onProductClickListener.onProductClick(productItem);
                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    ProductItem productItem = mDatas.get(position);
                    productItem.count += 1;
                    cnt.setText(productItem.count + "");
                    onProductListener.onProductAdd(productItem);
                }
            });

            sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    ProductItem productItem = mDatas.get(position);
                    if(productItem.count <= 0){
                        T.showToast("神经病");
                        return;
                    }
                    productItem.count -= 1;
                    cnt.setText(productItem.count + "");
                    onProductListener.onProductSub(productItem);
                }
            });
        }


    }

}
