package com.shtoone.shtw.fragment.mainactivity;

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
public class AsphaltFragment extends BaseFragment {
    private static final String TAG = AsphaltFragment.class.getSimpleName();

    public static AsphaltFragment newInstance() {
        return new AsphaltFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asphalt, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar_asphalt_fragment);
        mToolbar.setTitle("XX高速 > 沥青拌合站");
        ((MainActivity) _mActivity).initToolBar(mToolbar);
        initToolbarMenu(mToolbar);
    }
}
