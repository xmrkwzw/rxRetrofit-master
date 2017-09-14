package com.xmjj.rxretrofit_master.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;
import com.xmjj.jujianglibrary.util.skinloader.attr.DynamicAttr;
import com.xmjj.jujianglibrary.util.skinloader.listener.IDynamicNewView;

import java.util.ArrayList;
import java.util.List;


/**
 * 功能描述：
 * Created by wzw
 * 2017/8/24
 */

public abstract class BaseFragment extends RxFragment implements IDynamicNewView {
	public View containerView;
	public BaseActivity activity;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (containerView == null) {
			containerView = inflater.inflate(getLayoutResId(), null);
			activity = (BaseActivity) getActivity();
			activity.getActionBarHeight();
			activity.getScreenSize();
			initViews();
			initData();
		}
		ViewGroup parent = (ViewGroup) containerView.getParent();
		if (parent != null) {
			parent.removeView(containerView);
		}
		return containerView;
	}
	private IDynamicNewView mIDynamicNewView;
	private LayoutInflater mLayoutInflater;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try {
			mIDynamicNewView = (IDynamicNewView) context;
		} catch (ClassCastException e) {
			mIDynamicNewView = null;
		}
	}

	@Override
	public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
		if (mIDynamicNewView == null) {
			throw new RuntimeException("IDynamicNewView should be implements !");
		} else {
			mIDynamicNewView.dynamicAddView(view, pDAttrs);
		}
	}

	public void dynamicAddSkinView(View view, String attrName, int attrValueResId) {
		List<DynamicAttr> pDAttrs = new ArrayList<>();
		pDAttrs.add(new DynamicAttr(attrName, attrValueResId));
		dynamicAddView(view, pDAttrs);
	}

	public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
		LayoutInflater result = getActivity().getLayoutInflater();
		return result;
	}

	public abstract int getLayoutResId();

	public abstract void initViews();

	public abstract void initData();


	protected <T extends View> T findView(int id) {
		return (T) containerView.findViewById(id);
	}

}
