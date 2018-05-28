package com.example.trafficsignsdetection;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.renderscript.RenderScript;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

public class itemAdapter extends BaseAdapter{

	private List<Sign> listSign;
	private LayoutInflater inflater;
	private Activity activity;
	
	public itemAdapter(List<Sign> listSign, Activity activity) {
		super();
		this.listSign = listSign;
		this.activity = activity;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = view;
		if(view == null){
			v = inflater.inflate(R.layout.list_item, null);
		}
		Sign item = new Sign();
		item = listSign.get(position);
		ImageView iv = (ImageView)v.findViewById(R.id.list_image);
		TextView tvTitle = (TextView)v.findViewById(R.id.list_item_title);
		iv.setImageBitmap(Sign.myMap.get(item.getImage()));
		tvTitle.setText(item.getImage());
		SaveImage(Sign.myMap.get(item.getImage()));
		return v;
	}
	 
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listSign.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	private void SaveImage(Bitmap finalBitmap) {

		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/saved_images");
		myDir.mkdirs();
        Log.v("loc",""+root);
        Random generator = new Random();
		int n = 10000;
		n = generator.nextInt(n);
		String fname = "Image-"+n+".jpg";
		File file = new File (myDir, fname);
		if (file.exists ()) file.delete ();
		try {
			FileOutputStream out = new FileOutputStream(file);
			finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		uploadimage(file);
	}

	public  void uploadimage(File loc){

        AndroidNetworking.upload("http://192.168.43.100/traffic.php")
                .addMultipartFile("image",loc)
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        Log.v("uploading","image");
                    }
                })
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        // do anything with response
                        Log.v("uploaded",""+response);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.v("failed",""+error);
                    }
                });
    }

}
