package com.xmjj.rxretrofit_master.zxing;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.zxing.camera.CameraManager;
import com.xmjj.rxretrofit_master.zxing.decoding.CaptureActivityHandler;
import com.xmjj.rxretrofit_master.zxing.view.ViewfinderView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;


public class CaptureActivity extends Activity implements SurfaceHolder.Callback {
	public static final String QR_RESULT = "RESULT";

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private SurfaceView surfaceView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;

	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	// private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	CameraManager cameraManager;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zxing);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinderview);
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		hasSurface = false;

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		cameraManager = new CameraManager(getApplication());

		viewfinderView.setCameraManager(cameraManager);

		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		cameraManager.closeDriver();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			// CameraManager.get().openDriver(surfaceHolder);
			cameraManager.openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(Result obj, Bitmap barcode) {

		playBeepSoundAndVibrate();
		showResult(obj, barcode);
	}

	private void showResult(Result result, Bitmap barcode) {

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        Drawable drawable = new BitmapDrawable(barcode);
//        builder.setIcon(drawable);
//
//        builder.setTitle("类型:" + rawResult.getBarcodeFormat() + "\n 结果："
//                + rawResult.getText());
//        builder.setPositiveButton("确定", new OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                Intent intent = new Intent();
//                intent.putExtra("result", rawResult.getText());
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//        });
//        builder.setNegativeButton("重新扫描", new OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                restartPreviewAfterDelay(0L);
//            }
//        });
//        builder.setCancelable(false);
//        builder.show();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		if (resultString.equals("")) {
			Toast.makeText(CaptureActivity.this, "Scan failed!",
					Toast.LENGTH_SHORT).show();
		} else {
			Intent resultIntent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("result", resultString);
			// bundle.putParcelable("bitmap", barcode);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			barcode.compress(Bitmap.CompressFormat.PNG, 100, bos);
			byte[] bitmapByte = bos.toByteArray();
			bundle.putByteArray("bitmap", bitmapByte);
			resultIntent.putExtras(bundle);
			this.setResult(RESULT_OK, resultIntent);
			finish();
		}
	}

	public void restartPreviewAfterDelay(long delayMS) {
		if (handler != null) {
			handler.sendEmptyMessageDelayed(MessageIDs.restart_preview, delayMS);
		}
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			try {
				AssetFileDescriptor fileDescriptor = getAssets().openFd(
						"qrbeep.ogg");
				this.mediaPlayer.setDataSource(
						fileDescriptor.getFileDescriptor(),
						fileDescriptor.getStartOffset(),
						fileDescriptor.getLength());
				this.mediaPlayer.setVolume(0.1F, 0.1F);
				this.mediaPlayer.prepare();
			} catch (IOException e) {
				this.mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(RESULT_CANCELED);
			finish();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_FOCUS
				|| keyCode == KeyEvent.KEYCODE_CAMERA) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}