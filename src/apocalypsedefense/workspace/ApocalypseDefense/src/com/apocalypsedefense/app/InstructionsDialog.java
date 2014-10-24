package com.apocalypsedefense.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class InstructionsDialog extends Dialog {
	private static final String TAG = "InstructionsDialog";

	public InstructionsDialog(Context context) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "onTouchEvent");
		// Close the dialog when it's touched
		// NOTE: It's also necessary to set every item in the XML file to not be Clickable
		this.dismiss(); 
		return super.onTouchEvent(event);
	}

	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions_dialog);
        
        this.setCanceledOnTouchOutside(true);
    }
}
