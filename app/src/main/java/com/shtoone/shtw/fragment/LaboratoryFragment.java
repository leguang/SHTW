package com.shtoone.shtw.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.DialogActivity;
import com.shtoone.shtw.activity.MainActivity;
import com.shtoone.shtw.adapter.LaboratoryFragmentRecyclerViewAdapter;
import com.shtoone.shtw.adapter.OnItemClickLitener;
import com.shtoone.shtw.bean.LaboratoryFragmentRecyclerViewItemData;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.fragment.base.BaseFragment;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.DisplayUtils;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.ToastUtils;
import com.shtoone.shtw.utils.URL;
import com.squareup.otto.Subscribe;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by leguang on 2016/5/31 0031.
 */
public class LaboratoryFragment extends BaseFragment {
    private static final String TAG = "LaboratoryFragment";
    private Toolbar mToolbar;
    private PtrFrameLayout ptrframe;
    private RecyclerView recyclerView;
    private StoreHouseHeader header;
    private LaboratoryFragmentRecyclerViewAdapter mAdapter;
    private String startTime = "2016-01-01 00:00:00", endTime = "2016-06-01 00:00:00";
    private String userGroupID = "297ee90c4447f8a4014447fbba1e0015";
    private LaboratoryFragmentRecyclerViewItemData itemData;
    private String url;
    private FloatingActionButton fab;

    public static LaboratoryFragment newInstance() {
        return new LaboratoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_laboratory, container, false);
        initView(view);
        initData();
        BaseApplication.bus.register(this);
        return view;
    }


    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_laboratory_fragment);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_laboratory_fragment);
        ptrframe = (PtrFrameLayout) view.findViewById(R.id.ptr_framelayout_laboratory_fragment);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_laboratory_fragment);
    }

    private void initData() {
        mToolbar.setTitle("XX高速 > 试验室");
        ((MainActivity) _mActivity).initToolBar(mToolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.hide();
                Intent intent = new Intent(_mActivity, DialogActivity.class);
                startActivity(intent);

            }
        });


        header = new StoreHouseHeader(_mActivity);
        final String[] mStringList = {ConstantsUtils.DOMAIN_1, ConstantsUtils.DOMAIN_2};

        // 下拉刷新头部
        header.setTextColor(Color.BLACK);
        header.setPadding(0, DisplayUtils.dp2px(15), 0, 0);

        header.initWithString(mStringList[0]);
        // for changing string
        ptrframe.addPtrUIHandler(new PtrUIHandler() {

            private int mLoadTime = 0;

            @Override
            public void onUIReset(PtrFrameLayout frame) {
                mLoadTime++;
                String string = mStringList[mLoadTime % mStringList.length];
                header.initWithString(string);
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {
                String string = mStringList[mLoadTime % mStringList.length];
            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {

            }

            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

            }
        });
        ptrframe.setHeaderView(header);
        ptrframe.addPtrUIHandler(header);
        ptrframe.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrframe.autoRefresh(true);
            }
        }, 100);
        ptrframe.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                getDataFromNetwork();
                frame.refreshComplete();
            }
        });
    }

    private void getDataFromNetwork() {

        //联网获取数据
        HttpUtils.getRequest(url = URL.getSYSLingdaoData(userGroupID, startTime, endTime), new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                Log.e(TAG, response);
                parseData(response);
            }

            @Override
            public void onFailed(VolleyError error) {
                //提示网络数据异常
            }
        });
    }

    protected void parseData(String response) {
        if (!TextUtils.isEmpty(response)) {
            itemData = new Gson().fromJson(response, LaboratoryFragmentRecyclerViewItemData.class);
            if (null != itemData) {
                if (itemData.isSuccess()) {
                    setAdapter();
                    jumpTo();

                } else {
                    //提示用户名或密码错误
                }
            } else {
                //提示数据解析异常
            }
        } else {
            //提示返回数据异常
        }

    }

    private void setAdapter() {
        // 设置显示形式
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        // 设置适配器
        recyclerView.setAdapter(mAdapter = new LaboratoryFragmentRecyclerViewAdapter(_mActivity, itemData));
        // 设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnItemClickLitener(new OnItemClickLitener() {

            @Override
            public void onItemClick(View view, int position) {

                ToastUtils.showToast(_mActivity, position + "");
                // 实现局部界面刷新, 这个view就是被点击的item布局对象
                changeReadedState(view);
                // 跳转到详情页
                jumpTo();
            }
        });
    }

    private void changeReadedState(View view) {
//        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
//        tvTitle.setTextColor(Color.GRAY);
    }

    private void jumpTo() {
    }

    @Subscribe
    public void updateSearch(ParametersData parametersData) {

        if (parametersData != null) {

            fab.show();
            Log.e(TAG, "回来了");
            ToastUtils.showToast(_mActivity, parametersData.deviceType + "回来了…………………………");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.bus.unregister(this);
    }
}
