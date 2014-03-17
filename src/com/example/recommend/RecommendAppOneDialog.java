package com.example.recommend;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.example.framecomics.R;

public class RecommendAppOneDialog{
	
	private static String TAG = RecommendAppOneDialog.class.getSimpleName();
	
	private Activity parentActivity;
	private Dialog dialog;
	private RecommendAppData data;
	
	public RecommendAppOneDialog(Activity parent,RecommendAppData data) {
		this.parentActivity = parent;
		this.data = data;
	}
	
	
	public Dialog createMenu() {

		dialog = new Dialog(parentActivity,R.style.recommend_dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.recommend_app_one_dialog);
		dialog.setCancelable(true);
		ImageView recommend_app_one = (ImageView)dialog.findViewById(R.id.recommend_app_one);
		recommend_app_one.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse(data.getDownLoadURL());
				Intent intent = new Intent(Intent.ACTION_VIEW,uri);
				parentActivity.startActivity(intent);
			}
		});
		ImageView close_button = (ImageView)dialog.findViewById(R.id.close_recommend_button);
		close_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				
			}
		});
		Log.v(TAG, "hight" + this.data.getBitmap().getHeight());
		Log.v(TAG, "hight" + this.data.getBitmap().getWidth());
		recommend_app_one.setImageBitmap(this.data.getBitmap());

        return dialog;

	}
	

}
