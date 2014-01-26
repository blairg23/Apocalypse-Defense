package com.apocalypsedefense.app.gameplay_visuals;

import android.util.Log;
import com.apocalypsedefense.core.LogAdapter;

public class AndroidLogAdapter implements LogAdapter {

	public void d(String tag, String msg) {
		Log.d(tag, msg);
	}

}
