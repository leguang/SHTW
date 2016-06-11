package com.shtoone.shtw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtoone.shtw.R;

public class YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter extends RecyclerView.Adapter<YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter";
    private Context context;
    private OnItemClickListener mOnItemClickListener;
//    private LaboratoryFragmentRecyclerViewItemData itemData;

    public YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter(Context context) {
        super();
        this.context = context;
//        this.itemData = itemData;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemCount() {
//        if (itemData != null && itemData.isSuccess()) {
//            return itemData.getData().size();
//        }
        return 100;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.tv_title.setText("2016-06-01");
        holder.tv_equipment.setText("1标万能机");
        holder.tv_project.setText("石银高速XX工程");
        holder.tv_identifier.setText("YP-2016");
        holder.tv_position.setText("桩基");
        holder.tv_kind.setText("HRB4");
        holder.tv_testtype.setText("钢筋拉力");
//        holder.iv_qualified.setImageResource();

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
        TextView tv_equipment;
        TextView tv_project;
        TextView tv_identifier;
        TextView tv_position;
        TextView tv_kind;
        TextView tv_testtype;
        ImageView iv_qualified;

        public MyViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title_item_recyclerview_yaliji_fragment_viewpager_fragment);
            tv_equipment = (TextView) view.findViewById(R.id.tv_equipment_item_recyclerview_yaliji_fragment_viewpager_fragment);
            tv_project = (TextView) view.findViewById(R.id.tv_project_item_recyclerview_yaliji_fragment_viewpager_fragment);
            tv_identifier = (TextView) view.findViewById(R.id.tv_identifier_item_recyclerview_yaliji_fragment_viewpager_fragment);
            tv_position = (TextView) view.findViewById(R.id.tv_position_item_recyclerview_yaliji_fragment_viewpager_fragment);
            tv_kind = (TextView) view.findViewById(R.id.tv_kind_item_recyclerview_yaliji_fragment_viewpager_fragment);
            tv_testtype = (TextView) view.findViewById(R.id.tv_testtype_item_recyclerview_yaliji_fragment_viewpager_fragment);
            iv_qualified = (ImageView) view.findViewById(R.id.iv_qualified_item_recyclerview_yaliji_fragment_viewpager_fragment);
        }
    }
}
