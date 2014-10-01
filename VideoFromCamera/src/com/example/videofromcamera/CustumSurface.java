package com.example.videofromcamera;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.widget.VideoView;

public class CustumSurface extends VideoView implements SurfaceHolder.Callback {

	private Camera camera;
	private Camera.Parameters parameters;

	public CustumSurface(Context context) {
		super(context);
		initCamera();
	}

	public CustumSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		initCamera();
	}

	public CustumSurface(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initCamera();
	}

	private void initCamera() {
		Log.i("camera", "initCamera");
		this.getHolder().addCallback(this);

		camera = Camera.open();
	}

	private void startCamera(SurfaceHolder sh, int width, int height) {
		Log.i("camera", "startCamera");

		switch (getResources().getConfiguration().orientation) {
		case 1:
			camera.setDisplayOrientation(90);
			break;
		case 2:
			camera.setDisplayOrientation(0);
			break;
		}

		camera.setParameters(parameters);
		try {
			camera.setPreviewDisplay(sh);
		} catch (Exception e) {
			System.err.println(e);
		}
		camera.startPreview();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		Display display = getDisplay();

		Log.i("Camera", "on measure width=" + display.getWidth() + " heigth="
				+ display.getHeight());
		int w = display.getWidth();
		int h = display.getHeight();

		parameters = camera.getParameters();
		List<Size> sizes = parameters.getSupportedPictureSizes();
		for (int i = 0; i < sizes.size(); i++) {
			Log.i("Camera", "Camera = width=" + sizes.get(i).width + " heigth="
					+ sizes.get(i).height);
		}
		int w2 = sizes.get(0).width;
		int h2 = sizes.get(0).height;
		// this.setMeasuredDimension(sizes.get(0).height, sizes.get(0).width);
		parameters.setPictureSize(sizes.get(0).width, sizes.get(0).height);
		Log.i("Camera", "Camera = width=" + sizes.get(0).width + " heigth="
				+ sizes.get(0).height + " Layout 	=" + getWidth() + " heigth="
				+ getHeight());

		switch (getResources().getConfiguration().orientation) {
		case 1: {
			Log.i("Camera", "Case 1");
			int rh = (int) h / 2;
			int rw = (int) (rh *  h2/w2);
			this.setMeasuredDimension(rw, rh);
		}
			break;
		case 2: {

			int rw = (int) w / 2;
			int rh = (int) (rw * h2 / w2);
			this.setMeasuredDimension(rw, rh);
		}
			break;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		startCamera(arg0, arg1, arg2);
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		arg0.removeCallback(this);
		camera.setPreviewCallback(null);
		camera.release();
	}

	@Override
	public void draw(Canvas canvas) {
		// this.setScaleX(1.7f);
		// this.setScaleY(1.9f);
		// this.setPivotX(2.5f);
		this.setWillNotDraw(false);
		Log.i("Camera", "canvas width=" + canvas.getWidth() + " heigth="
				+ canvas.getHeight());

		super.draw(canvas);
		Log.i("Camera", "draw");
		Paint pen = new Paint();
		canvas.drawLine(0, 0, 100, 100, pen);

	}

	@Override
	public void onDraw(Canvas canvas) {
		Log.i("Camera", "onDraw");
		// super.onDraw(canvas);
		// Paint pen = new Paint();
		// canvas.drawLine(0, 0, 100, 100, pen);
	}

}
