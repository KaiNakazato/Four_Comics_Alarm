package com.example.recommend;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;

public class RecommendAppControl {

	private Activity rootActivity;
	
//	public static ProgressDialog progressDialog;

	/**
	 * オススメするためのカスタマイズダイアログの表示周り
	 **/

	private static RecommendAppOneDialog recommend_app_one = null;
	private static RecommendAppListDialog recommend_app_list = null;
	public static final int RECOMMEND_APP_ONE = 0, RECOMMEND_APP_LIST = 1;

	public RecommendAppControl(Activity root) {
		this.rootActivity = root;
	}

	public void showDialogRecommendApp(int separate_diarog) {
		switch (separate_diarog) {
		case RECOMMEND_APP_ONE:
			RecommendAppOneLoadTask task_one = new RecommendAppOneLoadTask(this.rootActivity);
        	task_one.execute();
			break;
		case RECOMMEND_APP_LIST:
			RecommendAppListLoadTask task_list = new RecommendAppListLoadTask(this.rootActivity);
        	task_list.execute();
			break;
		}

	}

	public static void initRecommendAppOne(Activity root,RecommendAppData data) {
		recommend_app_one = new RecommendAppOneDialog(root,data);
		showDialog(onCreateAppOneDialog());
	}

	public static void initRecommendAppList(Activity root) {
		recommend_app_list = new RecommendAppListDialog(root);
		showDialog(onCreateAppListDialog());
	}

	private static Dialog onCreateAppOneDialog() {
		return recommend_app_one.createMenu();
	}
	
	private static Dialog onCreateAppListDialog() {
		return recommend_app_list.createMenu();
	}
	
	private static void showDialog(Dialog dialog) {
		dialog.show();
	}
	

}
