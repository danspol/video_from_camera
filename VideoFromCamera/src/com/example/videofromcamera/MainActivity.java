package com.example.videofromcamera;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class MainActivity extends Activity implements SurfaceHolder.Callback {
	/* Activity event handlers */
	// Called when activity is initialised by OS
	private Camera mCam;
	private SurfaceView mCamSV;
	private SurfaceHolder mCamSH;

	@Override
	public void onCreate(Bundle inst) {
		super.onCreate(inst);
		Log.i("camera", "onCreate");
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);

		initCamera();
	}

	// Called when activity is closed by OS
	@Override
	public void onDestroy() {
		Log.i("camera", "onDestroy");
		stopCamera();
		super.onDestroy();
	}

	/* SurfaceHolder event handlers */
	// Called when the surface is first created
	public void surfaceCreated(SurfaceHolder sh) {
		Log.i("camera", "surfaceCreated");
	}

	// Called when surface dimensions etc change
	public void surfaceChanged(SurfaceHolder sh, int format, int width,
			int height) {
		// Start camera preview
		Log.i("camera", "surfaceChanged width=" + width + " height=" + height
				+ " format=" + format);
		startCamera(sh, width, height);
	}

	// Initialise camera and surface
	private void initCamera() {
		Log.i("camera", "initCamera");
		mCamSV = (SurfaceView) findViewById(R.id.surface_camera);
		mCamSH = mCamSV.getHolder();
		mCamSH.addCallback(this);
		mCam = Camera.open();
	}

	private void startCamera(SurfaceHolder sh, int width, int height) {
		Log.i("camera", "startCamera");
		Camera.Parameters parameters = mCam.getParameters();
		
		for (Camera.Size s : parameters.getSupportedPreviewSizes()) {
			parameters.setPreviewSize(s.width, s.height);
			break;
		}

		switch (getResources().getConfiguration().orientation) {
		case 1:
			mCam.setDisplayOrientation(90);
			break;
		case 2:
			mCam.setDisplayOrientation(0);
			break;
		}

		mCam.setParameters(parameters);
		try {
			mCam.setPreviewDisplay(sh);
		} catch (Exception e) {
		}
		mCam.startPreview();
	}

	// Stop camera when application ends
	private void stopCamera() {
		Log.i("camera", "stopCamera");
		mCamSH.removeCallback(this);
		mCam.setPreviewCallback(null);
		mCam.release();
	}

	@Override
	protected void onStop() {
		Log.i("camera", "onStop");
		super.onStop();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i("camera", "surfaceDestroyed");
	}

}