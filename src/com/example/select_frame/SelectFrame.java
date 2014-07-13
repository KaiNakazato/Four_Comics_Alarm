package com.example.select_frame;

import java.util.ArrayList;
import java.util.Collections;

import com.example.framecomics.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SelectFrame extends Activity implements OnTouchListener {

	// 最後にタッチされた座標
	private float startTouchX;
	private float startTouchY;

	// 現在タッチ中の座標
	private float nowTouchedX;
	private float nowTouchedY;

	// フリックの遊び部分（最低限移動しないといけない距離）
	private float adjust = 50;

	// 画面幅
	private int height, width;

	// 画像倍率の設定(1.0なら画面横幅と同じ)
	private double magnification = 0.85;

	private ScrollView sv;
	private HorizontalScrollView hsv;
	private ImageView upperleft, upperright, lowerleft, lowerright;
	private TableLayout tl;
	private TextView upperleftnumber, upperrightnumber, lowerleftnumber,
			lowerrightnumber;
	private boolean UpperLeftSelectFlag, UpperRightSelectFlag,
			LowerLeftSelectFlag, LowerRightSelectFlag;
	private int touchordernumber;
	private String[] OrderSetText = {"①", "②", "③", "④"};
	private ArrayList<String> SelectOrder = new ArrayList<String>();
	private static final String UPPERLEFT = "upperleft";
	private static final String UPPERRIGHT = "upperright";
	private static final String LOWERLEFT = "lowerleft";
	private static final String LOWERRIGHT = "lowerright";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_frame_layout);
		//startActivity(new Intent(this,DBAdapter.class));
		InitFlickGraphic();
		SetListeners();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		width = hsv.getWidth();
		height = hsv.getHeight();
		tl.setPadding((int) (width - width * magnification) / 2,
				(int) (height - width * magnification) / 2,
				(int) (width - width * magnification) / 2,
				(int) (height - width * magnification) / 2);
		ResizeImageView(upperleft);
		ResizeImageView(upperright);
		ResizeImageView(lowerleft);
		ResizeImageView(lowerright);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event_) {
		// フリックされたかどうかを示すフラグ
		int FlickFlag = 0;

		// タッチイベント
		switch (event_.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startTouchX = event_.getRawX();
			startTouchY = event_.getRawY();
			break;

		// タッチが離れた
		case MotionEvent.ACTION_UP:
			nowTouchedX = event_.getRawX();
			nowTouchedY = event_.getRawY();

			// フリック判定
			if (startTouchY > nowTouchedY) {
				if (startTouchX > nowTouchedX) {
					if ((startTouchY - nowTouchedY) > (startTouchX - nowTouchedX)) {
						if (startTouchY > nowTouchedY + adjust) {
							// 上フリック時の処理を記述する
							sv.fullScroll(ScrollView.FOCUS_DOWN);
							FlickFlag = 1;
						}
					} else if ((startTouchY - nowTouchedY) < (startTouchX - nowTouchedX)) {
						if (startTouchX > nowTouchedX + adjust) {
							// 左フリック時の処理を記述する
							hsv.fullScroll(ScrollView.FOCUS_RIGHT);
							FlickFlag = 1;
						}
					}
				} else if (startTouchX < nowTouchedX) {
					if ((startTouchY - nowTouchedY) > (nowTouchedX - startTouchX)) {
						if (startTouchY > nowTouchedY + adjust) {
							// 上フリック時の処理を記述する
							sv.fullScroll(ScrollView.FOCUS_DOWN);
							FlickFlag = 1;
						}
					} else if ((startTouchY - nowTouchedY) < (nowTouchedX - startTouchX)) {
						if (startTouchX < nowTouchedX + adjust) {
							// 右フリック時の処理を記述する
							hsv.fullScroll(ScrollView.FOCUS_LEFT);
							FlickFlag = 1;
						}
					}
				}
			} else if (startTouchY < nowTouchedY) {
				if (startTouchX > nowTouchedX) {
					if ((nowTouchedY - startTouchY) > (startTouchX - nowTouchedX)) {
						if (startTouchY < nowTouchedY + adjust) {
							// 下フリック時の処理を記述する
							sv.fullScroll(ScrollView.FOCUS_UP);
							FlickFlag = 1;
						}
					} else if ((nowTouchedY - startTouchY) < (startTouchX - nowTouchedX)) {
						if (startTouchX > nowTouchedX + adjust) {
							// 左フリック時の処理を記述する
							hsv.fullScroll(ScrollView.FOCUS_RIGHT);
							FlickFlag = 1;
						}
					}
				} else if (startTouchX < nowTouchedX) {
					if ((nowTouchedY - startTouchY) > (nowTouchedX - startTouchX)) {
						if (startTouchY < nowTouchedY + adjust) {
							// 下フリック時の処理を記述する
							sv.fullScroll(ScrollView.FOCUS_UP);
							FlickFlag = 1;
						}
					} else if ((nowTouchedY - startTouchY) < (nowTouchedX - startTouchX)) {
						if (startTouchX < nowTouchedX + adjust) {
							// 右フリック時の処理を記述する
							hsv.fullScroll(ScrollView.FOCUS_LEFT);
							FlickFlag = 1;
						}
					}
				}
			}
			// フリック判定されてなれば、画像のタッチ判定を行う
			if (FlickFlag != 1) {
				switch (v.getId()) {
				case R.id.upperleft:
					// 左上の画像をタッチ時の処理s
					if (UpperLeftSelectFlag == false) {
						SelectOrder.add(UPPERLEFT);

						upperleftnumber.setText(OrderSetText[touchordernumber]);
						touchordernumber++;
						upperleftnumber.setVisibility(View.VISIBLE);
						upperleft.setColorFilter(Color.argb(100, 0, 0, 0));
						UpperLeftSelectFlag = true;
					} else {
						SelectOrder.remove(SelectOrder.indexOf(UPPERLEFT));

						touchordernumber--;
						upperleft.setColorFilter(Color.TRANSPARENT);
						upperleftnumber.setVisibility(View.INVISIBLE);
						UpperLeftSelectFlag = false;
					}
					break;
				case R.id.upperright:
					// 右上の画像をタッチ時の処理
					if (UpperRightSelectFlag == false) {
						SelectOrder.add(UPPERRIGHT);

						upperrightnumber
								.setText(OrderSetText[touchordernumber]);
						touchordernumber++;
						upperrightnumber.setVisibility(View.VISIBLE);
						upperright.setColorFilter(Color.argb(100, 0, 0, 0));
						UpperRightSelectFlag = true;
					} else {
						SelectOrder.remove(SelectOrder.indexOf(UPPERRIGHT));

						touchordernumber--;
						upperrightnumber.setVisibility(View.INVISIBLE);
						upperright.setColorFilter(Color.TRANSPARENT);
						UpperRightSelectFlag = false;
					}
					break;
				case R.id.lowerleft:
					// 左下の画像をタッチ時の処理
					if (LowerLeftSelectFlag == false) {
						SelectOrder.add(LOWERLEFT);

						lowerleftnumber.setText(OrderSetText[touchordernumber]);
						touchordernumber++;
						lowerleftnumber.setVisibility(View.VISIBLE);
						lowerleft.setColorFilter(Color.argb(100, 0, 0, 0));
						LowerLeftSelectFlag = true;
					} else {
						SelectOrder.remove(SelectOrder.indexOf(LOWERLEFT));

						touchordernumber--;
						lowerleftnumber.setVisibility(View.INVISIBLE);
						lowerleft.setColorFilter(Color.TRANSPARENT);
						LowerLeftSelectFlag = false;
					}
					break;
				case R.id.lowerright:
					// 右下の画像をタッチ時の処理
					if (LowerRightSelectFlag == false) {
						SelectOrder.add(LOWERRIGHT);

						lowerrightnumber
								.setText(OrderSetText[touchordernumber]);
						touchordernumber++;
						lowerrightnumber.setVisibility(View.VISIBLE);
						lowerright.setColorFilter(Color.argb(100, 0, 0, 0));
						LowerRightSelectFlag = true;
					} else {
						SelectOrder.remove(SelectOrder.indexOf(LOWERRIGHT));

						touchordernumber--;
						lowerright.setColorFilter(Color.TRANSPARENT);
						lowerrightnumber.setVisibility(View.INVISIBLE);
						LowerRightSelectFlag = false;
					}
					break;
				}
				for (int i = 0; i < SelectOrder.size(); i++) {
					Log.v("ordernumber", "" + SelectOrder.get(i));
					String CurrentFrame = SelectOrder.get(i);
					if (CurrentFrame.equals(UPPERLEFT))
						upperleftnumber.setText(OrderSetText[i]);
					else if (CurrentFrame.equals(UPPERRIGHT))
						upperrightnumber.setText(OrderSetText[i]);
					else if (CurrentFrame.equals(LOWERLEFT))
						lowerleftnumber.setText(OrderSetText[i]);
					else if (CurrentFrame.equals(LOWERRIGHT))
						lowerrightnumber.setText(OrderSetText[i]);
				}

				// すべて選択したら確定&順番の確認処理に移る
				if (SelectOrder.size() == 4) {
					for (int i = 0; i < SelectOrder.size(); i++) {
						String CurrentFrame = SelectOrder.get(i);
						if (CurrentFrame.equals(UPPERLEFT))
							upperleftnumber.setTag(i);
						else if (CurrentFrame.equals(UPPERRIGHT))
							upperrightnumber.setTag(i);
						else if (CurrentFrame.equals(LOWERLEFT))
							lowerleftnumber.setTag(i);
						else if (CurrentFrame.equals(LOWERRIGHT))
							lowerrightnumber.setTag(i);
					}
					CheckOrder();
				}

				Log.v("ordernumber", "end");
			}
			break;
		}
		return true;
	}

	public void InitFlickGraphic() {
		hsv = (HorizontalScrollView) findViewById(R.id.hsv);
		sv = (ScrollView) findViewById(R.id.sv);
		upperleft = (ImageView) findViewById(R.id.upperleft);
		upperright = (ImageView) findViewById(R.id.upperright);
		lowerleft = (ImageView) findViewById(R.id.lowerleft);
		lowerright = (ImageView) findViewById(R.id.lowerright);
		tl = (TableLayout) findViewById(R.id.tl);
		upperleftnumber = (TextView) findViewById(R.id.upperleftnumber);
		upperrightnumber = (TextView) findViewById(R.id.upperrightnumber);
		lowerleftnumber = (TextView) findViewById(R.id.lowerleftnumber);
		lowerrightnumber = (TextView) findViewById(R.id.lowerrightnumber);

		//データベース問い合わせ
//		DBAdapter adptr = new DBAdapter("testcomic2", this);
//		ArrayList<Integer> list = adptr.getFrameOrder();
//		ArrayList<String> url = adptr.getImageURL();
//		upperleft.setTag(list.get(0));
//		upperright.setTag(list.get(1));
//		lowerleft.setTag(list.get(2));
//		lowerright.setTag(list.get(3));
//		for(int i =0; i<list.size(); i++) Log.v("hoge",url.get(i)+","+list.get(i));
		PictureManagement pm = new PictureManagement("testcomic");
//		for(int i=0;i<4;i++) Log.v("beforeRamdom", pm.getImageData(i).getImageURL() + "," + pm.getImageData(i).getOrderNumber());
		pm.ramdomizeOrder();
//		for(int i=0;i<4;i++) Log.v("afterRamdom", pm.getImageData(i).getImageURL() + "," + pm.getImageData(i).getOrderNumber());

		int resId = getResources().getIdentifier(pm.getImageData(0).getImageName(), "drawable", getPackageName());
		upperleft.setImageResource(resId);
		resId = getResources().getIdentifier(pm.getImageData(1).getImageName(), "drawable", getPackageName());
		upperright.setImageResource(resId);
		resId = getResources().getIdentifier(pm.getImageData(2).getImageName(), "drawable", getPackageName());
		lowerleft.setImageResource(resId);
		resId = getResources().getIdentifier(pm.getImageData(3).getImageName(), "drawable", getPackageName());
		lowerright.setImageResource(resId);

		upperleft.setTag(pm.getImageData(0).getOrderNumber());
		upperright.setTag(pm.getImageData(1).getOrderNumber());
		lowerleft.setTag(pm.getImageData(2).getOrderNumber());
		lowerright.setTag(pm.getImageData(3).getOrderNumber());

		// 正解の順番の設定
//		upperleft.setTag(1);
//		upperright.setTag(2);
//		lowerleft.setTag(3);
//		lowerright.setTag(4);
	}

	public void SetListeners() {
		sv.setOnTouchListener(this);
		hsv.setOnTouchListener(this);
		upperleft.setOnTouchListener(this);
		upperright.setOnTouchListener(this);
		lowerleft.setOnTouchListener(this);
		lowerright.setOnTouchListener(this);
	}

	public void ResizeImageView(ImageView iv) {
		Bitmap bmp = ((BitmapDrawable) iv.getDrawable()).getBitmap();
		bmp = Bitmap.createScaledBitmap(bmp, (int) (width * magnification),
				(int) (width * magnification), false);
		iv.setImageBitmap(bmp);
	}

	public void InitSelect() {
		UpperLeftSelectFlag = false;
		UpperRightSelectFlag = false;
		LowerLeftSelectFlag = false;
		LowerRightSelectFlag = false;
		touchordernumber = 0;
		upperleftnumber.setVisibility(View.INVISIBLE);
		upperrightnumber.setVisibility(View.INVISIBLE);
		lowerleftnumber.setVisibility(View.INVISIBLE);
		lowerrightnumber.setVisibility(View.INVISIBLE);
		upperleft.setColorFilter(Color.TRANSPARENT);
		upperright.setColorFilter(Color.TRANSPARENT);
		lowerleft.setColorFilter(Color.TRANSPARENT);
		lowerright.setColorFilter(Color.TRANSPARENT);
		SelectOrder.clear();
	}

	public void CheckOrder() {
		int correct = 0;
		if (upperleft.getTag().equals(upperleftnumber.getTag()))
			correct++;
		if (upperright.getTag().equals(upperrightnumber.getTag()))
			correct++;
		if (lowerleft.getTag().equals(lowerleftnumber.getTag()))
			correct++;
		if (lowerright.getTag().equals(lowerrightnumber.getTag()))
			correct++;

		Log.v("correct", "" + correct);

		if (correct == 4) {
			Toast.makeText(this, "correct!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "incorrect...", Toast.LENGTH_SHORT).show();
		}
		InitSelect();
	}

	//漫画を操作するクラス
	class PictureManagement{
		 private String comicName;
		ArrayList<FourComicImageData> comicArray = new ArrayList<FourComicImageData>();

		public PictureManagement(String comicName){
			this.comicName = comicName;
			for(int i=0;i<4;i++){
				comicArray.add(new FourComicImageData(comicName, SelectFrame.this, i));
			}
		}

		public FourComicImageData getImageData(int index){
			return this.comicArray.get(index);
		}

		public void ramdomizeOrder(){
			Collections.shuffle(comicArray);
		}
	}
}