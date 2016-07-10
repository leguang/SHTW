package com.shtoone.shtw.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shtoone.shtw.R;
import com.shtoone.shtw.bean.YalijiFragmentViewPagerFragmentRecyclerViewItemData;
import com.shtoone.shtw.ui.SlantedTextView;

public class YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter extends RecyclerView.Adapter<YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter.class.getSimpleName();
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private YalijiFragmentViewPagerFragmentRecyclerViewItemData itemsData;

    public enum ITEM_TYPE {
        KANGYAQIANGDU, GANGJINLALI, GANGJINHANJIE, GANGJINJIXIELIANJIE
    }

    public YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter(Context context, YalijiFragmentViewPagerFragmentRecyclerViewItemData itemsData) {
        super();
        this.context = context;
        this.itemsData = itemsData;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemCount() {
        if (itemsData != null && itemsData.isSuccess()) {
            return itemsData.getData().size();
        }
        return 0;
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

        YalijiFragmentViewPagerFragmentRecyclerViewItemData.DataBean item = itemsData.getData().get(position);
        holder.tv_title.setText(item.getSYRQ());
        holder.tv_test_number.setText(item.getSJBH());
        holder.tv_design_strength.setText(item.getSJQD());
        holder.tv_central_value.setText(item.getQDDBZ());
        holder.tv_project_name.setText(item.getGCMC());
        holder.tv_position.setText(item.getSGBW());
        holder.tv_testtype.setText(item.getTestName());
        if ("合格".equals(item.getPDJG())) {
            //默认就是合格设置
        } else if ("有效".equals(item.getPDJG())) {
            holder.stv_qualified.setText("有效");
        } else {
            //设置是否处置标签
            holder.stv_handle.setVisibility(View.VISIBLE);
            if (true) {
//                holder.stv_handle.setText("已处置").setSlantedBackgroundColor(Color.RED);
            } else {
                holder.stv_handle.setText("未处置").setSlantedBackgroundColor(Color.RED);
            }

            if ("不合格".equals(item.getPDJG())) {
                holder.stv_qualified.setText("不合格").setSlantedBackgroundColor(Color.RED);
            } else if ("无效".equals(item.getPDJG())) {
                holder.stv_qualified.setText("无效").setSlantedBackgroundColor(Color.RED);
            }
        }


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
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_yaliji_fragment_viewpager_fragment, parent, false));
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_test_number;
        TextView tv_design_strength;
        TextView tv_central_value;
        TextView tv_project_name;
        TextView tv_position;
        TextView tv_testtype;
        SlantedTextView stv_qualified;
        SlantedTextView stv_handle;
        CardView cv;

        public MyViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title_item_recyclerview_yaliji_fragment_viewpager_fragment);
            tv_test_number = (TextView) view.findViewById(R.id.tv_test_number_item_recyclerview_yaliji_fragment_viewpager_fragment);
            tv_design_strength = (TextView) view.findViewById(R.id.tv_design_strength_item_recyclerview_yaliji_fragment_viewpager_fragment);
            tv_central_value = (TextView) view.findViewById(R.id.tv_central_value_item_recyclerview_yaliji_fragment_viewpager_fragment);
            tv_project_name = (TextView) view.findViewById(R.id.tv_project_name_item_recyclerview_yaliji_fragment_viewpager_fragment);
            tv_position = (TextView) view.findViewById(R.id.tv_position_item_recyclerview_yaliji_fragment_viewpager_fragment);
            tv_testtype = (TextView) view.findViewById(R.id.tv_testtype_item_recyclerview_yaliji_fragment_viewpager_fragment);
            stv_qualified = (SlantedTextView) view.findViewById(R.id.stv_qualified_item_recyclerview_yaliji_fragment_viewpager_fragment);
            stv_handle = (SlantedTextView) view.findViewById(R.id.stv_handle_item_recyclerview_yaliji_fragment_viewpager_fragment);
            cv = (CardView) view.findViewById(R.id.cv_item_recyclerview_yaliji_fragment_viewpager_fragment);
        }
    }
}
