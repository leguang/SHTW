package com.shtoone.shtw.fragment.laboratoryactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.shtw.R;
import com.shtoone.shtw.fragment.base.BaseFragment;

/**
 * Created by leguang on 2016/6/9 0031.
 */
public class ViewPagerFragment extends BaseFragment {

    public static ViewPagerFragment newInstance() {
        return new ViewPagerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
    }

}
