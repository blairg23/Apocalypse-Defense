package com.apocalypsedefense.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ExitConfirmationDialog extends Dialog {
	public ExitConfirmationDialog(Context context) {
		super(context);
	}

	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit_game_dialog);
        
        //TODO: pause game (done in activity, but should it be?)
        
        final ExitConfirmationDialog dialog = this;
        
        // Continue game handler - dismisses dialog (and unpauses game?)
        View continueGame = findViewById(R.id.continue_game);
        continueGame.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				// unpause game? No, that's a bad practice.
			}
		});
        
        // Main menu handler - go to main menu 
        View mainMenu = findViewById(R.id.main_menu_in_exit_dialog);
        mainMenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// maybe manipulate the back stack so it doesn't take us back here
				dialog.dismiss();

				Activity ownerActivity = getOwnerActivity();
				Intent intent = new Intent(ownerActivity,
						ApocalypseDefenseActivity.class);
				// Clear back stack http://stackoverflow.com/questions/5794506/android-clear-the-back-stack#5794572
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Doesn't seem to be necessary
				ownerActivity.startActivity(intent);
			}
		});
        
        // Exit handler - exit app
        View exitApp = findViewById(R.id.exit_game);
        exitApp.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();

				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getOwnerActivity().startActivity(intent);
			}
		});
	}
}
