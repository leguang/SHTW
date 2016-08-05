package com.shtoone.shtw.activity;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.adapter.OrganizationTreeListViewAdapter;
import com.shtoone.shtw.bean.OrganizationActivityData;
import com.shtoone.shtw.bean.OrganizationBean;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.ui.treeview.Node;
import com.shtoone.shtw.ui.treeview.TreeListViewAdapter;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class OrganizationActivity extends BaseActivity {
    private static final String TAG = OrganizationActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private ListView treeListView;
    private LinearLayout ll_container;
    private ParametersData mParametersData;
    private PageStateLayout mPageStateLayout;
    private OrganizationActivityData data;
    private List<OrganizationBean> treeNodes;
    private String type;
    private OrganizationTreeListViewAdapter<OrganizationBean> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);
        initView();
        initData();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_organization_activity);
        mPageStateLayout = (PageStateLayout) findViewById(R.id.psl_organization_activity);
        treeListView = (ListView) findViewById(R.id.lv_tree_organization_activity);
        ll_container = (LinearLayout) findViewById(R.id.ll_container_organization_activity);
    }

    private void initData() {
        type = getIntent().getStringExtra("type");
        mParametersData = BaseApplication.parametersData;
        StringBuffer sb = new StringBuffer(BaseApplication.mUserInfoData.getDepartName() + " > ");
        sb.append(getString(R.string.organization)).trimToSize();
        mToolbar.setTitle(sb.toString());
        initToolbarBackNavigation(mToolbar);
        treeNodes = new ArrayList<OrganizationBean>();
        getDataFromNetwork();
    }

    //联网获取数据
    private void getDataFromNetwork() {
        //从全局参数类中取出参数，避免太长了，看起来不方便
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Calendar rld = Calendar.getInstance();
        String dateTime = sdf.format(rld.getTime());
        String userGroupID = mParametersData.userGroupID;
        String mUserRole = BaseApplication.mUserInfoData.getUserRole();

        HttpUtils.getRequest(URL.getOrganizationData(dateTime, type, userGroupID, mUserRole), new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.e(TAG, response);
                parseData(response);
            }

            @Override
            public void onFailed(VolleyError error) {
                //提示网络数据异常，展示网络错误页面。此时：1.可能是本机网络有问题，2.可能是服务器问题
                if (!NetworkUtils.isConnected(OrganizationActivity.this)) {
                    //提示网络异常,让用户点击设置网络
                    mPageStateLayout.showNetError();
                } else {
                    //服务器异常，展示错误页面，点击刷新
                    mPageStateLayout.showError();
                }
            }
        });
    }

    protected void parseData(String response) {
        if (!TextUtils.isEmpty(response)) {
            data = new Gson().fromJson(response, OrganizationActivityData.class);
            if (null != data) {
                if (data.isSuccess()) {
                    mPageStateLayout.showContent();
                    setViewData();
                } else {
                    //提示数据为空，展示空状态
                    mPageStateLayout.showEmpty();
                }
            } else {
                //提示数据解析异常，展示错误页面
                mPageStateLayout.showError();
            }
        } else {
            //提示返回数据异常，展示错误页面
            mPageStateLayout.showError();
        }
    }

    //还是不能这样搞，可能会内存泄漏，重复创建Adapyer对象。后面解决
    private void setViewData() {
        if (null == data || !(data.getData().size() > 0)) {
            return;
        }
        String ID = null;
        String ParentID = null;
        String name = null;

        for (OrganizationActivityData.DataBean mDataBean : data.getData()) {
            if (TextUtils.isEmpty(mDataBean.getID())) {
                mPageStateLayout.showError();
                return;
            } else {
                ID = mDataBean.getID();
            }

            ParentID = mDataBean.getParentdepartid();
            name = mDataBean.getDepartname();
            treeNodes.add(new OrganizationBean(ID, ParentID, name));
        }

        try {
            mAdapter = new OrganizationTreeListViewAdapter<OrganizationBean>(treeListView, this, treeNodes, 10);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        treeListView.setAdapter(mAdapter);
        mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int position) {
                BaseApplication.mDepartmentData.departmentID = node.getId();
                BaseApplication.mDepartmentData.departmentName = node.getName();
                BaseApplication.bus.post(node);
                onBackPressed();
            }
        });
    }

    private void revealView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = ll_container.getRight();
            int cy = ll_container.getTop();
            int radius = Math.max(ll_container.getWidth(), ll_container.getHeight());
            Animator mAnimator = ViewAnimationUtils.createCircularReveal(mPageStateLayout, cx, cy, 0, radius);
            mAnimator.setDuration(500);
            mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimator.start();
        }
    }

//    private void hideView() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            int cx = ll_container.getRight();
//            int cy = ll_container.getTop();
//            int radius = Math.max(ll_container.getWidth(), ll_container.getHeight());
//            Animator mAnimator = ViewAnimationUtils.createCircularReveal(mPageStateLayout, cx, cy, radius, 0);
//            mAnimator.setDuration(500);
//            mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//            mAnimator.addListener(new AnimatorListenerAdapter() {
//
//                @Override
//                public void onAnimationStart(Animator animation) {
//                    super.onAnimationStart(animation);
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    super.onAnimationEnd(animation);
//                    finish();
//                }
//            });
//            mAnimator.start();
//        }
//    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        revealView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 添加返回过渡动画.
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }
}
