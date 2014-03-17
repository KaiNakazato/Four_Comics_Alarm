package com.example.control_four_comics;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framecomics.R;

public class FourComicsListCustomAdapter extends ArrayAdapter<FourComicsData> {
	private LayoutInflater layoutInflater_;

	public FourComicsListCustomAdapter(Context context, int textViewResource,
			List<FourComicsData> objects) {
		super(context, textViewResource, objects);
		layoutInflater_ = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 特定の行（position）のデータを得る
		FourComicsData item = (FourComicsData) getItem(position);

		// converViewは使いまわしされている可能性があるのでnullの時だけ新しく作る
		if (null == convertView) {
			convertView = layoutInflater_.inflate(R.layout.four_comics_column,
					null);
		}

		// ComicDataのデータをViewの各widgetにセットする
		TextView textNumberView;
		textNumberView =(TextView) convertView.findViewById(R.id.comic_number);
		textNumberView.setText(item.getNumberData());

		ImageView imageView;
		imageView =(ImageView) convertView.findViewById(R.id.comic_image);
		imageView.setImageBitmap(item.getImageData());

		TextView textTitleView;
		textTitleView =(TextView) convertView.findViewById(R.id.comic_title);
		textTitleView.setText(item.getTitleData());

		ImageButton buttonView;
		buttonView = (ImageButton) convertView.findViewById(R.id.comic_button);
		buttonView.setImageBitmap(item.getButtonData());

		return convertView;
	}
}
