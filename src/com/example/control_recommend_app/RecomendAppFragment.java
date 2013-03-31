package com.example.control_recommend_app;

import com.example.base_fourcomics_alarm.MainActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecomendAppFragment extends Fragment {
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         
		MainActivity.theme.setText(getArguments().getString("name"));
        TextView textView = new TextView(getActivity());
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLUE);
        textView.setText(getArguments().getString("name"));
         
        return textView;
    }
     
}
	
	
