package com.shtoone.shtw.fragment.base;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.VolleyError;
import com.shtoone.shtw.R;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.DisplayUtils;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.ToastUtils;
import com.socks.library.KLog;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by leguang on 16/5/4.
 */
public abstract class BaseFragment extends SupportFragment {
    private static final String TAG = BaseFragment.class.getSimpleName();

    protected void initToolbarBackNavigation(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
    }

    protected void initToolbarMenu(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.menu_hierarchy);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_hierarchy:
                        ToastUtils.showToast(_mActivity, "组织结构");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new FragmentAnimator(0, 0, 0, 0);
    }

    public void setPageStateLayout(final PageStateLayout mPageStateLayout) {
        if (null == mPageStateLayout) return;

        mPageStateLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageStateLayout.showLoading();
                refresh();
            }
        });

        mPageStateLayout.setOnNetErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageStateLayout.showEmpty();
                NetworkUtils.openSetting(_mActivity);
            }
        });
    }

    public void setPtrFrameLayout(final PtrFrameLayout mPtrFrameLayout) {
        if (null == mPtrFrameLayout) return;

        // 下拉刷新头部
        final StoreHouseHeader ptrHeader = new StoreHouseHeader(_mActivity);
        final String[] mStringList = {ConstantsUtils.DOMAIN_1, ConstantsUtils.DOMAIN_2};
        ptrHeader.setTextColor(Color.BLACK);
        ptrHeader.setPadding(0, DisplayUtils.dp2px(15), 0, 0);
        ptrHeader.initWithString(mStringList[0]);
        mPtrFrameLayout.addPtrUIHandler(new PtrUIHandler() {
            private int mLoadTime = 0;

            @Override
            public void onUIReset(PtrFrameLayout frame) {
                mLoadTime++;
                String string = mStringList[mLoadTime % mStringList.length];
                ptrHeader.initWithString(string);
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
        mPtrFrameLayout.setHeaderView(ptrHeader);
        mPtrFrameLayout.addPtrUIHandler(ptrHeader);
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh(true);
            }
        }, 100);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return isCanDoRefresh();

            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                refresh();
                frame.refreshComplete();
            }
        });
    }

    public void refresh() {
        String URL = createRefreshULR();

        //联网获取数据
        //还没有判断url，用户再判断
        HttpUtils.getRequest(URL, new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.json(response);
                refreshSuccess(response);
            }

            @Override
            public void onFailed(VolleyError error) {
                KLog.d(error);
                refreshFailed(error);
            }
        });
    }

    public void refreshSuccess(String response) {
    }

    public void refreshFailed(VolleyError error) {
    }

    public void loadMore() {
        String URL = createLoadMoreULR();

        //联网获取数据
        //还没有判断url，用户再判断
        HttpUtils.getRequest(URL, new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.json(response);
                loadMoreSuccess(response);
            }

            @Override
            public void onFailed(VolleyError error) {
                KLog.d(error);
                loadMoreFailed(error);
            }
        });
    }

    public void loadMoreSuccess(String response) {
    }

    public void loadMoreFailed(VolleyError error) {
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
    }

    public boolean isCanDoRefresh() {
        return true;
    }

    public String createRefreshULR() {
        return null;
    }

    public String createLoadMoreULR() {
        return null;
    }
}
