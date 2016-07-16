package com.shtoone.shtw.fragment.mainactivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.DialogActivity;
import com.shtoone.shtw.activity.LaboratoryActivity;
import com.shtoone.shtw.activity.MainActivity;
import com.shtoone.shtw.adapter.LaboratoryFragmentRecyclerViewAdapter;
import com.shtoone.shtw.adapter.OnItemClickListener;
import com.shtoone.shtw.bean.LaboratoryFragmentRecyclerViewItemData;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.fragment.base.BaseFragment;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.DisplayUtils;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.ToastUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;
import com.squareup.otto.Subscribe;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;


/**
 * Created by leguang on 2016/5/31 0031.
 */
public class LaboratoryFragment extends BaseFragment {
    private static final String TAG = LaboratoryFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private PtrFrameLayout ptrframe;
    private RecyclerView mRecyclerView;
    private StoreHouseHeader header;
    private LaboratoryFragmentRecyclerViewAdapter mAdapter;
    private LaboratoryFragmentRecyclerViewItemData itemData;
    private FloatingActionButton fab;
    private PageStateLayout pageStateLayout;
    private boolean isRegistered;
    private ParametersData mParametersData;
    private View view;

    public static LaboratoryFragment newInstance() {
        return new LaboratoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BaseApplication.bus.register(this);
        view = inflater.inflate(R.layout.fragment_laboratory, container, false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //返回到看见此fragment时，fab显示
        fab.show();
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_laboratory_fragment);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_laboratory_fragment);
        ptrframe = (PtrFrameLayout) view.findViewById(R.id.ptr_framelayout_laboratory_fragment);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_laboratory_fragment);
        pageStateLayout = (PageStateLayout) view.findViewById(R.id.psl_laboratory_fragment);
    }

    private void initData() {
        mParametersData = (ParametersData) BaseApplication.parametersData.clone();
        mParametersData.fromTo = ConstantsUtils.LABORATORYFRAGMENT;
        isRegistered = false;

        //做健壮性判断
        StringBuffer sb = new StringBuffer(BaseApplication.mUserInfoData.getDepartName() + " > ");
        sb.append(getString(R.string.laboratory)).trimToSize();
        mToolbar.setTitle(sb.toString());

        ((MainActivity) _mActivity).initToolBar(mToolbar);
        initToolbarMenu(mToolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Activity共享元素切换版本适配
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    fab.hide();
                    Intent intent = new Intent(_mActivity, DialogActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ConstantsUtils.PARAMETERS, mParametersData);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(_mActivity, DialogActivity.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(_mActivity, fab, getString(R.string.transition_dialog));
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ConstantsUtils.PARAMETERS, mParametersData);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 100, options.toBundle());
                }
            }
        });

        pageStateLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageStateLayout.showLoading();
                getDataFromNetwork(mParametersData);
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
                if (null != mRecyclerView) {
                    if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                        LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                        if (lm.findViewByPosition(lm.findFirstVisibleItemPosition()).getTop() == 0 && lm.findFirstVisibleItemPosition() == 0) {
                            return true;
                        }
                    }
                } else {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                }
                return false;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                getDataFromNetwork(mParametersData);
                frame.refreshComplete();
            }
        });

//        showTipMask();
    }


    private void getDataFromNetwork(ParametersData mParametersData) {

        //联网获取数据
        HttpUtils.getRequest(URL.getSYSLingdaoData(mParametersData.userGroupID, mParametersData.startDateTime, mParametersData.endDateTime), new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.e(TAG, response);
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
            itemData = new Gson().fromJson(response, LaboratoryFragmentRecyclerViewItemData.class);
            if (null != itemData) {
                if (itemData.isSuccess()) {
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
        // 设置显示形式
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));

        //设置动画
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new LaboratoryFragmentRecyclerViewAdapter(_mActivity, itemData));
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
        ScaleInAnimationAdapter mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        mRecyclerView.setAdapter(mScaleInAnimationAdapter);

        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                ToastUtils.showToast(_mActivity, "点击第：" + position);
                // 实现局部界面刷新, 这个view就是被点击的item布局对象
                changeReadedState(view);
                // 跳转到详情页
                jumpToLaboratoryActivity();
            }
        });
    }

    private void changeReadedState(View view) {
        //此处可以做一些修改点击过的item的样式，方便用户看出哪些已经点击查看过
    }

    private void jumpToLaboratoryActivity() {
        Intent intent = new Intent(_mActivity, LaboratoryActivity.class);
        startActivity(intent);
    }

    @Subscribe
    public void updateSearch(ParametersData mParametersData) {

        if (mParametersData != null) {
            if (mParametersData.fromTo == ConstantsUtils.LABORATORYFRAGMENT) {
                fab.show();
                ToastUtils.showToast(_mActivity, "刷新");
                this.mParametersData = mParametersData;
                getDataFromNetwork(mParametersData);
                KLog.e(TAG, "fromto:" + mParametersData.fromTo);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        //防止屏幕旋转后重画时fab显示
        fab.hide();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.bus.unregister(this);
    }
}
