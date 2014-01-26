package com.apocalypsedefense.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.apocalypsedefense.core.GameData;
import com.apocalypsedefense.core.Shared;

//import android.support.v4.app.ListFragment;

public class ApocalypseDefenseActivity extends Activity {
	protected static final int OVERWRITE_EXISTING_GAME_DIALOG_ID = 0;
    Boolean _isResumeGameEnabled = true;
    private static final String TAG = "ApocalypseDefenseActivity";

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            if(savedInstanceState.containsKey(Shared.GAME_DATA_01)){
    //            Shared.gameData = (GameData) savedInstanceState.get(Shared.GAME_DATA_01);
                final Object gameData01 = savedInstanceState.get(Shared.GAME_DATA_01);
                Log.d(TAG, "bundle contained: " + gameData01.toString());
            }
        }

		setContentView(R.layout.main);

		// new game screen transition
		View newgamebutton = this.findViewById(R.id.newGame);
		final ApocalypseDefenseActivity thisActivity = this;
		newgamebutton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), NewGameSettingsActivity.class));
				// TODO: Add logic for overwriting-game dialog
//				showDialog(OVERWRITE_EXISTING_GAME_DIALOG_ID);
			}
		});

//		// resume game handler
//		View r = this.findViewById(R.id.resumeGame);
//		r.setEnabled(_isResumeGameEnabled);
//		r.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				// TODO: resume saved game
//				startActivity(new Intent(thisActivity,
//						InGameActivity.class));
//			}
//		});
//
//		// achievements handler
//		View a = this.findViewById(R.id.viewAchievements);
//		a.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				startActivity(new Intent(thisActivity,
//						AchievementsActivity.class));
//			}
//		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case OVERWRITE_EXISTING_GAME_DIALOG_ID:
			return new OverwriteExistingGameDialog(this);
//			LayoutInflater factory = LayoutInflater.from(this);
//			final View xmlLayout = factory.inflate(
//					R.layout.overwrite_existing_game_dialog, null);
//			return new AlertDialog.Builder(ApocalypseDefenseActivity.this)
//					// .setIcon(R.drawable.alert_dialog_icon)
//					// .setTitle(R.string.alert_dialog_two_buttons_msg)
//					// .setMessage("Overwrite message")
//					.setView(xmlLayout)
//					.setPositiveButton("Overwrite", _newGameListener)
//					.setNegativeButton("Keep", null)
////					.setPositiveButton("Overwrite", new
////							DialogInterface.OnClickListener() {
////						public void onClick(DialogInterface dialog, int
////								whichButton) {
////
////							/* User clicked OK so do some stuff */
////						}
////					})
//					// .setNeutralButton(R.string.alert_dialog_something, new
//					// DialogInterface.OnClickListener() {
//					// public void onClick(DialogInterface dialog, int
//					// whichButton) {
//					//
//					// /* User clicked Something so do some stuff */
//					// }
//					// })
//					// .setNegativeButton("Keep", new
//					// DialogInterface.OnClickListener() {
//					// public void onClick(DialogInterface dialog, int
//					// whichButton) {
//					//
//					// /* User clicked Cancel so do some stuff */
//					// }
//					// })
//					.create();
		}
		return null;
	}
}