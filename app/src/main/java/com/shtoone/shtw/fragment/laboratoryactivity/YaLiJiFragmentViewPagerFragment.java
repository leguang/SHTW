package com.shtoone.shtw.fragment.laboratoryactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.YaLiJiDetailActivity;
import com.shtoone.shtw.adapter.OnItemClickListener;
import com.shtoone.shtw.adapter.YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.bean.YalijiFragmentViewPagerFragmentRecyclerViewItemData;
import com.shtoone.shtw.fragment.base.BaseFragment;
import com.shtoone.shtw.fragment.mainactivity.LaboratoryFragment;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.DisplayUtils;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.ToastUtils;
import com.shtoone.shtw.utils.URL;
import com.squareup.otto.Subscribe;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by leguang on 2016/6/9 0031.
 */
public class YaLiJiFragmentViewPagerFragment extends BaseFragment {

    private static final String TAG = LaboratoryFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter mAdapter;
    private PageStateLayout pageStateLayout;
    private PtrFrameLayout ptrframe;
    private StoreHouseHeader header;
    private YalijiFragmentViewPagerFragmentRecyclerViewItemData itemsData;

    public static YaLiJiFragmentViewPagerFragment newInstance() {
        return new YaLiJiFragmentViewPagerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_pager_yaliji_fragment, container, false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        BaseApplication.bus.register(this);
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_view_pager_yaliji_fragment);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        ptrframe = (PtrFrameLayout) view.findViewById(R.id.ptr_framelayout_fragment_view_pager_yaliji_fragment);
        pageStateLayout = (PageStateLayout) view.findViewById(R.id.psl_fragment_view_pager_yaliji_fragment);
    }

    private void initData() {
        pageStateLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageStateLayout.showContent();
                getDataFromNetwork();
            }
        });

        pageStateLayout.setOnNetErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageStateLayout.showEmpty();
                NetworkUtils.openSetting(_mActivity);
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
                LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                if (lm.findViewByPosition(lm.findFirstVisibleItemPosition()).getTop() == 0 && lm.findFirstVisibleItemPosition() == 0) {
                    return true;
                }
                return false;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                getDataFromNetwork();
                frame.refreshComplete();
            }
        });

    }

    private void getDataFromNetwork() {
        //从全局参数类中取出参数，避免太长了，看起来不方便
        String userGroupID = BaseApplication.parametersData.userGroupID;
        String startDateTime = BaseApplication.parametersData.startDateTime;
        String endDateTime = BaseApplication.parametersData.endDateTime;
        String currentPage = BaseApplication.parametersData.currentPage;
        String equipmentNumber = BaseApplication.parametersData.equipmentNumber;
        String isQualified = BaseApplication.parametersData.isQualified;
        String isReal = BaseApplication.parametersData.isReal;
        String testType = BaseApplication.parametersData.testType;

        //联网获取数据
        HttpUtils.getRequest(URL.getYalijiTestList(userGroupID, isQualified, startDateTime, endDateTime, currentPage, equipmentNumber, isReal, testType), new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                Log.e(TAG, response);
                parseData(response);
            }

            @Override
            public void onFailed(VolleyError error) {
                //提示网络数据异常，展示网络错误页面。此时：1.可能是本机网络有问题，2.可能是服务器问题
                if (!NetworkUtils.isConnected(_mActivity)) {
                    //提示网络异常,让用户点击设置网络
                    pageStateLayout.showNetError();
                } else {
                    //服务器异常，展示错误页面，点击刷新
                    pageStateLayout.showError();
                }
            }
        });
    }

    protected void parseData(String response) {
        if (!TextUtils.isEmpty(response)) {
            itemsData = new Gson().fromJson(response, YalijiFragmentViewPagerFragmentRecyclerViewItemData.class);
            if (null != itemsData) {
                if (itemsData.isSuccess()) {
                    pageStateLayout.showContent();
                    setAdapter();
                } else {
                    //提示数据为空，展示空状态
                    pageStateLayout.showEmpty();
                }
            } else {
                //提示数据解析异常，展示错误页面
                pageStateLayout.showError();
            }
        } else {
            //提示返回数据异常，展示错误页面
            pageStateLayout.showError();
        }
    }

    //还是不能这样搞，可能会内存泄漏，重复创建Adapyer对象。后面解决
    private void setAdapter() {
        //设置动画
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter(_mActivity, itemsData));
        mSlideInLeftAnimationAdapter.setFirstOnly(true);
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));

        ScaleInAnimationAdapter mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        mScaleInAnimationAdapter.setFirstOnly(true);
        mRecyclerView.setAdapter(mScaleInAnimationAdapter);

        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showToast(_mActivity, "点击第：" + position);
                // 实现局部界面刷新, 这个view就是被点击的item布局对象
                changeReadedState(view);
                jumpToYaLiJiDetailActivity();
            }
        });
    }

    private void changeReadedState(View view) {
        //此处可以做一些修改点击过的item的样式，方便用户看出哪些已经点击查看过
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimation() {
        return new DefaultNoAnimator();
    }

    //进入YaLiJiDetailActivity
    private void jumpToYaLiJiDetailActivity() {
        Intent intent = new Intent(_mActivity, YaLiJiDetailActivity.class);
        startActivity(intent);
    }

    @Subscribe
    public void updateSearch(ParametersData parametersData) {
        if (parametersData != null) {
            ptrframe.autoRefresh(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        BaseApplication.bus.unregister(this);
    }
}
