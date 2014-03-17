package com.example.recommend;

import android.graphics.Bitmap;

public class RecommendAppData {

	private String app_packagename = null;//パッケージ名ダウンロード済みかどうかを判別
	private String app_name = null;//アプリ名
	private String app_introduction = null;//紹介文
	private String app_imageURL = null;//紹介用のイメージのURL
	private String download_URL = null;//アプリのダウンロード先URL
	private Bitmap app_bitmap = null;//画像
	
	public RecommendAppData(String packageName,String appName,String appIntroduction,String appImageURL,String downloadAppURL){
		setPackageName(packageName);
		setAppName(appName);
		setAppIntroduction(appIntroduction);
		setApp_imageURL(appImageURL);
		setDownLoadURL(downloadAppURL);
		
	}

	// getter

	public String getPackageName() {
		return app_packagename;

	}

	public String getAppName() {
		return app_name;

	}

	public String getAppIntroduction() {
		return app_introduction;

	}

	public Bitmap getBitmap() {
		return app_bitmap;

	}
	
	public String getApp_imageURL() {
		return app_imageURL;
	}

	public String getDownLoadURL() {
		return download_URL;

	}

	// setter

	public void setPackageName(String package_name) {
		this.app_packagename = package_name;

	}

	public void setAppName(String app_name) {
		this.app_name = app_name;
	}

	public void setAppIntroduction(String app_introduction) {
		this.app_introduction = app_introduction;
	}

	public void setBitmap(Bitmap app_bitmap) {
		this.app_bitmap = app_bitmap;
	}
	
	public void setApp_imageURL(String app_imageURL) {
		this.app_imageURL = app_imageURL;
	}

	public void setDownLoadURL(String download_app_URL) {
		this.download_URL = download_app_URL;

	}

}
