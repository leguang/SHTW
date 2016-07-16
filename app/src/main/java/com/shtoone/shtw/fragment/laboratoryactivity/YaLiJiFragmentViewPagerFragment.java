package com.shtoone.shtw.fragment.laboratoryactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by leguang on 2016/6/9 0031.
 */
public class YaLiJiFragmentViewPagerFragment extends BaseFragment {
    private static final String TAG = YaLiJiFragmentViewPagerFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter mAdapter;
    private PageStateLayout pageStateLayout;
    private PtrFrameLayout ptrframe;
    private StoreHouseHeader header;
    private boolean isRegistered = false;
    private YalijiFragmentViewPagerFragmentRecyclerViewItemData itemsData;
    private ParametersData mParametersData;
    private int lastVisibleItemPosition;
    private boolean isLoading;
    private List<YalijiFragmentViewPagerFragmentRecyclerViewItemData.DataBean> listData;
    private Handler handler = new Handler();
    private Gson mGson;

    public static YaLiJiFragmentViewPagerFragment newInstance(ParametersData mParametersData) {
        Bundle args = new Bundle();
        args.putSerializable("ParametersData", mParametersData);
        YaLiJiFragmentViewPagerFragment fragment = new YaLiJiFragmentViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mParametersData = (ParametersData) args.getSerializable("ParametersData");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }
        View view = inflater.inflate(R.layout.fragment_view_pager_yaliji_fragment, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        listData = new ArrayList<>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_view_pager_yaliji_fragment);
        final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        //设置动画与适配器
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new YaLiJiFragmentViewPagerFragmentRecyclerViewAdapter(_mActivity, listData));
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
                jumpToYaLiJiDetailActivity(position);
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
                        }, 1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
        ptrframe = (PtrFrameLayout) view.findViewById(R.id.ptr_framelayout_fragment_view_pager_yaliji_fragment);
        pageStateLayout = (PageStateLayout) view.findViewById(R.id.psl_fragment_view_pager_yaliji_fragment);
        pageStateLayout.showLoading();
    }

    private void initData() {
        mGson = new Gson();
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
                                if (lm.findViewByPosition(position).getTop() == 0 && position == 0 && BaseApplication.isExpand) {
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

    private void getDataFromNetwork(final ParametersData mParametersData) {
        //从全局参数类中取出参数，避免太长了，看起来不方便
        String userGroupID = mParametersData.userGroupID;
        String startDateTime = mParametersData.startDateTime;
        String endDateTime = mParametersData.endDateTime;
        String currentPage = mParametersData.currentPage;
        String equipmentID = mParametersData.equipmentID;
        String isQualified = mParametersData.isQualified;
        String isReal = mParametersData.isReal;
        String testType = mParametersData.testTypeID;

        //联网获取数据
        //还没有判断url，用户再判断
        HttpUtils.getRequest(URL.getYalijiTestList(userGroupID, isQualified, startDateTime, endDateTime, currentPage, equipmentID, isReal, testType), new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.json(response);
                parseData(response);
            }

            @Override
            public void onFailed(VolleyError error) {
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
        if (null != itemsData) {
            itemsData.getData().clear();
        }
        itemsData = mGson.fromJson(response, YalijiFragmentViewPagerFragmentRecyclerViewItemData.class);
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

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultNoAnimator();
    }

    //进入YaLiJiDetailActivity
    private void jumpToYaLiJiDetailActivity(int position) {

        Intent intent = new Intent(_mActivity, YaLiJiDetailActivity.class);
        intent.putExtra("detailID", listData.get(position).getSYJID());
        startActivity(intent);
    }

    @Subscribe
    public void updateSearch(ParametersData mParametersData) {
        if (mParametersData != null) {
            if (mParametersData.fromTo == ConstantsUtils.YALIJIFRAGMENT) {
                ToastUtils.showToast(_mActivity, mParametersData.testTypeID);
                this.mParametersData.startDateTime = mParametersData.startDateTime;
                this.mParametersData.endDateTime = mParametersData.endDateTime;
                this.mParametersData.equipmentID = mParametersData.equipmentID;
                this.mParametersData.testTypeID = mParametersData.testTypeID;
                this.mParametersData.currentPage = "1";
                if (null != listData) {
                    listData.clear();
                }
                getDataFromNetwork(this.mParametersData);
                KLog.e("点击查询mParametersData.currentPage=" + mParametersData.currentPage);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.bus.unregister(this);
    }
}
