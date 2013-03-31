package com.example.four_comics_alarm.ad;
//package com.example.four_comics_alarm.ad;
//
//import android.app.Activity;
//import android.util.Log;
//import android.view.LayoutInflater;
//
//import com.google.ads.AdSize;
//import com.google.ads.mediation.MediationAdRequest;
//import com.google.ads.mediation.customevent.CustomEventBanner;
//import com.google.ads.mediation.customevent.CustomEventBannerListener;
//
//public class MyAd_imobile implements CustomEventBanner{
//	
//	private final String TAG = "MyAd_imobile";
//
//	private jp.co.imobile.android.AdView imobileAd;
//	
//	private String[] params;
//
//	@Override
//	public void requestBannerAd(CustomEventBannerListener listener, Activity activity,
//			String label, String params, AdSize adSize, MediationAdRequest mar) {
//
//		Log.v(TAG, "requestBannerAd label : " + label + " params : " + params);
//		
//		this.params = params.split(",");
//		
//		int id1 = Integer.parseInt(this.params[0]);
//		int id2 = Integer.parseInt(this.params[1]);
//		
//		Log.v(TAG, "id1 : " + id1 + " id2 : " + id2);
//		
//		//ここで広告Viewを設定
//		imobileAd = jp.co.imobile.android.AdView.create(activity, id1, id2);
//		imobileAd.start();
//		
//		listener.onReceivedAd(imobileAd);
//	}
//}
