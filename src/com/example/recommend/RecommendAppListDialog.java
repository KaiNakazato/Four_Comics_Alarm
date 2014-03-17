package com.example.recommend;


import android.app.Activity;
import android.app.Dialog;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framecomics.R;

public class RecommendAppListDialog {

	private Activity parentActivity;
	private Dialog dialog;
	private ViewPager recommend_app_list;
	private TextView contents_number;

	private RecommendAppListPagerAdapter recommend_app_adapter;

	public RecommendAppListDialog(Activity parent) {
		this.parentActivity = parent;
	}

	public Dialog createMenu() {

		dialog = new Dialog(parentActivity, R.style.recommend_dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.recommend_app_list_dialog);

		recommend_app_list = (ViewPager) dialog
				.findViewById(R.id.recommend_app_list);
		recommend_app_list.setAdapter(setAdapter(this.parentActivity));

		ImageView arrow_left = (ImageView) dialog.findViewById(R.id.arrow_left);
		arrow_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ViewPager viewPager = (ViewPager) dialog
						.findViewById(R.id.recommend_app_list);
				viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
			}
		});

		// 広告のページのページめくり(次へ)
		ImageView arrow_right = (ImageView) dialog
				.findViewById(R.id.arrow_right);
		arrow_right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ViewPager viewPager = (ViewPager) dialog
						.findViewById(R.id.recommend_app_list);
				viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
			}
		});

		// 現ページ/広告数の表示
		contents_number = (TextView) dialog
				.findViewById(R.id.recommend_app_contents);

		contents_number
				.setText(String.valueOf(recommend_app_list.getCurrentItem() + 1)
						+ "/" + recommend_app_adapter.getCount());

		// ページの切り替わる度に処理を行う
		recommend_app_list.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				if (state == ViewPager.SCROLL_STATE_SETTLING) {
					int page = recommend_app_list.getCurrentItem();
					contents_number.setText(recommend_app_list.getCurrentItem()
							+ "/" + recommend_app_list.getChildCount());

					contents_number.setText(String.valueOf(page + 1) + "/"
							+ recommend_app_adapter.getCount());
				}
			}
		});

		return dialog;

	}

	private RecommendAppListPagerAdapter setAdapter(Activity root) {

		if (RecommendAppList.getInstance().size() == 0) {
			return null;
		} else {
			return recommend_app_adapter = new RecommendAppListPagerAdapter(
					root, RecommendAppList.getInstance());
		}
	}

}
