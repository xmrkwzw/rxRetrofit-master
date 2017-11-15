package com.xmjj.rxretrofit_master.util.recyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xmjj.jujianglibrary.util.GlideImageLoader;


/**
 * Created by wangzhiwei on 2016/6/2.
 */
public class BaseRecycleHolder extends RecyclerView.ViewHolder {

	/**
	 * 用于存储当前item当中的View
	 */
	private SparseArray<View> mViews;
	private Context context;


	public BaseRecycleHolder(Context context, View itemView) {
		super(itemView);
		this.context = context;
		mViews = new SparseArray<>();
	}

	public <T extends View> T findView(int ViewId) {
		View view = mViews.get(ViewId);
		//集合中没有，则从item当中获取，并存入集合当中
		if (view == null) {
			view = itemView.findViewById(ViewId);
			mViews.put(ViewId, view);
		}
		return (T) view;
	}

	public BaseRecycleHolder setText(int viewId, String text) {
		TextView tv = findView(viewId);

		tv.setText(text);

		return this;
	}

	public BaseRecycleHolder setTextColor(int viewId, int res) {
		TextView tv = findView(viewId);

		tv.setTextColor(context.getResources().getColor(res));

		return this;
	}

	public BaseRecycleHolder setText(int viewId, Spanned s) {
		TextView tv = findView(viewId);
		if (!TextUtils.isEmpty(s.toString())) {
			tv.setText(s.toString());
		}

		return this;
	}


	public BaseRecycleHolder setLinearLayoutBg(int viewId, int drawableRes) {
		LinearLayout ll = findView(viewId);
		ll.setBackgroundResource(drawableRes);
		return this;
	}


	public BaseRecycleHolder setImageResource(int viewId, int ImageId) {
		ImageView image = findView(viewId);
		image.setImageResource(ImageId);
		return this;
	}

	public BaseRecycleHolder setImageResource2(int viewId, int ImageId) {
		ImageView image = findView(viewId);
		image.setBackgroundResource(ImageId);
		return this;
	}

	public BaseRecycleHolder setImageBitmap(int viewId, Bitmap bitmap) {
		ImageView image = findView(viewId);
		image.setImageBitmap(bitmap);
		return this;
	}


	public BaseRecycleHolder setImageBg(int viewId, String url) {
		ImageView image = findView(viewId);
		GlideImageLoader.getInstance().setImage(context, url, image, null);

		//使用你所用的网络框架等
		return this;
	}

	public BaseRecycleHolder setRoundBg(int viewId, String url) {
		ImageView image = findView(viewId);
		GlideImageLoader.getInstance().setRoundImage(context, url, image);
		return this;
	}

	//可增加其他方法（如项目需要后续慢慢增加）


	public BaseRecycleHolder setImageParams(int viewId, ViewGroup.LayoutParams params) {
		ImageView imageView = findView(viewId);

		imageView.setLayoutParams(params);
		return this;
	}

	public BaseRecycleHolder setTextViewParams(int viewId, ViewGroup.LayoutParams params) {
		TextView textView = findView(viewId);

		textView.setLayoutParams(params);
		return this;
	}


}
