package com.xmjj.rxretrofit_master.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;


/**
 * 功能描述：
 * Created by wzw
 * 2017/8/24
 */

public abstract class BaseFragment extends RxFragment {
	public View containerView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (containerView == null) {
			containerView = inflater.inflate(getLayoutResId(), null);
			initViews();
			initData();
		}
		ViewGroup parent = (ViewGroup) containerView.getParent();
		if (parent != null) {
			parent.removeView(containerView);
		}
		return containerView;
	}

	public abstract int getLayoutResId();

	public abstract void initViews();

	public abstract void initData();


	protected <T extends View> T findView(int id) {
		return (T) containerView.findViewById(id);
	}

}
