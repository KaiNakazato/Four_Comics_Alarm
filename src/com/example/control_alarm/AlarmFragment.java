package com.example.control_alarm;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.ToggleButton;

import com.example.base_fourcomics_alarm.MainActivity;
import com.example.drum_picker.TimeDrumPicker;
import com.example.framecomics.R;

public class AlarmFragment extends Fragment {
	private static final String TAG = AlarmFragment.class.getSimpleName();
	private TimeDrumPicker tPicker;
	private CheckBox snooze_check, vibration_check;
	private ToggleButton alarm_ON_OFF_bt;
	private SeekBar volume_bar;
	private ImageView[] image;

	private enum SELECT_IMAGE {
		CORN(0, R.id.image_corn), TOMATO(1, R.id.image_tomato), EGG_PLANT(2,
				R.id.image_eggplant), CARROT(3, R.id.image_carrot);

		public int index;
		public int resource_id;

		private SELECT_IMAGE(int index, int resource_id) {
			this.index = index;
			this.resource_id = resource_id;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MainActivity.back_bt.setVisibility(View.GONE);
		return inflater.inflate(R.layout.tab_alarm_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		MainActivity.theme.setText("アラーム設定");

		init_drumpicker();
		init_snooze();
		init_vibration();
		init_ON_OFF_bt();
		init_volumebar();
		init_selectImage();

	}

	private void init_drumpicker() {

		tPicker = (TimeDrumPicker) getActivity().findViewById(R.id.timepicker);
		tPicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				// プリファレンスに状態を保存
				new AlarmPreference(getActivity()).editPreferenceTime(
						AlarmPreferenceDefinition.HOUR_KEY, hourOfDay);
				new AlarmPreference(getActivity()).editPreferenceTime(
						AlarmPreferenceDefinition.MINUTE_KEY, minute);
			}
		});

		int time_hour = new AlarmPreference(getActivity())
				.getPreferenceTime(AlarmPreferenceDefinition.HOUR_KEY);
		int time_minute = new AlarmPreference(getActivity())
				.getPreferenceTime(AlarmPreferenceDefinition.MINUTE_KEY);

		if ((time_hour == -1) && (time_minute == -1)) {
			Calendar calendar = Calendar.getInstance();
			time_hour = calendar.get(Calendar.HOUR_OF_DAY);
			time_minute = calendar.get(Calendar.MINUTE);
		}

		tPicker.setHour(time_hour);
		tPicker.setMinitue(time_minute);

		Log.v(TAG, "timepicker:"+"hight:"+tPicker.getWidth()+"width:"+tPicker.getWidth());
	}

	private void init_volumebar() {
		if (!(volume_bar instanceof SeekBar)) {
			volume_bar = (SeekBar) getActivity().findViewById(R.id.volumeBar);
			volume_bar
					.setProgress(new AlarmPreference(getActivity())
							.getPreferenceVolume(AlarmPreferenceDefinition.ALARM_VOLUME_KEY));
			volume_bar
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
						// トラッキング開始時に呼び出されます
						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {
							Log.v("onStartTrackingTouch()",
									String.valueOf(seekBar.getProgress()));
						}

						// トラッキング中に呼び出されます
						@Override
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromTouch) {
							Log.v("onProgressChanged()",
									String.valueOf(progress) + ", "
											+ String.valueOf(fromTouch));
						}

						// トラッキング終了時に呼び出されます
						@Override
						public void onStopTrackingTouch(SeekBar seekBar) {
							Log.v("onStopTrackingTouch()",
									String.valueOf(seekBar.getProgress()));
							new AlarmPreference(getActivity())
									.editPreferenceVolume(
											AlarmPreferenceDefinition.ALARM_VOLUME_KEY,
											seekBar.getProgress());
						}
					});
		}
	}

	private void init_snooze() {
		if (!(snooze_check instanceof CheckBox)) {
			snooze_check = (CheckBox) getView().findViewById(
					R.id.snoozeCheckBox);
			snooze_check.setChecked(new AlarmPreference(getActivity())
					.getPreferenceBool(AlarmPreferenceDefinition.SNOOZE_KEY));
			snooze_check
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// プリファレンスに状態を保存
							new AlarmPreference(getActivity())
									.editPreferenceBool(
											AlarmPreferenceDefinition.SNOOZE_KEY,
											isChecked);
							if (isChecked) {

							} else {

							}
						}
					});
		}

	}

	private void init_vibration() {
		if (!(vibration_check instanceof CheckBox)) {
			vibration_check = (CheckBox) getView().findViewById(
					R.id.vibrationCheckBox);
			vibration_check
					.setChecked(new AlarmPreference(getActivity())
							.getPreferenceBool(AlarmPreferenceDefinition.VIBRATION_KEY));
			vibration_check
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// プリファレンスに状態を保存
							new AlarmPreference(getActivity())
									.editPreferenceBool(
											AlarmPreferenceDefinition.VIBRATION_KEY,
											isChecked);
							if (isChecked) {

							} else {

							}
						}
					});
		}

	}

	private void init_ON_OFF_bt() {
		if (!(alarm_ON_OFF_bt instanceof ToggleButton)) {
			alarm_ON_OFF_bt = (ToggleButton) getView().findViewById(
					R.id.alarmSwich);
			alarm_ON_OFF_bt
					.setChecked(new AlarmPreference(getActivity())
							.getPreferenceBool(AlarmPreferenceDefinition.ALARM_SWITCH_KEY));
			alarm_ON_OFF_bt
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// プリファレンスに状態を保存
							new AlarmPreference(getActivity())
									.editPreferenceBool(
											AlarmPreferenceDefinition.ALARM_SWITCH_KEY,
											isChecked);
							if (isChecked) {

							} else {

							}
						}
					});
		}
	}

	private void init_selectImage() {

		image = new ImageView[SELECT_IMAGE.values().length];

		for (final SELECT_IMAGE _id : SELECT_IMAGE.values()) {
			image[_id.index] = (ImageView) getActivity().findViewById(
					_id.resource_id);

			image[_id.index].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new AlarmPreference(getActivity())
							.editPreferenceSelectCharacter(
									AlarmPreferenceDefinition.SELECT_CHARACTER_KEY,
									_id.index);
					for (int i = 0; i < SELECT_IMAGE.values().length; i++) {
						image[i].setImageLevel(0);
					}
					image[_id.index].setImageLevel(1);
				}
			});
		}
		switch (new AlarmPreference(getActivity())
				.getPreferenceSelectCharacter(AlarmPreferenceDefinition.SELECT_CHARACTER_KEY)) {
		case -1:
			new AlarmPreference(getActivity()).editPreferenceSelectCharacter(
					AlarmPreferenceDefinition.SELECT_CHARACTER_KEY, 0);
			image[0].setImageLevel(1);
			break;
		case 0:
			image[0].setImageLevel(1);
			break;
		case 1:
			image[1].setImageLevel(1);
			break;
		case 2:
			image[2].setImageLevel(1);
			break;
		case 3:
			image[3].setImageLevel(1);
			break;
		default:
			break;
		}
	}

}
