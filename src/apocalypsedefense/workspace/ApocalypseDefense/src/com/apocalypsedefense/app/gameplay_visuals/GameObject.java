package com.apocalypsedefense.app.gameplay_visuals;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.apocalypsedefense.app.NewGameSettingsActivity;
import com.apocalypsedefense.core.Person;
import com.apocalypsedefense.core.Zombie;

public class GameObject {
	private static final String TAG = "GameObject"; // for logging
	public static Bitmap zombieBitmap;
	public static Bitmap towerBitmap;
	private Bitmap graphic;
	private Person inner;
	private final GameFacade game;
	public static Paint attackedCirclePaint;
	

	/**
	 * Wrap a Person for easy access to relevant visual data.
	 * NOTE: You will need to set the static Bitmaps for these objects to work.
	 * @param person zombie/survivor/etc which this object is wrapping
	 * @param game
	 */
	public GameObject(Person person, GameFacade game){
		this.inner = person;
		this.game = game;
		setGraphic(inner);
	}

    private void setGraphic(Person inner) {
        // Was going to use the Visitor pattern, but never mind
        if (inner instanceof Zombie){
            graphic = zombieBitmap;
        }
        else {//if (inner instanceof Survivor){
            graphic = towerBitmap;
        }
    }

    public void draw(Canvas canvas) {
		// Translate to screen coords (divide, in this case), centered in the image
		float xStart = inner.position.x / game.xScale;
		float x = xStart - graphic.getWidth()/2;
		float yStart = inner.position.y / game.yScale;
		float y = yStart - graphic.getHeight()/2;
		
		canvas.drawBitmap(graphic, x, y, null);
		Log.v(TAG, String.format("drew gameObject centered at %.0f,%.0f", x, y));
		
		if (inner.isBeingAttacked()) {
			Log.d(TAG, "this game object isBeingAttacked");
			float radius = inner.hp / 5.0f;
			canvas.drawCircle(xStart, yStart, radius, attackedCirclePaint);
		}
	}

	public int getHealth() {
		return inner.hp;
	}
}
