package com.example.videofromcamera;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

public class MainActivity extends Activity  {
	
	@Override
	public void onCreate(Bundle inst) {
		super.onCreate(inst);
		Log.i("camera", "onCreate");
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
	}

	@Override
	public void onDestroy() {
		Log.i("camera", "onDestroy");
		super.onDestroy();
	}
	
	@Override
	protected void onStop() {
		Log.i("camera", "onStop");
		super.onStop();
	}
	
}