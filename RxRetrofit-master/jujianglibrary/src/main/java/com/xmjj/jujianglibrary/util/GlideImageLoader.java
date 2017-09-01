package com.xmjj.jujianglibrary.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.xmjj.jujianglibrary.R;


public class GlideImageLoader {
	public volatile static GlideImageLoader imageLoader;

	public static GlideImageLoader getInstance() {
		if (imageLoader == null) {
			synchronized (GlideImageLoader.class) {
				if (imageLoader == null) {
					imageLoader = new GlideImageLoader();
				}
			}
		}
		return imageLoader;
	}

	private RequestOptions getRequestOptions() {
		RequestOptions requestOptions = new RequestOptions();
		requestOptions.centerCrop()
				.placeholder(R.mipmap.ic_launcher)
				.error(R.mipmap.ic_launcher)
				.priority(Priority.HIGH)
				.dontAnimate();
		return requestOptions;
	}

	/*一般的图片设置*/
	public void setImage(Context context, Object object, ImageView imageView, RequestOptions requestOptions) {
		RequestBuilder requestBuilder = Glide.with(context).load(object).thumbnail(0.5f);
		if (requestOptions != null) {
			requestBuilder.apply(requestOptions);
		}
		requestBuilder.listener(new RequestListener() {
			@Override
			public boolean onLoadFailed(@Nullable GlideException e, Object o, Target target, boolean b) {
				return false;
			}

			@Override
			public boolean onResourceReady(Object o, Object o2, Target target, DataSource dataSource, boolean b) {
				return false;
			}
		});
		requestBuilder.into(imageView);
	}

	/**
	 * 设置图片是否缓存
	 *
	 * @param context
	 * @param object
	 * @param imageView
	 * @param isNotCache 是否要缓存
	 */
	public void setImageNocache(Context context, Object object, ImageView imageView, boolean isNotCache) {
		RequestOptions options = getRequestOptions();

		if (isNotCache) {
			//不做缓存
			options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
		}
		setImage(context, object, imageView, options);
	}

	/*设置centerCrop缩放模式*/
	public void setImageCenterCrop(Context context, Object object, ImageView imageView) {
		RequestOptions options = getRequestOptions();
		options.centerCrop();
		setImage(context, object, imageView, options);
	}

	/*设置fitCenter的缩放模式*/
	public void setImageFitCenter(Context context, Object object, ImageView imageView) {

		RequestOptions options = getRequestOptions();
		options.fitCenter();
		setImage(context, object, imageView, options);
	}


	/*设置圆形头像*/
	public void setRoundImage(Context context, Object object, ImageView imageView) {
		RequestOptions options = getRequestOptions();
		options.circleCrop();
		options.override(200,200);
		setImage(context, object, imageView, options);
	}

	/*设置圆角图片*/
	public void setRoundCornerImage(Context context, Object object, ImageView imageView, int corner) {
		RequestOptions options = getRequestOptions();
		options.transform(new RoundedCorners(corner));
		options.override(200,200);
		setImage(context, object, imageView, options);
	}

	/*显示gif图*/
	public void setGifImage(Context context, Object object, ImageView imageView) {

		Glide.with(context).asGif().load(object).into(imageView);

	}




}