package com.apocalypsedefense.app.gameplay_visuals;

import java.util.ArrayList;

import android.util.FloatMath;
import android.util.Log;

import com.apocalypsedefense.core.*;

/**
 * Simplified interface to the core game stuff.
 * @author Pat
 *
 */
public class GameFacade {
    public static final String TAG = "GameFacade";
    /**
	 * Multiply by this to adjust from pixel location to game tile location.
	 */
	public float xScale;
	/**
	 * Multiply by this to adjust from pixel location to game tile location.
	 */
	public float yScale;
	private Game game;
    private boolean isStarted;
	
	/**
	 * @param screenWidth width of view
	 * @param screenHeight height of view
	 * @param tileWidth how many pixels should be considered as one group/block/tile (across)
	 * @param tileHeight how many pixels should be considered as one group/block/tile (down)
	 */
	public GameFacade(int screenWidth, int screenHeight, float tileWidth, float tileHeight){
		xScale = 1 / tileWidth;
		yScale = 1 / tileHeight;
		int width = getGameWidth(screenWidth); 
		int height = getGameHeight(screenHeight); 
		game = new Game(height, width);
	}

	public GameFacade(Game game){
		this.game = game;
	}
	
	public void start(){
		game.startNew();
        this.isStarted = true;
	}

	public void update() {
		//TODO Make sure there's only one way to pause/resume
		if(isStarted() && !isGameOver()) {
			game.update();
		}
	}

    private boolean isGameOver() {
        switch (game.getGameState()) {
            case PAUSED:
            case RUNNING:
                return false;
            case LOST:
            case WON:
                return true;
            default:
                return false;
        }
    }

    public ArrayList<GameObject> getGameObjects() {
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		//TODO: Need to lock these collections while modifying/retrieving
		for (Survivor s : game.survivors){
			objects.add(new GameObject(s, this));
		}
		for (Zombie z : game.zombies){
			objects.add(new GameObject(z, this));
		}
		return objects;
	}

	public boolean isPaused() {
		return game.getGameState() == GameState.PAUSED;
	}

	public boolean isStarted() {
		return this.isStarted;
	}

	public boolean canBuyTowerAt(int screenX, int screenY) {
        //TODO Presumption is that tower was purchasable, so don't check gold here
        if (game.gold < 10) return false; //KLUDGE
        final int gameX = (int) (screenX*xScale);
        final int gameY = (int) (screenY*yScale);
        return game.survivorManager.canBuySurvivorAt(gameX, gameY);
	}

	public void buyTowerAt(int screenX, int screenY) {
		final int gameX = (int) (screenX*xScale);
		final int gameY = (int) (screenY*yScale);
		if (game.survivorManager.canBuySurvivorAt(gameX, gameY)) {
			try {
				game.survivorManager.buySurvivorAt(gameX, gameY);
                game.gold -= 10; //TODO replace with cost obtained from particular tower
			} catch (Exception e) {
				Log.e(TAG, "exception in buySurvivorAt", e);
			}
		}
	}

	private int getGameHeight(int screenHeight) {
		return (int) FloatMath.ceil(screenHeight * yScale);
	}

	private int getGameWidth(int screenWidth) {
		return (int) FloatMath.ceil(screenWidth * xScale);
	}

	public GameData getGameData() {
		Shared.gameData = game.gameData;
        return Shared.gameData;
	}

    public GameState getGameState(){
        return game.getGameState();
    }
}
