package com.example.control_four_comics;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.base_fourcomics_alarm.MainActivity;
import com.example.framecomics.R;

public class FourComicsListFragment extends Fragment {
	final int CORN = 0;
	final int TOMATO = 1;
	final int EGG_PLANT = 2;
	final int CARROT = 3;

	private int selected_idx;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MainActivity.back_bt.setVisibility(View.VISIBLE);
		return inflater.inflate(R.layout.child_comicslist_layout, container,
				false);
	}

	/**
	 * @author toyozumi
	 * @param savedInstanceState
	 * R.id...で画像が読み込め舐めなかったので、assetを使った方法に変更。
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		MainActivity.theme.setText("よんこま漫画");

		// リソースに準備した画像ファイルからBitmapを作成しておく
		Bitmap image = null;

		switch (selected_idx) {
		case CORN:
			image = loadBitmapAsset("corn.png",getActivity());
			break;
		case TOMATO:
			image = loadBitmapAsset("tomato.png",getActivity());
			break;
		case EGG_PLANT:
			image = loadBitmapAsset("eggplant.png",getActivity());
			break;
		case CARROT:
			image = loadBitmapAsset("carrot.png",getActivity());
			break;
		}
		Bitmap button;
		button = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);

		// データの作成
		List<FourComicsData> objects = new ArrayList<FourComicsData>();

		for (int i = 1; i < 15; i++) {
			FourComicsData item = new FourComicsData();
			item.setNumberData(String.format("No.%02d", i));
			item.setImageData(image);
			item.setTitleData(i + "つ目！");
			item.setButtonData(button);
			if (i < 3) {
				item.setFlagFilter(false);
			} else {
				item.setFlagFilter(true);
			}
			objects.add(item);
		}

		FourComicsListCustomAdapter customComicListAdapter = new FourComicsListCustomAdapter(
				getActivity(), 0, objects);

		ListView listView = (ListView) getActivity().findViewById(
				R.id.list_comics);
		listView.setAdapter(customComicListAdapter);
	}

	public void setSelectedIndex(int idx) {
		selected_idx = idx;
	}

	public static Bitmap loadBitmapAsset(String fileName, Context context) {
		final AssetManager assetManager = context.getAssets();
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(assetManager.open(fileName));
			return BitmapFactory.decodeStream(bis);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
