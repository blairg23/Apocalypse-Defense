package com.apocalypsedefense.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OverwriteExistingGameDialog extends Dialog {
	public OverwriteExistingGameDialog(Context context) {
		super(context);
	}

	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overwrite_existing_game_dialog);
        
        final OverwriteExistingGameDialog dialog = this;
        
        // Overwrite game handler - starts new game
        View overwriteButton = findViewById(R.id.overwrite_game);
        overwriteButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				Activity ownerActivity = getOwnerActivity();
				Intent intent = new Intent(ownerActivity,
						NewGameSettingsActivity.class);
				ownerActivity.startActivity(intent);
			}
		});
        
        // Keep game handler - dismisses dialog is all
        View keepGame = findViewById(R.id.keep_game);
        keepGame.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}
}
