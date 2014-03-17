package com.example.control_alarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AlarmPreference {
	
	
	private static final String TAG = AlarmPreference.class.getSimpleName();
	
	private static final String PREFERENCE_TAG = "ALARM_PREFERENCE";
	
	private SharedPreferences alarm_preference;
	private Editor editor;
	
	public AlarmPreference(Context context){
		alarm_preference = context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE);
	}
	
	public void editPreferenceBool(String key,boolean bool){
		editor = alarm_preference.edit();
		editor.putBoolean(key, bool);
		editor.commit();
	}
	
	public void editPreferenceTime(String key,int time){
		editor = alarm_preference.edit();
		editor.putInt(key, time);
		editor.commit();
	}
	
	public void editPreferenceVolume(String key,int volume){
		editor = alarm_preference.edit();
		editor.putInt(key, volume);
		editor.commit();
	}
	
	public void editPreferenceSelectCharacter(String key,int select_num){
		editor = alarm_preference.edit();
		editor.putInt(key, select_num);
		editor.commit();
	}
	
	public boolean getPreferenceBool(String key){
		return alarm_preference.getBoolean(key, false);
	}
	
	public int getPreferenceTime(String key){
		return alarm_preference.getInt(key, -1);
	}
	
	public int getPreferenceVolume(String key){
		return alarm_preference.getInt(key, 100);
	}
	
	public int getPreferenceSelectCharacter(String key){
		return alarm_preference.getInt(key, -1);
	}
	
}
