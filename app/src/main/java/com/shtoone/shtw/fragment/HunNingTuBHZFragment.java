package com.shtoone.shtw.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.MainActivity;
import com.shtoone.shtw.fragment.base.BaseFragment;

/**
 * Created by leguang on 2016/5/31 0031.
 */
public class HunNingTuBHZFragment extends BaseFragment {

    public static HunNingTuBHZFragment newInstance() {
        return new HunNingTuBHZFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hunningtubhz, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar_hunningtu_fragment);
        mToolbar.setTitle("XX高速 > 混凝土拌合站");
        ((MainActivity) _mActivity).initToolBar(mToolbar);
    }

}
