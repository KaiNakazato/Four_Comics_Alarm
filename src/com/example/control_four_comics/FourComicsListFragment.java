package com.example.control_four_comics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.base_fourcomics_alarm.MainActivity;
import com.example.framecomics.R;

public class FourComicsListFragment extends Fragment {
	TextView text;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		MainActivity.back_bt.setVisibility(View.GONE);
		return inflater.inflate(R.layout.tab_comics_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		MainActivity.theme.setText("よんこま漫画");
		text = (TextView) getActivity().findViewById(R.id.comics_text);
		text.setText("よんこま漫画");
	}

}
