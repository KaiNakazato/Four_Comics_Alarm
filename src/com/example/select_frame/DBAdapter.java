package com.example.select_frame;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBAdapter {
	private SQLiteDatabase mydb;
	private String comicName;

	public DBAdapter(String comicName, Activity activity) {
		this.comicName = comicName;
		MyDBHelper hlpr = new MyDBHelper(activity);
		mydb = hlpr.getWritableDatabase();
	}

	public ArrayList<String> getImageURL() {
		ArrayList<String> URLList = new ArrayList<String>();
		Cursor c = mydb.query("SFtable", new String[]{"_id", "name", "gra1", "gra2", "gra3", "gra4", "SForder"}, null, null, null, null, null);
		c.moveToFirst();
		while (true) {
			Log.v("getImage", c.getString(1));
			if (this.comicName.equals(c.getString(1))) {
				for (int j = 2; j < 6; j++) {
					URLList.add(c.getString(j));
				}
				break;
			}
			if(c.isLast()) break;
			else c.moveToNext();
		}
		return URLList;
	}
	public ArrayList<Integer> getFrameOrder() {
		ArrayList<Integer> order = new ArrayList<Integer>();
		Cursor c = mydb.query("SFtable", new String[]{"_id", "name", "gra1", "gra2", "gra3", "gra4", "SForder"}, null, null, null, null, null);
		c.moveToFirst();
		while (true) {
			Log.v("getImage", c.getString(1));
			if (this.comicName.equals(c.getString(1))) {
			    order = getDigits(c.getInt(6));
				break;
			}
			if(c.isLast()) break;
			else c.moveToNext();
		}
		return order;
	}

	private ArrayList<Integer> getDigits(int number) {
		ArrayList<Integer> orderList = new ArrayList<Integer>();
		int tempNum, digiter = 1000;
		for (int i = 0; i < 4; i++) {
			tempNum = (int) Math.floor(number / digiter);
			number = number - (tempNum * digiter);
			digiter = digiter / 10;
			orderList.add(tempNum);
		}
		return orderList;
	}
}
