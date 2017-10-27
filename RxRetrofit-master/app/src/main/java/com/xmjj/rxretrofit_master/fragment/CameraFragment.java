package com.xmjj.rxretrofit_master.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.xmjj.jujianglibrary.util.ToastUtils;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseFragment;

import java.nio.ByteBuffer;
import java.util.Arrays;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

import static android.os.Looper.getMainLooper;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/13
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CameraFragment extends BaseFragment implements View.OnClickListener {
	private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

	/**
	 * 权限请求值
	 */
	private static final int PERMISSION_REQUEST_CODE = 0;

	///为了使照片竖直显示
	static {
		ORIENTATIONS.append(Surface.ROTATION_0, 90);
		ORIENTATIONS.append(Surface.ROTATION_90, 0);
		ORIENTATIONS.append(Surface.ROTATION_180, 270);
		ORIENTATIONS.append(Surface.ROTATION_270, 180);
	}

	@BindView(R.id.surfaceview)
	SurfaceView mSurfaceView;
	@BindView(R.id.iv_show)
	PhotoView iv_show;
	@BindView(R.id.iv_take_photo)
	ImageView ivTakePicture;

	private SurfaceHolder mSurfaceHolder;

	private CameraManager mCameraManager;//摄像头管理器
	private Handler childHandler, mainHandler;
	private String mCameraID;//摄像头Id 0 为后  1 为前
	private ImageReader mImageReader;
	private CameraCaptureSession mCameraCaptureSession;
	private CameraDevice mCameraDevice;
	private CaptureRequest.Builder previewRequestBuilder;

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_camera;
	}

	@Override
	public void initViews() {
		iv_show = findView(R.id.iv_show);
		ivTakePicture = findView(R.id.iv_take_photo);
		ivTakePicture.setOnClickListener(this);
		//mSurfaceView
		mSurfaceView = findView(R.id.surfaceview);
		mSurfaceView.setOnClickListener(this);


	}

	@Override
	public void initData() {


		initSurfaceView();
	}


	/*设置surfaceview*/
	public void initSurfaceView() {
		setSurfaceViewParams();
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.setKeepScreenOn(true);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		//mSurfaceHolder.setFixedSize(activity.screenWidth, activity.screenHeight-50);// surfaceView 分辨率
		mSurfaceView.setFocusable(true);
		// mSurfaceView添加回调
		mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
			@Override
			public void surfaceCreated(SurfaceHolder holder) { //SurfaceView创建
				// 初始化Camera
				initCamera2();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) { //SurfaceView销毁
				// 释放Camera资源
				if (null != mCameraDevice) {
					mCameraDevice.close();
					mCameraDevice = null;
				}
			}
		});
	}


	/**
	 * 初始化Camera2
	 */

	private void initCamera2() {
		HandlerThread handlerThread = new HandlerThread("Camera2");
		handlerThread.start();
		childHandler = new Handler(handlerThread.getLooper());
		mainHandler = new Handler(getMainLooper());
		mCameraID = "" + CameraCharacteristics.LENS_FACING_FRONT;//后摄像头
		mImageReader = ImageReader.newInstance(1080, 1920, ImageFormat.JPEG, 1);
		mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() { //可以在这里处理拍照得到的临时照片 例如，写入本地
			@Override
			public void onImageAvailable(ImageReader reader) {
				mCameraDevice.close();
				mSurfaceView.setVisibility(View.GONE);
				iv_show.setVisibility(View.VISIBLE);
				// 拿到拍照照片数据
				Image image = reader.acquireNextImage();
				ByteBuffer buffer = image.getPlanes()[0].getBuffer();
				byte[] bytes = new byte[buffer.remaining()];
				buffer.get(bytes);//由缓冲区存入字节数组
				final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
				if (bitmap != null) {

					iv_show.setImageBitmap(bitmap);
				}
			}
		}, mainHandler);
		//获取摄像头管理
		mCameraManager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
		try {
			if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
				return;
			}
			//打开摄像头
			mCameraManager.openCamera(mCameraID, stateCallback, mainHandler);
		} catch (CameraAccessException e) {
			e.printStackTrace();
		}


	}


	/**
	 * 摄像头创建监听
	 */
	private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
		@Override
		public void onOpened(CameraDevice camera) {//打开摄像头
			mCameraDevice = camera;
			//开启预览
			takePreview();
		}

		@Override
		public void onDisconnected(CameraDevice camera) {//关闭摄像头
			if (null != mCameraDevice) {
				mCameraDevice.close();
				mCameraDevice = null;
			}
		}

		@Override
		public void onError(CameraDevice camera, int error) {//发生错误
			ToastUtils.showShortMes(activity, "摄像头开启失败");
		}
	};

	/**
	 * 开始预览
	 */
	private void takePreview() {
		try {
			// 创建预览需要的CaptureRequest.Builder
			previewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
			// 将SurfaceView的surface作为CaptureRequest.Builder的目标
			previewRequestBuilder.addTarget(mSurfaceHolder.getSurface());
			// 创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
			mCameraDevice.createCaptureSession(Arrays.asList(mSurfaceHolder.getSurface(), mImageReader.getSurface()), new CameraCaptureSession.StateCallback() // ③
			{
				@Override
				public void onConfigured(CameraCaptureSession cameraCaptureSession) {
					if (null == mCameraDevice) return;
					// 当摄像头已经准备好时，开始显示预览
					mCameraCaptureSession = cameraCaptureSession;
					try {
						setPreviewParams(previewRequestBuilder);
						// 显示预览
						CaptureRequest previewRequest = previewRequestBuilder.build();
						mCameraCaptureSession.setRepeatingRequest(previewRequest, null, childHandler);
					} catch (CameraAccessException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
					ToastUtils.showShortMes(activity, "配置失败");
				}
			}, childHandler);
		} catch (CameraAccessException e) {
			e.printStackTrace();
		}
	}

	private void updateCameraPreviewSession() throws CameraAccessException {
		if (previewRequestBuilder != null) {

		}
		setPreviewParams(previewRequestBuilder);
		mCameraCaptureSession.setRepeatingRequest(previewRequestBuilder.build(), null, childHandler);
	}

	private void setPreviewParams(CaptureRequest.Builder previewRequestBuilder) {
		// 自动对焦
		previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
		// 打开闪光灯
		previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
		// 获取手机方向
		int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
		// 根据设备方向计算设置照片的方向
		previewRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
	}

	public void setSurfaceViewParams() {
		ViewTreeObserver observer = mSurfaceView.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {

				FrameLayout.LayoutParams surfaceParams = (FrameLayout.LayoutParams) mSurfaceView.getLayoutParams();

				surfaceParams.height = activity.screenHeight;
				surfaceParams.width = activity.screenWidth;
				mSurfaceView.setLayoutParams(surfaceParams);

				mSurfaceView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

			}
		});

	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.surfaceview:
				try {
					updateCameraPreviewSession();
				} catch (CameraAccessException e) {
					e.printStackTrace();
				}
				break;

			case R.id.iv_take_photo:

				takePicture();
				break;

			default:
				break;
		}

	}

	/**
	 * 拍照
	 */
	private void takePicture() {
		if (mCameraDevice == null) return;
		// 创建拍照需要的CaptureRequest.Builder
		final CaptureRequest.Builder captureRequestBuilder;
		try {
			captureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
			// 将imageReader的surface作为CaptureRequest.Builder的目标
			captureRequestBuilder.addTarget(mImageReader.getSurface());
			// 自动对焦
			captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
			// 自动曝光
			captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
			// 获取手机方向
			int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
			// 根据设备方向计算设置照片的方向
			captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
			//拍照
			CaptureRequest mCaptureRequest = captureRequestBuilder.build();
			mCameraCaptureSession.capture(mCaptureRequest, null, childHandler);

		} catch (CameraAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		// 释放Camera资源
		if (null != mCameraDevice) {
			mCameraDevice.close();
			mCameraDevice = null;
		}

	}


}
