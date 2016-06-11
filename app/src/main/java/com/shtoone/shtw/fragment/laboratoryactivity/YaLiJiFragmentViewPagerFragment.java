package com.shtoone.shtw.fragment.laboratoryactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.shtw.R;
import com.shtoone.shtw.adapter.OnItemClickListener;
import com.shtoone.shtw.adapter.YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter;
import com.shtoone.shtw.fragment.base.BaseFragment;
import com.shtoone.shtw.utils.ToastUtils;

import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by leguang on 2016/6/9 0031.
 */
public class YaLiJiFragmentViewPagerFragment extends BaseFragment {

    private static final String TAG = "YaLiJiFragmentViewPagerFragment";
    private RecyclerView mRecyclerView;
    private YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter mAdapter;

    public static YaLiJiFragmentViewPagerFragment newInstance() {
        return new YaLiJiFragmentViewPagerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_view_pager_fragment);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mAdapter = new YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter(_mActivity));
        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showToast(_mActivity, "第：" + position);
            }
        });

    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimation() {
        return new DefaultNoAnimator();
    }

}
