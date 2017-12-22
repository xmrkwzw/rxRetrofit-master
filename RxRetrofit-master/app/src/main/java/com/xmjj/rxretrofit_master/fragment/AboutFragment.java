package com.xmjj.rxretrofit_master.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wzgiceman.rxbuslibrary.rxbus.RxBus;
import com.xmjj.jujianglibrary.util.GlideImageLoader;
import com.xmjj.jujianglibrary.util.SharedPreferencesUtil;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.activity.Camera2Activity;
import com.xmjj.rxretrofit_master.base.BaseFragment;
import com.xmjj.rxretrofit_master.contact.Constant;
import com.xmjj.rxretrofit_master.entity.event.HeaderUpdateEvent;
import com.xmjj.rxretrofit_master.util.DisplayUtils;
import com.xmjj.rxretrofit_master.util.PopupWindowUtil;
import com.xmjj.rxretrofit_master.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.xmjj.rxretrofit_master.R.id.tv_content;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/11
 */

public class AboutFragment extends BaseFragment {
	@BindView(tv_content)
	TextView tvContent;
	@BindView(R.id.civ_header)
	CircleImageView header;

	private SharedPreferencesUtil preferencesUtil = SharedPreferencesUtil.getInstance();
	private PopupWindowUtil popupWindowUtil;


	@Override
	public int getLayoutResId() {
		return R.layout.fragment_about;
	}

	@Override
	public void initViews() {
		tvContent = findView(tv_content);
		tvContent.setAutoLinkMask(Linkify.ALL);
		tvContent.setMovementMethod(LinkMovementMethod
				.getInstance());


	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		popupWindowUtil.disMissPop();
	}

	@Override
	public void initData() {
		String obj = preferencesUtil.getString(Constant.SharePerferencedKey.URL);
		if (!TextUtils.isEmpty(obj)) {
			setHeaderBg(obj);
			return;
		}
		setHeaderBg(R.mipmap.avatar);
	}

	public void setHeaderBg(Object url) {
		GlideImageLoader.getInstance().setImage(getActivity(), url, header, null);
		SharedPreferencesUtil.getInstance().putString(Constant.SharePerferencedKey.URL, url.toString());
	}

	@OnClick(R.id.civ_header)
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.civ_header:
				showPpw();
				break;
		}
	}

	// 从本地相册选取图片作为头像
	private void choseHeadImageFromGallery() {
		Intent intentFromGallery = new Intent();
		// 设置文件类型
		intentFromGallery.setType("image/*");
		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intentFromGallery, Constant.CODE.CODE_GALLERY_REQUEST);
	}

	private void choseHeadImageFromCamera() {
		Intent intentCamera = new Intent(activity, Camera2Activity.class);
		startActivityForResult(intentCamera, Constant.CODE.CODE_CAMERA_REQUEST);


	}

	private void showPpw() {
		popupWindowUtil = new PopupWindowUtil(activity, R.layout.ppw_photo) {
			@Override
			public int setWidth(View view) {
				return DisplayUtils.getScreenWidth(activity);
			}

			@Override
			public int setHeight(View view) {
				return view.getMeasuredHeight();
			}

			@Override
			public void init(View view, final PopupWindow popupWindow) {
				TextView tvGallery = (TextView) view.findViewById(R.id.tv_gallery);
				TextView tvPhoto = (TextView) view.findViewById(R.id.tv_take_photo);
				TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);


				tvGallery.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						choseHeadImageFromGallery();
						popupWindow.dismiss();
					}
				});

				tvPhoto.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						choseHeadImageFromCamera();
						popupWindow.dismiss();
					}
				});

				tvCancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						popupWindow.dismiss();
					}
				});


			}

			@Override
			public void setLocation(PopupWindow popupWindow) {
				popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
			}
		};
		popupWindowUtil.createPopupWindow(true, true);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == activity.RESULT_CANCELED) {
			ToastUtils.showShortMes(activity, "取消操作");
			return;
		}
		switch (requestCode) {
			case Constant.CODE.CODE_GALLERY_REQUEST:
				setHeaderBg(data.getData());
				RxBus.getDefault().post(new HeaderUpdateEvent(data.getData()));
				break;

			case Constant.CODE.CODE_CAMERA_REQUEST:
				setHeaderBg(data.getParcelableExtra(Constant.Intent_key.URI));
				RxBus.getDefault().post(new HeaderUpdateEvent(data.getParcelableExtra(Constant.Intent_key.URI)));
				break;


		}

	}


}
