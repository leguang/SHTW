package com.shtoone.shtw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shtoone.shtw.R;
import com.shtoone.shtw.bean.LaboratoryFragmentRecyclerViewItemData;
import com.shtoone.shtw.ui.ItemInItemView;

public class LaboratoryFragmentRecyclerViewAdapter extends RecyclerView.Adapter<LaboratoryFragmentRecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "LabAdapter";
    private Context context;
    private OnItemClickLitener mOnItemClickLitener;
    private LaboratoryFragmentRecyclerViewItemData itemData;

    public LaboratoryFragmentRecyclerViewAdapter(Context context, LaboratoryFragmentRecyclerViewItemData itemData) {
        super();
        this.context = context;
        this.itemData = itemData;
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public int getItemCount() {
        if (itemData != null) {
            return itemData.getData().size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        if (itemData != null && itemData.isSuccess() && itemData.getData().size() > 0) {

            holder.tv_organization.setText(itemData.getData().get(position).get(0).getDepartName());
            holder.tv_laboratory_count.setText(itemData.getData().get(position).get(0).getSysCount());     //拌合站总数
            holder.tv_machine_countll.setText(itemData.getData().get(position).get(0).getSyjCount());     //拌合机总数xx
            for (int i = 0; i < itemData.getData().get(position).size(); i++) {

                ItemInItemView itemInItem = new ItemInItemView(context);
                itemInItem.setTestType(itemData.getData().get(position).get(i).getTestName());
                itemInItem.setTestCount(itemData.getData().get(position).get(i).getTestCount());
                itemInItem.setDisqualificationCount(itemData.getData().get(position).get(i).getNotQualifiedCount());
                itemInItem.setDispositionCount(itemData.getData().get(position).get(i).getRealCount());
                if (TextUtils.isEmpty(itemData.getData().get(position).get(i).getRealPer())) {  //将""写在前头，这样，不管name是否为null，都不会出错。
                    itemInItem.setDispositionRate(itemData.getData().get(position).get(i).getRealPer() + "%");
                } else {
                    itemInItem.setDispositionRate("0.00%");
                }
                holder.ll_content.addView(itemInItem);
            }
        }

        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_laboratory_fragment, parent, false));
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_organization;
        TextView tv_laboratory_count;
        TextView tv_machine_countll;
        LinearLayout ll_content;

        public MyViewHolder(View view) {
            super(view);
            tv_organization = (TextView) view.findViewById(R.id.tv_organization_item_recyclerview_laboratory_fragment);
            tv_laboratory_count = (TextView) view.findViewById(R.id.tv_laboratory_count_item_recyclerview_laboratory_fragment);
            tv_machine_countll = (TextView) view.findViewById(R.id.tv_machine_countll_content_item_recyclerview_laboratory_fragment);
            ll_content = (LinearLayout) view.findViewById(R.id.ll_content_item_recyclerview_laboratory_fragment);
        }
    }
}
