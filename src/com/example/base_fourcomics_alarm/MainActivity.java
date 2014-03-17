package com.example.base_fourcomics_alarm;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.example.control_alarm.AlarmFragment;
import com.example.control_four_comics.FourComicsSelectFragment;
import com.example.control_recommend_app.IntroductionAndRecomendAppFragment;
import com.example.framecomics.R;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class MainActivity extends FragmentActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
	
	public static TextView theme;
	public static Button back_bt;
	private FrameLayout mediation;
	private AdView adView;
	private float banner_saize = 100.0f;

	private FragmentTabHost mTabHost;

	private enum TAB {
		ALARM(0, R.drawable.tab_btn_alarm, R.drawable.tab_btn_alarm_background,
				AlarmFragment.class), 
		COMICS_LIST(1,R.drawable.tab_btn_comic_list,R.drawable.tab_btn_comic_list_background,
				FourComicsSelectFragment.class), 
		RECOMMEND_APP(2,R.drawable.tab_btn_recommendation_app,R.drawable.tab_btn_recommendation_aap_background,
				IntroductionAndRecomendAppFragment.class);

		/**
		 * フィールドの宣言
		 **/
		public int image_resouce_id;
		public int background_resouce_id;
		public Class<?> fragmentClass;

		/**
		 * コンストラクタの宣言
		 **/
		private TAB(int index, int image_resouce_id, int background_resouce_id,
				Class<?> fragmentClass) {
			this.image_resouce_id = image_resouce_id;
			this.background_resouce_id = background_resouce_id;
			this.fragmentClass = fragmentClass;
		}
	}

	private TAB[] tab;
	private ImageButton[] tabButton;
	private TabSpec[] tabSpec;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		/**
		 * hedearのセットアップ
		 */
		theme = (TextView) findViewById(R.id.theme);
		
		back_bt = (Button) findViewById(R.id.header_back_bt);
		back_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getCurrentFragment().popBackStack();
			}
		});

		/**
		 * タブのセットアップ
		 */
		tab = TAB.values();
		initTabs(tab);

		// 最初にフォーカスをAlarmタブにフォーカスを当てる
		setOnListener(0);

		/**
		 * 広告周り
		 */
		mediation = (FrameLayout) findViewById(R.id.mediation);
		adView = new AdView(this, new AdSize(AdSize.FULL_WIDTH,
				AdSize.AUTO_HEIGHT), "9e2da437b1654c13"); // mediation

		// 画面サイズに合わせる
		ResizeDisplay resaze_banner = new ResizeDisplay(this);
		LinearLayout.LayoutParams banner_params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				(int) resaze_banner.resizeDisplay(banner_saize),
				Gravity.NO_GRAVITY);

		mediation.setLayoutParams(banner_params);
		mediation.addView(adView);
		AdRequest adRequest = new AdRequest();
		adView.loadAd(adRequest);
	}

	/**
	 * タブ初期化
	 */
	private void initTabs(TAB[] tab) {

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		tabButton = new ImageButton[tab.length];
		createView(tab);
		add_tab_fragment(tab);

		mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				// タブ切り替え時にBackStackをクリア
				getCurrentFragment().clearBackStack();
			}
		});
	}

	private void add_tab_fragment(TAB[] tab) {

		int size = tab.length;

		tabSpec = new TabSpec[size];
		Bundle[] args = new Bundle[size];
		// bundle = new Bundle[size];
		for (int i = 0; i < size; i++) {
			tabSpec[i] = mTabHost.newTabSpec("tab" + (i + 1)).setIndicator(
					"tab" + (i + 1));
			tabSpec[i].setIndicator(tabButton[i]);
			args[i] = new Bundle();
			// ///拡張for文でもっとすっきりまとめられる？？/////tabSpec[i],tabButton[i],bundle[i]は、連想配列のパラメータを使えば良い？？/////逆に煩雑になりそう。/////このままがbetter。
			if (tab[i] == TAB.ALARM) {
				args[i].putString("root", TAB.ALARM.fragmentClass.getName());
			} else if (tab[i] == TAB.COMICS_LIST) {
				args[i].putString("root",
						TAB.COMICS_LIST.fragmentClass.getName());
			} else if (tab[i] == TAB.RECOMMEND_APP) {
				args[i].putString("root",
						TAB.RECOMMEND_APP.fragmentClass.getName());
			}
			mTabHost.addTab(tabSpec[i], TabRootFragment.class, args[i]);

		}
	}

	private void createView(TAB[] tab) {

		int size = tab.length;

		tabButton = new ImageButton[size];
		for (int i = 0; i < size; i++) {
			tabButton[i] = new ImageButton(this);
			tabButton[i].setImageResource(tab[i].image_resouce_id);
			tabButton[i].setBackgroundResource(tab[i].background_resouce_id);
		}
	}

	/**
	 * リスナを設定
	 */
	private void setOnListener(int first_forcus) {
		final int size = tabButton.length;
		tabButton[first_forcus].setImageLevel(1);
		for (int i = 0; i < size; i++) {
			final int index = i;
			//onClickだとtabHostに影響がでてFragmentの切り替えが上手くいかない。
			//onTouchだと、クリックと判定されない動作のときにタブの画像が切り替えられてしまう。
			tabButton[index].setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {

					
					switch(event.getAction()){
					case MotionEvent.ACTION_DOWN:
						Log.v(TAG, "Down:"+event.getAction());
						break;
					case MotionEvent.ACTION_MOVE:
						Log.v(TAG, "Move:"+event.getAction());
						break;
					case MotionEvent.ACTION_UP:
						Log.v(TAG, "Up:"+event.getAction());
						for (int j = 0; j < size; j++) {
							tabButton[j].setImageLevel(0);
						}
						tabButton[index].setImageLevel(1);
						break;
					case MotionEvent.ACTION_CANCEL:
						Log.v(TAG, "Cancel:"+event.getAction());
						break;
					}
					
					return false;
				}
			});
		}
	}

	private TabRootFragment getCurrentFragment() {
		return (TabRootFragment) getSupportFragmentManager().findFragmentById(
				R.id.realtabcontent);
	}

	@Override
	public void onBackPressed() {
		if (!getCurrentFragment().popBackStack()) {
			// タブ内FragmentのBackStackがない場合は終了
			super.onBackPressed();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mTabHost = null;
	}
	
	/**
	 *　fragmentのレイアウトが広告部分とかぶるので調整している。
	 *　広告の高さ分だけ余白を設けている。
	 * @param layout
	 */
	public void setPaddingBottom(LinearLayout layout){
		layout.setPadding(layout.getPaddingLeft(), layout.getPaddingTop(), layout.getPaddingRight(), mediation.getHeight());
	}
	
    public void setPaddingBottom(FrameLayout layout){
    	layout.setPadding(layout.getPaddingLeft(), layout.getPaddingTop(), layout.getPaddingRight(), mediation.getHeight());
	}
    
    public void setPaddingBottom(RelativeLayout layout){
    	layout.setPadding(layout.getPaddingLeft(), layout.getPaddingTop(), layout.getPaddingRight(), mediation.getHeight());
	}
	
	
}