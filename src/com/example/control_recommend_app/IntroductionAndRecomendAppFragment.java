package com.example.control_recommend_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.base_fourcomics_alarm.MainActivity;
import com.example.control_alarm.AlarmFragment;
import com.example.framecomics.R;

public class IntroductionAndRecomendAppFragment extends Fragment {
	
	TextView text;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.tab_introduction_recomenndapp_layout, container, false);
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainActivity.theme.setText("アプリ説明＆おすすめアプリ");
        text = (TextView)getActivity().findViewById(R.id.introduction_recommndapp_text);
        text.setText("アプリ説明＆おすすめアプリ");
        
        Button button = (Button) getActivity().findViewById(R.id.next);
        button.setText("次へ");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	changeView();
            }
        });
    }
	
	private void changeView() {
		FragmentTransaction fragmentTransaction = getFragmentManager()
				.beginTransaction();
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.replace(R.id.fragment, new IntroductionAppFragment());
		fragmentTransaction.commit();
	}
     
}
	
	
