package com.example.select_frame;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FourComicImageData {
	private SQLiteDatabase db;
	private String comicName; //4コママンガの名称
	private String imageName; //画像のファイル名
	private int orderNumber; //画像の順番(何番目の画像なのか:0-3)

	public FourComicImageData(String comicName, Activity activity, int orderNumber){
		MyDBHelper helper = new MyDBHelper(activity);
		db = helper.getWritableDatabase();
		helper.onUpgrade(db, 0, 0);

		this.comicName = comicName;
		this.orderNumber = orderNumber;
		this.imageName = getImageNamefromDB();
	}

	public String getComicName(){
		return this.comicName;
	}

	public String getImageName(){
		return this.imageName;
	}

	public int getOrderNumber(){
		return this.orderNumber;
	}

	private String getImageNamefromDB(){
		String name = null;
		Cursor c = db.query("SFtable", new String[]{"_id", "name", "gra1", "gra2", "gra3", "gra4"}, null, null, null, null, null);
		c.moveToFirst();
		while (true) {
			//Log.v("getImage", c.getString(1));
			if (this.comicName.equals(c.getString(1))) {
				name = c.getString(orderNumber+2);
				break;
			}
			if(c.isLast()) break;
			else c.moveToNext();
		}
		return name;
	}
}
