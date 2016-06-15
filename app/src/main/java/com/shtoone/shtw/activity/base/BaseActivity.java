package com.shtoone.shtw.activity.base;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.shtoone.shtw.R;
import com.shtoone.shtw.utils.NetworkUtils;

import me.yokeyword.fragmentation.SupportActivity;


public abstract class BaseActivity extends SupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!NetworkUtils.isConnected(this)) {
            View view = getWindow().getDecorView();
            Snackbar.make(view, "当前网络已断开！", Snackbar.LENGTH_LONG)
                    .setAction("设置网络", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 跳转到系统的网络设置界面
                            NetworkUtils.openSetting(BaseActivity.this);
                        }
                    })
                    .show();
        }
        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    protected void initToolbarBackNavigation(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                        break;
                }
                return true;
            }
        });
    }

}
