package com.example.recommend;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class RecommendAppOneLoadTask extends
		AsyncTask<Void, Integer, ArrayList<RecommendAppData>> {

	private Context mContext;
	private Activity mActivity;
	private SharedPreferences preferences;
	SharedPreferences.Editor editor;

	private static final String TAG = RecommendAppOneLoadTask.class
			.getSimpleName();
	private static final String PREFERENCE_TAG = "recommend_app_preference";
	private static final String KEY_ALARM_TIME = "recommend_alarm_time";
	private static final String KEY_RECOMMEND_FLAG = "recommend_flag";
	private static final String KEY_RECOMMEND_NUM = "recommend_num";
	private static final String QUANTITY_URL = "";
	private static final String BASE_URL = "";
	private static final String DEV_QUANTITY_URL = "https://dl.dropboxusercontent.com/u/103910219/quan_advertise/kddi/quan_advertisement.json";
	private static final String DEV_BASE_URL = "https://dl.dropboxusercontent.com/u/103910219/quan_advertise/kddi/";

	private static int app_quantity = 0;

	public RecommendAppOneLoadTask(Activity root) {
		this.mActivity = root;
		this.mContext = root;
	}

	@Override
	protected void onPreExecute() {
		Log.v(TAG, "onPreExecute()");
		getAlermTime();
	};

	@Override
	protected ArrayList<RecommendAppData> doInBackground(Void... params) {
		Log.v(TAG, "doInBackground()");
		ArrayList<RecommendAppData> recommendData;
		if (preferences.getBoolean(KEY_RECOMMEND_FLAG, false)) {

			if (NetworkCheck.netWorkCheck(this.mContext)) {
				// 非同期通信などの処理をかく
				getQuantitySize();

				recommendData = getRecommendAppList();

				DownloadRecommendAppImage(recommendData);

			} else {
				// 繋がらなかったよ…
				recommendData = null;
			}
		} else {
			recommendData = null;
		}

		return recommendData;
	}

	@Override
	protected void onPostExecute(ArrayList<RecommendAppData> result) {
		Log.v(TAG, "onPostExecute()");
		if (result == null) {
			return;
		}

		// リストに以前のものが残っている場合クリアする
		if (RecommendAppList.getInstance().size() != 0)
			RecommendAppList.getInstance().clear();

		for (RecommendAppData data : result) {
			RecommendAppList.getInstance().add(data);
		}

		if (RecommendAppList.getInstance().size() == 0) {
			return;
		} else {
			editor = preferences.edit();
			if (preferences.getInt(KEY_RECOMMEND_NUM, -1) == -1) {
				editor.putInt(KEY_RECOMMEND_NUM, 1);
			} else if (preferences.getInt(KEY_RECOMMEND_NUM, -1) < RecommendAppList
					.getInstance().size()) {
				editor.putInt(KEY_RECOMMEND_NUM,
						preferences.getInt(KEY_RECOMMEND_NUM, -1) + 1);
			} else if (preferences.getInt(KEY_RECOMMEND_NUM, -1) >= RecommendAppList
					.getInstance().size()) {
				editor.putInt(KEY_RECOMMEND_NUM, 1);
			} else {
				editor.putInt(KEY_RECOMMEND_NUM, -1);
			}
			editor.commit();
			if (preferences.getInt(KEY_RECOMMEND_NUM, -1) != -1)
				RecommendAppControl.initRecommendAppOne(
						this.mActivity,
						RecommendAppList.getInstance().get(
								preferences.getInt(KEY_RECOMMEND_NUM, -1) - 1));
		}

	}

	private void getQuantitySize() {
		try {
			app_quantity = new JSONObject(
					DownlodRecommendAppjson.getJsonString(DEV_QUANTITY_URL))
					.getInt("advetizement_quantity");
		} catch (JSONException e) {
			app_quantity = -1;
			e.printStackTrace();
		}catch(NullPointerException e){
			app_quantity = -1;
			e.printStackTrace();
		}
	}

	private ArrayList<RecommendAppData> getRecommendAppList() {
		ArrayList<String> recommendAppJsonStrings;
		recommendAppJsonStrings = new ArrayList<String>();
		for (int i = 1; i <= app_quantity; i++) {
			recommendAppJsonStrings
					.add(DownlodRecommendAppjson.getJsonString(DEV_BASE_URL
							+ "advertisement" + i + ".json"));
		}
		ArrayList<RecommendAppData> recommendData = new ArrayList<RecommendAppData>();
		// Log.v(TAG, "" + recommendAppJsonStrings.size());
		for (int i = 0; i < recommendAppJsonStrings.size(); i++) {
			if (recommendAppJsonStrings.get(i) != null) {
				Log.v(TAG, recommendAppJsonStrings.get(i).toString());
				try {
					RecommendAppData appData = new Gson().fromJson(
							recommendAppJsonStrings.get(i),
							RecommendAppData.class);
					if (!appData.getPackageName().equals(
							this.mContext.getPackageName())) {
						// 検索するアプリの条件をIntentで指定する
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_MAIN);
						intent.addCategory(Intent.CATEGORY_LAUNCHER);

						// PackageManagerを使ってアプリの情報を取得
						PackageManager pm = this.mActivity.getPackageManager();
						List<ResolveInfo> list = pm.queryIntentActivities(
								intent, 0);

						boolean install_flag = false;
						String appName = null;
						String packageName = null;
						for (ResolveInfo item : list) {
							// アプリの名前、パッケージ名、クラス名（Activity名）を取得する
							appName = item.loadLabel(pm).toString();
							packageName = item.activityInfo.packageName;
							if (appData.getAppName().equals(appName)
									|| appData.getPackageName().equals(
											packageName)) {
								install_flag = true;
								Log.d("debug", appName + " : " + packageName);
							}
						}
						if (!install_flag)
							recommendData.add(appData);
					}
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}catch(NullPointerException e){
					e.printStackTrace();
				}
			}
		}
		return recommendData;
	}

	private void DownloadRecommendAppImage(
			ArrayList<RecommendAppData> recommendData) {
		URL url = null;
		InputStream istream = null;
		for (int i = 0; i < recommendData.size(); i++) {
			try {
				url = new URL(recommendData.get(i).getApp_imageURL());
				istream = url.openStream();
				recommendData.get(i).setBitmap(
						BitmapFactory.decodeStream(istream));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean getAlermTime() {
		preferences = mContext.getSharedPreferences(PREFERENCE_TAG, 0);
		editor = preferences.edit();

		/*
		 * 前回のアラーム時間を取得
		 */
		boolean recommend_flag = preferences.getBoolean(KEY_RECOMMEND_FLAG,
				false);
		long lastAlarmTime = preferences.getLong(KEY_ALARM_TIME, -1);
		long nowAlarmTime = lastAlarmTime;
		long now = System.currentTimeMillis();

		Log.v(TAG, "last:" + lastAlarmTime + "now:" + now);

		if (lastAlarmTime == -1) {
			editor.putBoolean(KEY_RECOMMEND_FLAG, false);
			nowAlarmTime = getTimeWithinDay();
		} else if (lastAlarmTime > now) {
			editor.putBoolean(KEY_RECOMMEND_FLAG, false);
		} else if (lastAlarmTime < now) {
			editor.putBoolean(KEY_RECOMMEND_FLAG, true);
			nowAlarmTime = getTimeWithinDay();
		}

		/*
		 * 今回のアラーム時間を保存
		 */
		editor.putLong(KEY_ALARM_TIME, nowAlarmTime);
		editor.commit();

		return recommend_flag;
	}

	/**
	 * 現在時刻から一日以内の時間を返す
	 * 
	 * @return
	 */
	private long getTimeWithinDay() {
		long now = System.currentTimeMillis();
		long randomAddTimeWithinDay = (long) (86400000 * Math.random());
		return now + randomAddTimeWithinDay;
	}

}
