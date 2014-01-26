package com.apocalypsedefense.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EndOfGameDialog extends Dialog {
    public EndOfGameDialog(Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_game_scoreboard);

        final Dialog dialog = this;

        // Main menu handler - go to main menu 
        View mainMenu = findViewById(R.id.main_menu_in_end_game);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // maybe manipulate the back stack so it doesn't take us back here
                dialog.dismiss();

                Activity ownerActivity = getOwnerActivity();
                Intent intent = new Intent(ownerActivity,
                        ApocalypseDefenseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ownerActivity.startActivity(intent);
            }
        });
    }
}
