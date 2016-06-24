package com.shtoone.shtw.fragment.messageactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.adapter.MessageActivityViewPagerFragmentRecyclerViewAdapter;
import com.shtoone.shtw.adapter.OnItemClickListener;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.bean.YalijiFragmentViewPagerFragmentRecyclerViewItemData;
import com.shtoone.shtw.fragment.base.BaseFragment;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.DisplayUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.ToastUtils;

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
public class MessageActivityViewPagerFragment extends BaseFragment {

    private static final String TAG = MessageActivityViewPagerFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private MessageActivityViewPagerFragmentRecyclerViewAdapter mAdapter;
    private PageStateLayout pageStateLayout;
    private PtrFrameLayout ptrframe;
    private StoreHouseHeader header;
    private boolean isRegistered = false;
    private YalijiFragmentViewPagerFragmentRecyclerViewItemData itemsData;
    private ParametersData mParametersData;
    private String yalijiID;

    public static MessageActivityViewPagerFragment newInstance() {

        Bundle args = new Bundle();

        MessageActivityViewPagerFragment fragment = new MessageActivityViewPagerFragment();
        fragment.setArguments(args);
        return fragment;


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }
        View view = inflater.inflate(R.layout.fragment_view_pager_message_activity, container, false);
        initView(view);
        initData();
        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_view_pager_message_activity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        ptrframe = (PtrFrameLayout) view.findViewById(R.id.ptr_fragment_view_pager_message_activity);
        pageStateLayout = (PageStateLayout) view.findViewById(R.id.psl_fragment_view_pager_message_activity);
    }

    private void initData() {

        pageStateLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageStateLayout.showContent();
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
                //判断RecyclerView是否在在顶部，在顶部则允许滑动下拉刷新
                if (null != mRecyclerView) {
                    if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                        LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                        if (null != lm) {
                            if (lm.findViewByPosition(lm.findFirstVisibleItemPosition()).getTop() == 0 && lm.findFirstVisibleItemPosition() == 0) {
                                return true;
                            }
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
        setAdapter();
    }

    private void getDataFromNetwork(ParametersData mParametersData) {
        //从全局参数类中取出参数，避免太长了，看起来不方便

    }

    protected void parseData(String response) {

    }

    //还是不能这样搞，可能会内存泄漏，重复创建Adapyer对象。后面解决
    private void setAdapter() {
        //设置动画
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new MessageActivityViewPagerFragmentRecyclerViewAdapter(_mActivity));
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

            }
        });
    }

    private void changeReadedState(View view) {
        //此处可以做一些修改点击过的item的样式，方便用户看出哪些已经点击查看过
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultNoAnimator();
    }

    //进入YaLiJiDetailActivity
    private void jumpTo() {

    }
}