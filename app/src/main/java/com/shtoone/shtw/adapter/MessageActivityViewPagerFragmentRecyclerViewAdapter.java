package com.shtoone.shtw.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shtoone.shtw.R;

public class MessageActivityViewPagerFragmentRecyclerViewAdapter extends RecyclerView.Adapter<MessageActivityViewPagerFragmentRecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "MessageActivityViewPagerFragmentRecyclerViewAdapter";
    private Context context;
    private OnItemClickListener mOnItemClickListener;

//    public enum ITEM_TYPE {
//        KANGYAQIANGDU, GANGJINLALI, GANGJINHANJIE, GANGJINJIXIELIANJIE
//    }

    public MessageActivityViewPagerFragmentRecyclerViewAdapter(Context context) {
        super();
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemCount() {
//        if (itemsData != null && itemsData.isSuccess()) {
//            return itemsData.getData().size();
//        }
        return 100;
    }

    @Override
    public int getItemViewType(int position) {
        //需要返回试验类型编码给我，如：100047，100048等
//        YalijiFragmentViewPagerFragmentRecyclerViewItemData.DataBean item = itemsData.getData().get(position);

//        switch (item.getT)
        return 0;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Resources mResources = context.getResources();
        holder.cv.setCardBackgroundColor(position % 2 == 0 ? mResources.getColor(R.color.material_teal_50) : mResources.getColor(R.color.material_blue_50));

        holder.tv_title.setText("消息：拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！拌合站出事啦！" + position);

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_message_activity_viewpager_fragment, parent, false));
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        CardView cv;

        public MyViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title_item_recyclerview_message_activity_viewpager_fragment);
            cv = (CardView) view.findViewById(R.id.cv_item_recyclerview_message_activity_viewpager_fragment);
        }
    }
}
