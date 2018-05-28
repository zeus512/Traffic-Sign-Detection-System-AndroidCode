package com.example.trafficsignsdetection;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private Button btRuntime;
	private Button btPickPhoto;
	private Button btTakePhoto;
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
			android.Manifest.permission.READ_EXTERNAL_STORAGE,
			android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.CAMERA

	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Initialize();
		btRuntime.setOnClickListener(this);
		btTakePhoto.setOnClickListener(this);
		verifyStoragePermissions(MainActivity.this);
		btPickPhoto.setOnClickListener(this);
	}
	public void Initialize(){
		btRuntime = (Button)findViewById(R.id.btRuntime);
		btPickPhoto = (Button)findViewById(R.id.btPickPhoto);
		btTakePhoto = (Button)findViewById(R.id.btTakePhoto);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btRuntime:
			Intent runtimeIntent = new Intent(MainActivity.this, CameraActivity.class);
			startActivity(runtimeIntent);
			return;
		case R.id.btTakePhoto:
//			Intent takePhotoIntent = new Intent(MainActivity.this, TakePhotoActivity.class);
//			startActivity(takePhotoIntent);
			return;
		case R.id.btPickPhoto:
			Intent pickPhotoIntent = new Intent(MainActivity.this, PhotoActivity.class);
			startActivity(pickPhotoIntent);
			break;
		default:
			break;
		}
	}

	public static void verifyStoragePermissions(Activity activity) {
		int writePermission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
		int readPermission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE);
		int writePermission1 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
		int readPermission1 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

		if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED || writePermission1 != PackageManager.PERMISSION_GRANTED || readPermission1 != PackageManager.PERMISSION_GRANTED ) {
			ActivityCompat.requestPermissions(
					activity,
					PERMISSIONS_STORAGE,
					REQUEST_EXTERNAL_STORAGE
			);
		}

	}

}
