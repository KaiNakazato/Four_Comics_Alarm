package com.example.control_alarm;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.base_fourcomics_alarm.MainActivity;
import com.example.framecomics.R;

public class AlarmFragment extends  Fragment{
	
	TextView text;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.tab_alarm_layout, container, false);
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainActivity.theme.setText("アラーム設定");
        text = (TextView)getActivity().findViewById(R.id.alarm_text);
        text.setText("アラーム設定");
    }
	
	/**
     * タブ内でFragment変更.
     */
//    private void changeView() {
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.replace(R.id.fragment, new AlarmFragment());
//        fragmentTransaction.commit();
//    }

}
