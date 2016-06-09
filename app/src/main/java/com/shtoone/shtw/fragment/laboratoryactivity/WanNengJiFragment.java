package com.shtoone.shtw.fragment.laboratoryactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.shtw.R;
import com.shtoone.shtw.fragment.base.BaseFragment;

/**
 * Created by leguang on 2016/6/9 0031.
 */
public class WanNengJiFragment extends BaseFragment {

    public static WanNengJiFragment newInstance() {
        return new WanNengJiFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wannengji, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar_wannengji_fragment);
        mToolbar.setTitle("XX高速 > 试验室 > 万能机");
    }
}
