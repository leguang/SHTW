package com.shtoone.shtw.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	public static Toast mToast;

	private ToastUtils() {
        /* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	public static void showToast(Context mContext, String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
		}
		mToast.setText(msg);
		mToast.show();
	}
}
