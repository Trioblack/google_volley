package com.wordpress.felipenipo;

import android.app.Application;

public class GoogleVolleyApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		NetworkQueue.getInstance().init(this);
	}
	
}
