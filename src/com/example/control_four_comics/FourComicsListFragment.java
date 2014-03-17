package com.example.control_four_comics;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.base_fourcomics_alarm.MainActivity;
import com.example.framecomics.R;


public class FourComicsListFragment extends Fragment{
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		MainActivity.back_bt.setVisibility(View.VISIBLE);
		return inflater.inflate(R.layout.child_comicslist_layout, container, false);
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.theme.setText("よんこま漫画");
        
        
     // リソースに準備した画像ファイルからBitmapを作成しておく
     		Bitmap image;
     		image = BitmapFactory.decodeResource(getResources(), R.drawable.carrot);

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
     			objects.add(item);
     		}

     		FourComicsListCustomAdapter customComicListAdapter = new FourComicsListCustomAdapter(
     				getActivity(), 0, objects);

     		ListView listView = (ListView) getActivity().findViewById(R.id.list_comics);
     		listView.setAdapter(customComicListAdapter);
    }
}
