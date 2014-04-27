package com.example.recommend;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.framecomics.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class RecommendAppListLoadTask extends
		AsyncTask<Void, Integer, ArrayList<RecommendAppData>> implements OnCancelListener{

	private static final String TAG = RecommendAppListLoadTask.class
			.getSimpleName();
	private static final String QUANTITY_URL = "";
	private static final String BASE_URL = "";
	private static final String DEV_QUANTITY_URL = "https://dl.dropboxusercontent.com/u/103910219/quan_advertise/kddi/quan_advertisement.json";
	private static final String DEV_BASE_URL = "https://dl.dropboxusercontent.com/u/103910219/quan_advertise/kddi/";

	private static int app_quantity = 0;

	private Context mContext;
	private Activity mActivity;
	private ProgressDialog progressDialog;

	public RecommendAppListLoadTask(Activity root) {
		this.mActivity = root;
		this.mContext = root;
	}

	@Override
	protected void onPreExecute() {
		Log.v(TAG, "onPreExecute()");
		showDialog();
	};

	@Override
	protected ArrayList<RecommendAppData> doInBackground(Void... voids) {

		Log.v(TAG, "doInBackground()");

		ArrayList<RecommendAppData> recommendData;
		if (NetworkCheck.netWorkCheck(this.mContext)) {
			// 非同期通信などの処理をかく
			getQuantitySize();

			recommendData = getRecommendAppList();

			DownloadRecommendAppImage(recommendData);

		} else {
			// 繋がらなかったよ…
			recommendData = null;
		}

		return recommendData;
	}

	@Override
	protected void onPostExecute(ArrayList<RecommendAppData> result) {
		Log.v(TAG, "onPostExecute()");
		if (result == null) {
			progressDialog.dismiss();
			NetworkCheck.netWOrkErroDialog(this.mContext);
			return;
		}

		// リストに以前のものが残っている場合クリアする
		if (RecommendAppList.getInstance().size() != 0)
			RecommendAppList.getInstance().clear();

		for (RecommendAppData data : result) {
			RecommendAppList.getInstance().add(data);
		}

		progressDialog.dismiss();

		if (RecommendAppList.getInstance().size() == 0) {
			Toast.makeText(
					this.mActivity,
					this.mActivity.getResources().getString(
							R.string.recomend_app_nothing), Toast.LENGTH_SHORT)
					.show();
		} else {
			RecommendAppControl.initRecommendAppList(this.mActivity);
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
							this.mContext.getPackageName()))
						recommendData.add(appData);
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

	private void showDialog() {
		Log.v(TAG, "showDialog()");
		progressDialog = new ProgressDialog(mContext);
		// プログレスダイアログのメッセージを設定します
		progressDialog.setMessage("Loading data...");
		// プログレスダイアログのスタイルを円スタイルに設定します
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 progressDialog.setIndeterminate(true);
		progressDialog.show();
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		
	}

}
