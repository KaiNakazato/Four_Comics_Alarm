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
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.example.control_alarm.AlarmFragment;
import com.example.control_four_comics.FourComicsListFragment;
import com.example.control_recommend_app.RecomendAppFragment;
import com.example.framecomics.R;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class MainActivity extends FragmentActivity {
	 
	public static TextView theme;
	private FrameLayout mediation;
	private AdView adView;
	private float banner_saize = 100.0f;
	
	private FragmentTabHost host;
	
	private enum TAB {
		ALARM(0,R.drawable.tab_btn_alarm,R.drawable.tab_btn_alarm_background,AlarmFragment.class),
		COMICS_LIST(1,R.drawable.tab_btn_comic_list,R.drawable.tab_btn_comic_list_background,FourComicsListFragment.class),
		RECOMMEND_APP(2,R.drawable.tab_btn_recommendation_app,R.drawable.tab_btn_recommendation_aap_background,RecomendAppFragment.class);

		public int image_resouce_id;
		public int background_resouce_id;
		public Class<?> fragmentClass;
		
		
		private TAB(int index,int image_resouce_id,int background_resouce_id,Class<?> fragmentClass) {
			this.image_resouce_id = image_resouce_id;
			this.background_resouce_id = background_resouce_id;
			this.fragmentClass = fragmentClass;
		}
	}
	
	private ImageButton[] tabButton;
	private TabSpec[] tabSpec;
	private Bundle[] bundle;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.activity_main);
        host = (FragmentTabHost) findViewById(android.R.id.tabhost);
        host.setup(this, getSupportFragmentManager(), R.id.content);
        
        theme = (TextView)findViewById(R.id.theme);
        
        TAB[] tab = TAB.values();
        tabButton = new ImageButton[tab.length];
        
        createView(tab);
        add_tab_fragment(tab);
        
        setOnListener(0);
        
        mediation = (FrameLayout)findViewById(R.id.mediation);
        
        adView = new AdView(this,new AdSize(AdSize.FULL_WIDTH,AdSize.AUTO_HEIGHT), "9e2da437b1654c13"); // mediation
        
//        Log.v("BANNER_HEIGHT","HEIGHT"+String.valueOf(adView.getHeight()));//この時点でまだ広告の大きさが取得できていない
        ResizeDisplay resaze_banner = new ResizeDisplay(this);
        LinearLayout.LayoutParams banner_params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,(int)resaze_banner.resizeDisplay(banner_saize),Gravity.NO_GRAVITY);
        mediation.setLayoutParams(banner_params);
        
        mediation.addView(adView);

        AdRequest adRequest = new AdRequest();
     
        adView.loadAd(adRequest);
    }
    
    private void add_tab_fragment(TAB[] tab){
    	
    	int size = tab.length;
    	
    	tabSpec =  new TabSpec[size];
    	bundle = new Bundle[size];
    	for (int i = 0; i < size; i++){
    		tabSpec[i] = host.newTabSpec("tab"+(i+1));
    		tabSpec[i].setIndicator(tabButton[i]);
    		bundle[i] = new Bundle();
    		/////拡張for文でもっとすっきりまとめられる？？/////tabSpec[i],tabButton[i],bundle[i]は、連想配列のパラメータを使えば良い？？/////逆に煩雑になりそう。/////このままがbetter。
    		if(tab[i] == TAB.ALARM){
    			bundle[i].putString("name", "アラーム設定");//あとで@stringでまとめる
//    			Log.v("LOGよ表示されたまえ", String.valueOf(tab[i]));
    		}else if(tab[i] == TAB.COMICS_LIST){
    			bundle[i].putString("name", "４コマ漫画");//あとで@stringでまとめる
//    			Log.v("LOGよ表示されたまえ", String.valueOf(tab[i]));
    		}else if(tab[i] == TAB.RECOMMEND_APP){
    			bundle[i].putString("name", "オススメアプリ");//あとで@stringでまとめる
//    			Log.v("LOGよ表示されたまえ", String.valueOf(tab[i]));
    		}
    		host.addTab(tabSpec[i], tab[i].fragmentClass, bundle[i]);
    		
    	}
    }
    private void createView(TAB[] tab){
    	
    	int size = tab.length;

		tabButton = new ImageButton[size];
		for(int i=0;i<size;i++){
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
			tabButton[index].setOnTouchListener(new OnTouchListener(){
//				@Override
//				public void onClick(View v) {//OnclickをオーバーライドするとFragmentTabHostが反応しなくなる
//					for (int j = 0; j < size; j++) {
//						tabButton[j].setImageLevel(0);
//					}
//					tabButton[index].setImageLevel(1);
//				}

				@Override
				public boolean onTouch(View v, MotionEvent event) {
				
					for (int j = 0; j < size; j++) {
						tabButton[j].setImageLevel(0);
					}
					tabButton[index].setImageLevel(1);
					return false;
				}
			});
		}
	}
    
    
}