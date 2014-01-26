package com.apocalypsedefense.app;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.apocalypsedefense.app.gameplay_visuals.GameSurfaceView;
import com.apocalypsedefense.app.gameplay_visuals.OnGameEndListener;
import com.apocalypsedefense.app.gameplay_visuals.OnStatsChangedListener;
import com.apocalypsedefense.core.GameData;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.apocalypsedefense.core.GameState;
import com.apocalypsedefense.core.Shared;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class InGameActivity extends Activity {
	private static final String TAG = "InGameActivity";
    private static final int EXIT_CONFIRMATION_DIALOG = 0;
    private static final int END_GAME_DIALOG = 1;
    private static final int INSTRUCTIONS_DIALOG = 2;
    private GameSurfaceView gameView;
    private static final int GAME_STATS_CHANGED = 1;
    private volatile GameData gameStats;
    private ImageView pauseImage;
    private volatile boolean isPaused;
    private Drawable pauseDrawable;
    private Drawable playDrawable;
    private GameState gameState;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(Shared.GAME_DATA_01)){
                final Object gameData01 = savedInstanceState.get(Shared.GAME_DATA_01);
                Log.d(TAG, "bundle contained: "+gameData01.toString());
            }
        }

        setContentView(R.layout.in_game);

        // Not sure if we should hold on to resources like this (maybe use WeakReference?)
        pauseDrawable = getResources().getDrawable(R.drawable.pause);
        playDrawable = getResources().getDrawable(R.drawable.play);

		gameView = (GameSurfaceView) findViewById(R.id.surfaceView1);
		
		// Make the game surface be on top and transparent (to underlying map image)
		// NOTE: I tried to call this code in the GameSurfaceView at various points,
		// and it always seemed to cause an exception (usually NullPointer) --PCK
		gameView.setZOrderOnTop(true);
		gameView.getHolder().setFormat(PixelFormat.TRANSPARENT);

        // Pause button
        pauseImage = (ImageView) findViewById(R.id.pause_image);
        pauseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePause();
            }
        });

        // Set up code to handle updating stats (in gutter)
        final TextView cashView = (TextView) findViewById(R.id.cash_display);
		final TextView zombieKillView = (TextView) findViewById(R.id.zombies_killed_display);
		final TextView waveCounterView = (TextView) findViewById(R.id.wave_counter);
		
		final Handler statsHandler = new Handler() {
	          public void  handleMessage(Message msg) {
	        	  cashView.setText(String.format("$%d", gameStats.gold));
                  zombieKillView.setText(
                          String.format("%d", gameStats.zombieKillCount));
                  waveCounterView.setText(
                          String.format("Wave %d/100", gameStats.wave));
	          }
	    };
	    
		gameView.setOnStatsChangedListener(new OnStatsChangedListener() {
            @Override
			public void onStatsChanged(GameData stats) {
				Log.d("OnStatsChangedListener", "stats received in callback in InGameActivity");
                gameStats = stats;
                statsHandler.sendEmptyMessage(GAME_STATS_CHANGED);
			}
		});

        // Set up to handle game end
        // Transition to game end stats display
        final InGameActivity thisActivity = this;
        final Handler gameEndHandler = new Handler() {
            public void handleMessage(Message msg){
                showDialog(END_GAME_DIALOG);
//                //TODO: make sure back button in next activity goes to main menu, not here
//                startActivity(new Intent(thisActivity, EndOfGameActivity.class));
            }
        };

        gameView.setOnGameEndListener(new OnGameEndListener() {
            @Override
            public void onGameEnd(GameState state) {
                Log.d("OnGameEndListener", "callback for game end with gamestate: "+state);
                //TODO: somehow pass the game state to the new activity
                gameState = state;
                gameEndHandler.sendEmptyMessage(0);
            }
        });
        
        // Show game instructions dialog
        showDialog(INSTRUCTIONS_DIALOG);
	}

    private void togglePause() {
        if (isPaused){
            onResume();
        }
        else {
            onPause();
        }
    }

    @Override
	protected void onResume() {
        Log.d(TAG, "onResume called");
        isPaused = false;
        pauseImage.setImageDrawable(pauseDrawable);
		super.onResume();
		gameView.onResume();
        FileInputStream in = null;
        try{
            in = openFileInput("apocalypse_defense_game_state.bin");
            Shared.gameData = GameData.load(in);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "persistence file not found", e);
        }
        finally {
            try{
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException e) {
                Log.d(TAG, "persistence input error", e);
            }
        }
    }

	@Override
	protected void onPause() {
        Log.d(TAG, "onPause called");
        isPaused = true;
        pauseImage.setImageDrawable(playDrawable);
		super.onPause();
		gameView.onPause();
        FileOutputStream out = null;
        try{
            // I'm not sure what mode is best, but need to consider retaining the file if the user uninstalls and re-installs or upgrades
            out = openFileOutput("apocalypse_defense_game_state.bin", MODE_WORLD_READABLE);
            Shared.gameData.persist(out);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "persistence file not found", e);
        }
        finally {
        	try{
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException e) {
                Log.d(TAG, "persistence output error", e);
            }
        }
        System.gc(); // Perform garbage collection while we're paused
	}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState == null){
            outState = new Bundle();
        }
        outState.putString(Shared.GAME_DATA_01, "put in from InGameActivity's onSaveInstanceState");
//        outState.putSerializable(Shared.GAME_DATA_01, gameStats);
    }

    @Override
	public void onBackPressed() {
        onPause();
        showDialog(EXIT_CONFIRMATION_DIALOG);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id){
		case INSTRUCTIONS_DIALOG:
			return new InstructionsDialog(this);
		case EXIT_CONFIRMATION_DIALOG:
			return new ExitConfirmationDialog(this);
        case END_GAME_DIALOG:
            return new EndOfGameDialog(this);
        }
        return super.onCreateDialog(id);
	}
}
