package com.shtoone.shtw.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtoone.shtw.R;
import com.shtoone.shtw.bean.WannengjiFragmentViewPagerFragmentRecyclerViewItemData;

public class WannengjiFragmentViewPagerFragmentRecyclerViewAdapter extends RecyclerView.Adapter<WannengjiFragmentViewPagerFragmentRecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "WannengjiFragmentViewPagerFragmentRecyclerViewAdapter";
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private WannengjiFragmentViewPagerFragmentRecyclerViewItemData itemsData;

//    public enum ITEM_TYPE {
//        KANGYAQIANGDU, GANGJINLALI, GANGJINHANJIE, GANGJINJIXIELIANJIE
//    }

    public WannengjiFragmentViewPagerFragmentRecyclerViewAdapter(Context context, WannengjiFragmentViewPagerFragmentRecyclerViewItemData itemsData) {
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

        WannengjiFragmentViewPagerFragmentRecyclerViewItemData.DataBean item = itemsData.getData().get(position);
        holder.tv_title.setText(item.getSYRQ());
        holder.tv_equipment.setText(item.getShebeiname());
        holder.tv_project_name.setText(item.getGCMC());
        holder.tv_identifier.setText(item.getSJBH());
        holder.tv_position.setText(item.getSGBW());
        holder.tv_kind.setText(item.getPZBM());
        holder.tv_testtype.setText(item.getTestName());
        if (!("合格".equals(item.getPDJG()) || "有效".equals(item.getPDJG()))) {
            holder.iv_qualified.setImageResource(R.drawable.ic_logo);
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
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_wannengji_fragment_viewpager_fragment, parent, false));
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_equipment;
        TextView tv_project_name;
        TextView tv_identifier;
        TextView tv_position;
        TextView tv_kind;
        TextView tv_testtype;
        ImageView iv_qualified;
        CardView cv;

        public MyViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title_item_recyclerview_wannengji_fragment_viewpager_fragment);
            tv_equipment = (TextView) view.findViewById(R.id.tv_equipment_item_recyclerview_wannengji_fragment_viewpager_fragment);
            tv_project_name = (TextView) view.findViewById(R.id.tv_project_name_item_recyclerview_wannengji_fragment_viewpager_fragment);
            tv_identifier = (TextView) view.findViewById(R.id.tv_identifier_item_recyclerview_wannengji_fragment_viewpager_fragment);
            tv_position = (TextView) view.findViewById(R.id.tv_position_item_recyclerview_wannengji_fragment_viewpager_fragment);
            tv_kind = (TextView) view.findViewById(R.id.tv_kind_item_recyclerview_wannengji_fragment_viewpager_fragment);
            tv_testtype = (TextView) view.findViewById(R.id.tv_testtype_item_recyclerview_wannengji_fragment_viewpager_fragment);
            iv_qualified = (ImageView) view.findViewById(R.id.iv_qualified_item_recyclerview_wannengji_fragment_viewpager_fragment);
            cv = (CardView) view.findViewById(R.id.cv_item_recyclerview_wannengji_fragment_viewpager_fragment);
        }
    }
}
