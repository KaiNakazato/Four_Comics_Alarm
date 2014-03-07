package com.example.control_alarm;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.example.base_fourcomics_alarm.MainActivity;
import com.example.drum_picker.TimeDrumPicker;
import com.example.framecomics.R;

public class AlarmFragment extends  Fragment{
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		MainActivity.back_bt.setVisibility(View.GONE);
		
		return inflater.inflate(R.layout.tab_alarm_layout, container, false);
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainActivity.theme.setText("アラーム設定");
        
        Calendar calendar = Calendar.getInstance();
		final int hour = calendar.get(Calendar.HOUR_OF_DAY);
		final int minitue = calendar.get(Calendar.MINUTE);
		final TimeDrumPicker tPicker = (TimeDrumPicker) getActivity().findViewById(R.id.timepicker);
		tPicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				
			}
		});
		
		tPicker.setHour(hour);
		tPicker.setMinitue(minitue);
		
    }

}
