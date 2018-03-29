package com.test.hamsters;

import android.app.Application;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppApplication extends Application {

	private static AppApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		initStyles();
	}

	public static AppApplication getInstance() {
		return instance;
	}

	private void initStyles() {
		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
				.setDefaultFontPath("fonts/OpenSans-Regular.ttf")
				.setFontAttrId(R.attr.fontPath)
				.build()
		);
	}
}