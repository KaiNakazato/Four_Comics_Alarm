package com.example.control_recommend_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.base_fourcomics_alarm.MainActivity;
import com.example.base_fourcomics_alarm.TabRootFragment;
import com.example.framecomics.R;

public class IntroductionAppFragment extends Fragment{
	
	TextView text;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		MainActivity.back_bt.setVisibility(View.VISIBLE);
        return inflater.inflate(R.layout.introduction_layout, container, false);
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.theme.setText("アプリ説明");
        text = (TextView)getActivity().findViewById(R.id.introduction_text);
        text.setText("アプリ説明");

    }
     
 
}
