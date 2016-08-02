package com.shtoone.shtw.fragment.concreteactivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.DialogActivity;
import com.shtoone.shtw.activity.ProduceQueryDetailActivity;
import com.shtoone.shtw.adapter.OnItemClickListener;
import com.shtoone.shtw.adapter.ProduceQueryFragmentRecyclerViewAdapter;
import com.shtoone.shtw.bean.EventData;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.bean.ProduceQueryFragmentListData;
import com.shtoone.shtw.fragment.base.BaseLazyFragment;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.DisplayUtils;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.ToastUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

/**
 * Created by leguang on 2016/7/20 0020.
 */
public class ProduceQueryFragment extends BaseLazyFragment {
    private static final String TAG = ProduceQueryFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private PtrFrameLayout ptrframe;
    private RecyclerView mRecyclerView;
    private StoreHouseHeader header;
    private ProduceQueryFragmentRecyclerViewAdapter mAdapter;
    private ProduceQueryFragmentListData itemsData;
    private FloatingActionButton fab;
    private boolean isRegistered = false;
    private PageStateLayout pageStateLayout;
    private Gson mGson;
    private boolean isLoading;
    private List<ProduceQueryFragmentListData.DataBean> listData;
    private ParametersData mParametersData;
    private View view;
    private LinearLayoutManager mLinearLayoutManager;
    private int lastVisibleItemPosition;
    private Handler handler = new Handler();

    public static ProduceQueryFragment newInstance() {
        return new ProduceQueryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }
        view = inflater.inflate(R.layout.fragment_produce_query, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //返回到看见此fragment时，fab显示
        fab.show();
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_produce_query_fragment);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_produce_query_fragment);
        ptrframe = (PtrFrameLayout) view.findViewById(R.id.ptr_produce_query_fragment);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_produce_query_fragment);
        pageStateLayout = (PageStateLayout) view.findViewById(R.id.psl_produce_query_fragment);
        pageStateLayout.showLoading();
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        mParametersData = (ParametersData) BaseApplication.parametersData.clone();
        mParametersData.fromTo = ConstantsUtils.PRODUCEQUERYFRAGMENT;
        mGson = new Gson();
        listData = new ArrayList<>();
        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        StringBuffer sb = new StringBuffer(BaseApplication.mUserInfoData.getDepartName() + " > ");
        sb.append(getString(R.string.concrete) + " > ");
        sb.append(getString(R.string.produce_query)).trimToSize();
        mToolbar.setTitle(sb.toString());
        initToolbarBackNavigation(mToolbar);
//        initToolbarMenu(mToolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_mActivity, DialogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstantsUtils.PARAMETERS, mParametersData);
                intent.putExtras(bundle);
                //Activity共享元素切换版本适配
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                startActivity(intent);
//                } else {
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(_mActivity, fab, getString(R.string.transition_dialog));
//                    startActivity(intent, options.toBundle());
//                }
            }
        });

        //设置动画与适配器
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new ProduceQueryFragmentRecyclerViewAdapter(_mActivity, listData));
        mSlideInLeftAnimationAdapter.setFirstOnly(true);
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
        ScaleInAnimationAdapter mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        mScaleInAnimationAdapter.setFirstOnly(true);
        mRecyclerView.setAdapter(mScaleInAnimationAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 实现局部界面刷新, 这个view就是被点击的item布局对象
                changeReadedState(view);
                jump2ProduceQueryDetailActivity(position);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //还有一个不完美的地方就是当规定的item个数时，最后一个item在屏幕外滑到可见时，其底部没有footview，这点以后再解决。
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mAdapter.getItemCount() && listData.size() >= 4) {

                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) + 1) + "";
                                KLog.e("第" + mParametersData.currentPage + "页");
                                getDataFromNetwork(mParametersData);
                                KLog.e("上拉加载更多mParametersData.currentPage=" + mParametersData.currentPage);
                                isLoading = false;
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });

        pageStateLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageStateLayout.showLoading();
                mParametersData.currentPage = "1";
                if (null != listData) {
                    listData.clear();
                }
                getDataFromNetwork(mParametersData);
                KLog.e("点击查询mParametersData.currentPage=" + mParametersData.currentPage);
            }
        });

        pageStateLayout.setOnNetErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageStateLayout.showNetError();
                NetworkUtils.openSetting(_mActivity);
            }
        });

        header = new StoreHouseHeader(_mActivity);
        final String[] mStringList = {ConstantsUtils.DOMAIN_1, ConstantsUtils.DOMAIN_2};

        // 下拉刷新头部
        header.setTextColor(Color.BLACK);
        header.setPadding(0, DisplayUtils.dp2px(15), 0, 0);
        header.initWithString(mStringList[0]);
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
                //判断是哪种状态的页面，都让其可下拉
                if (pageStateLayout.isShowContent) {
                    //判断RecyclerView是否在在顶部，在顶部则允许滑动下拉刷新
                    if (null != mRecyclerView) {
                        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                            LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                            int position = lm.findFirstVisibleItemPosition();
                            if (position >= 0) {
                                if (lm.findViewByPosition(position).getTop() > 0 && position == 0) {
                                    return true;
                                }
                            }
                        }
                    } else {
                        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                    }
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                mParametersData.currentPage = "1";
                if (null != listData) {
                    listData.clear();
                }
                getDataFromNetwork(mParametersData);
                KLog.e("下拉刷新mParametersData.currentPage=" + mParametersData.currentPage);
                frame.refreshComplete();
            }
        });
    }

    //联网获取数据
    private void getDataFromNetwork(final ParametersData mParametersData) {
        //从全局参数类中取出参数，避免太长了，看起来不方便
        String userGroupID = mParametersData.userGroupID;
        String startDateTime = mParametersData.startDateTime;
        String endDateTime = mParametersData.endDateTime;
        String currentPage = mParametersData.currentPage;
        String equipmentID = mParametersData.equipmentID;

        //联网获取数据
        //还没有判断url，用户再判断
        HttpUtils.getRequest(URL.getProduceData(userGroupID, startDateTime, endDateTime, currentPage, equipmentID), new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.json(response);
                parseData(response);
            }

            @Override
            public void onFailed(VolleyError error) {
                KLog.e(error.toString());
                if (listData.size() > 0) {
                    //此时是分页上拉加载更多
                    //提示网络数据异常，展示网络错误页面。此时：1.可能是本机网络有问题，2.可能是服务器问题
                    if (!NetworkUtils.isConnected(_mActivity)) {
                        //提示网络异常,让用户点击设置网络，
                        View view = _mActivity.getWindow().getDecorView();
                        Snackbar.make(view, "当前网络已断开！", Snackbar.LENGTH_LONG)
                                .setAction("设置网络", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // 跳转到系统的网络设置界面
                                        NetworkUtils.openSetting(_mActivity);
                                    }
                                }).show();
                    } else {
                        //服务器异常，展示错误页面，点击刷新
                        ToastUtils.showToast(_mActivity, "服务器异常");
                    }
                    mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                    mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                } else {
                    //提示网络数据异常，展示网络错误页面。此时：1.可能是本机网络有问题，2.可能是服务器问题
                    if (!NetworkUtils.isConnected(_mActivity)) {
                        //提示网络异常,让用户点击设置网络
                        pageStateLayout.showNetError();
                    } else {
                        //服务器异常，展示错误页面，点击刷新
                        pageStateLayout.showError();
                    }
                }
            }
        });
    }

    protected void parseData(String response) {
        itemsData = mGson.fromJson(response, ProduceQueryFragmentListData.class);
        if (null != itemsData && itemsData.isSuccess()) {
            listData.addAll(itemsData.getData());
        }

        if (null != listData) {
            if (listData.size() > 0) {
                pageStateLayout.showContent();
                if (!itemsData.isSuccess()) {
                    ToastUtils.showToast(_mActivity, "无更多数据");
                    mParametersData.currentPage = (Integer.parseInt(mParametersData.currentPage) - 1) + "";
                    mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            } else {
                //提示数据为空，展示空状态
                pageStateLayout.showEmpty();
            }
        } else {
            //提示数据解析异常，展示错误页面
            pageStateLayout.showError();
        }
    }

    private void changeReadedState(View view) {
        //此处可以做一些修改点击过的item的样式，方便用户看出哪些已经点击查看过
    }

    //进入ProduceQueryDetailActivity
    private void jump2ProduceQueryDetailActivity(int position) {
        Intent intent = new Intent(_mActivity, ProduceQueryDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("producequerydetail", listData.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Subscribe
    public void updateSearch(ParametersData mParametersData) {
        if (mParametersData != null) {
            if (mParametersData.fromTo == ConstantsUtils.PRODUCEQUERYFRAGMENT) {
                //后期优化考虑的时候，看这里需不需要克隆，应该只要直接复制即可
                this.mParametersData = (ParametersData) mParametersData.clone();
                this.mParametersData.currentPage = "1";
                if (null != listData) {
                    listData.clear();
                }
//                getDataFromNetwork(this.mParametersData);

                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.smoothScrollToPosition(0);
                    }
                }, 300);
                ptrframe.autoRefresh(true);
                KLog.e("点击查询mParametersData.currentPage=" + mParametersData.currentPage);
            }
        }
    }

    @Subscribe
    public void go2TopOrRefresh(EventData event) {
        if (event.position == 0) {
            mRecyclerView.smoothScrollToPosition(0);
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
