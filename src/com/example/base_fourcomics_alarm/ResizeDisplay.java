package com.example.base_fourcomics_alarm;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.FrameLayout.LayoutParams;

public class ResizeDisplay {
	
	private float dispwidth,dispheight,disprate,dispwidthrate,dispheightrate,value;
	private Context root;
	
	
	public ResizeDisplay(Context context){
		root = context;
		WindowManager wm = (WindowManager)root.getSystemService(Context.WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();
        dispwidth = disp.getWidth();
        dispheight = disp.getHeight();
        disprate = 0.0F;
        dispwidthrate = (float)(dispwidth/640.0F);
        dispheightrate = (float)(dispheight/960.0F);
	}
	
	public float resizeDisplay(float size){
		
		if((float)((float)dispheight/(float)dispwidth)<=(float)(960.0F/640.0F)){
//        	dispheightrate = (float)((dispheight-statusBarHeight)/960.0F);
			((Activity) root).getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        	disprate =dispheightrate;
        }else if((float)((float)dispheight/(float)dispwidth)>=(float)(960.0F/640.0F)){
        	disprate = dispwidthrate;
//        	getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
		if(disprate == dispwidthrate){
			value = (float) (size*disprate);
		}else if(disprate == dispheightrate){
			value = (float) (size*disprate);
		}
		
		
		return  value;
		
	}
	
	
}
