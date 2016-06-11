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
public class DisqualificationFragment extends BaseFragment {

    public static DisqualificationFragment newInstance() {
        return new DisqualificationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disqualification, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar_disqualification_fragment);
        mToolbar.setTitle("XX高速 > 试验室 > 不合格");
        initToolbarBackNavigation(mToolbar);
        initToolbarMenu(mToolbar);
    }
}
