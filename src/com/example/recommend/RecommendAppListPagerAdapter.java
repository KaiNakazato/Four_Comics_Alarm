package com.example.recommend;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class RecommendAppListPagerAdapter extends PagerAdapter {

	private Context mContext;
	private ArrayList<RecommendAppData> recomennd_app_List;

	RecommendAppListPagerAdapter(Context context,
			ArrayList<RecommendAppData> list) {
		this.mContext = context;
		this.recomennd_app_List = list;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// リストから取得
		final RecommendAppData item = this.recomennd_app_List.get(position);

		// View を生成
		ImageView recommend_image_view = new ImageView(mContext);
		recommend_image_view.setImageBitmap(item.getBitmap());
		recommend_image_view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String activity_name = null;
				
				activity_name = isInstallApp(item);
				
				if(activity_name != null){
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.setClassName(item.getPackageName(), activity_name);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
					mContext.startActivity(intent);

				}else{
					Uri uri = Uri.parse(item.getDownLoadURL());
					Intent intent_url = new Intent(Intent.ACTION_VIEW, uri);
					mContext.startActivity(intent_url);
				}
				
			}
		});

		// コンテナに追加
		container.addView(recommend_image_view);

		return recommend_image_view;
	}
	
	
	private String isInstallApp(RecommendAppData item){
		// 検索するアプリの条件をIntentで指定する
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);

		// PackageManagerを使ってアプリの情報を取得
		PackageManager pm = mContext.getPackageManager();
		List<ResolveInfo> list = pm.queryIntentActivities(
				intent, 0);

//		boolean install_flag = false;
		String appName = null;
		String packageName = null;
		String mainActhivityName = null;
		
		for (ResolveInfo _item : list) {
			// アプリの名前、パッケージ名、クラス名（Activity名）を取得する
			appName = _item.loadLabel(pm).toString();
			packageName = _item.activityInfo.packageName;
			
			
			if (item.getAppName().equals(appName)
					|| item.getPackageName().equals(
							packageName)) {
//				install_flag = true;
				mainActhivityName = _item.activityInfo.name;
				Log.d("debug", appName + " : " + packageName + " : " + mainActhivityName);
			}
		}
		return mainActhivityName;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// コンテナから View を削除
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		// TODO 自動生成されたメソッド・スタブ
		return this.recomennd_app_List.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO 自動生成されたメソッド・スタブ
		return arg0 == (ImageView) arg1;
	}

}
