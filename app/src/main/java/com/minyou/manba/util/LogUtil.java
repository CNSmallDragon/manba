package com.minyou.manba.util;

import android.util.Log;

public class LogUtil {

	private static boolean isOpen = true;
	
	public static void d(String tag,String msg){
		if(isOpen){
			Log.d(tag, msg);
		}
	}
	
	public static void i(String tag,String msg){
		if(isOpen){
			Log.i(tag, msg);
		}
	}
	
	public static void w(String tag,String msg){
		if(isOpen){
			Log.w(tag, msg);
		}
	}
	
	public static void e(String tag,String msg){
		if(isOpen){
			Log.e(tag, msg);
		}
	}
}
