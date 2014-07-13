package com.example.select_frame;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHelper extends SQLiteOpenHelper {
	static final String DB = "SFdb";
	static final String TABLE = "SFtable";

	public MyDBHelper(Context context) {
		// DBの作成
		// 第一引数：コンテキスト
		// 第二引数：データベースの名前（String型）
		// 第三引数：とりあえず null にしておけば大丈夫だと思います。（CursorFactory型）
		// 第四引数：データベースのバージョン番号（int型）
		super(context, DB, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//_id:行識別番号
		//name:漫画の名前
		//gra1:左上の画像のファイル名
		//gra2:右上…
		//gra3:左下…
		//gra4:右下…
		db.execSQL("CREATE TABLE SFtable (_id INTEGER PRIMARY KEY, name TEXT, gra1 TEXT, gra2 TEXT, gra3 TEXT, gra4 TEXT)");
		long ret;
		//ゆくゆくはwhile文で回すようにしたほうがいい
		ret = db.insert(TABLE, null, insertLine());
		ret = db.insert(TABLE, null, insertLine2());

		if (ret == -1) {
			Log.v("DB insert", "fail");
		} else {
			Log.v("DB insert", "success");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    db.execSQL("DROP TABLE IF EXISTS SFtable");
	    onCreate(db);
	}


	private ContentValues insertLine() {
		ContentValues values = new ContentValues();
		values.put("_id", 1);
		values.put("name", "testcomic");
		values.put("gra1", "image1");
		values.put("gra2", "image2");
		values.put("gra3", "image3");
		values.put("gra4", "image4");
		//values.put("SForder", 2134);
		return values;
	}

	private ContentValues insertLine2() {
		ContentValues values = new ContentValues();
		values.put("_id", 2);
		values.put("name", "testcomic2");
		values.put("gra1", "upperleft");
		values.put("gra2", "upperright");
		values.put("gra3", "lowerleft");
		values.put("gra4", "lowerright");
		//values.put("SForder", 1324);
		return values;
	}
}
