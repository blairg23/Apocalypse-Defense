package com.apocalypsedefense.app.gameplay_visuals;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * 
 * @author Pat (taken from http://android-coding.blogspot.com/2012/02/introduce-sprite.html)
 *
 */
public class Sprite {
	private Bitmap bitmap;
	private int x;
	private int y;
	float bitmap_halfWidth, bitmap_halfHeight;

	public Sprite(Bitmap bm, int tx, int ty) {
		bitmap = bm;
		x = tx;
		y = ty;
		bitmap_halfWidth = bitmap.getWidth() / 2;
		bitmap_halfHeight = bitmap.getHeight() / 2;
	}

	public void setX(int tx) {
		x = tx;
	}

	public void setY(int ty) {
		y = ty;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - bitmap_halfWidth, y - bitmap_halfHeight,
				null);
	}

}
