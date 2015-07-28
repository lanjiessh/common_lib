package com.lanjie.lib.utils;

import android.app.Application;

import com.lanjie.lib.utils.JLog;

public class App extends Application {

	private static App sInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		initAppSetting();
	}

	private void initAppSetting(){
		JLog.isDebug = false;  //是否打印日志
	}
	
	public static synchronized App getInstance() {
		return sInstance;
	}


	
	
	
	
	
	
}
