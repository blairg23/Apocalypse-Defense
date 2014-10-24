package com.apocalypsedefense.app.gameplay_visuals;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

import com.apocalypsedefense.app.R;

import com.apocalypsedefense.core.GameData;
import com.apocalypsedefense.core.GameState;
import com.apocalypsedefense.core.Shared;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class GameSurfaceView extends SurfaceView implements Callback {

	private static final String TAG = "GameSurfaceView"; // for logging

	private GameFacade game = null;
	
	private static final int MAX_SPRITE_COUNT = 100;

	static SurfaceHolder surfaceHolder;

	static GameThread myGameThread = null;

//	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	static Random random;

	private int myCanvas_w;

	private int myCanvas_h;

	private static Bitmap myCanvasBitmap;

	private static Canvas myCanvas;

	private static Sprite tower;
	
	static Sprite[] zombieSprites = new Sprite[MAX_SPRITE_COUNT];

	static Sprite[] towerSprites = new Sprite[MAX_SPRITE_COUNT];
    private int width;
    private int height;

	private OnStatsChangedListener onStatsChangedListener;
    private OnGameEndListener onGameEndListener;

    public GameSurfaceView(Context context) {
		super(context);
		init();
	}

	public GameSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public GameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d(TAG, "surfaceChanged called: " + width + "x" + height);
		this.width = width;
        this.height = height;
        game = new GameFacade(width, height, 10,10); //TODO: Test for best tile size 
        // (affects speed of zombies, even though they should move at a pace independent of tile size)
    }
	 
	private void init(){
		int xLocationToPlaceCenterInCanvas = 100;
		int y = 100;
		final Bitmap towerBitmapFullSize = BitmapFactory.decodeResource(getResources(),
				R.drawable.boy_top_facing_left);
		final Bitmap towerBitmapScaled = Bitmap.createScaledBitmap(
				towerBitmapFullSize, 75, 75, false);
		GameObject.towerBitmap = towerBitmapScaled;
		tower = new Sprite(towerBitmapScaled, 
				xLocationToPlaceCenterInCanvas, y);
		towerSprites[0] = tower;
		
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setColor(Color.RED);
		p.setAlpha(128);
		GameObject.attackedCirclePaint = p;
		
		// Game core settings
		Shared.Log = new AndroidLogAdapter();
	}
	 
	@Override
	protected void onDraw(Canvas canvas) {
		// Clear a Canvas which has a transparent background
		canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
		
//		for (int i=0; i<towerSprites.length; i++){
//			if (towerSprites[i] != null) {
//				towerSprites[i].draw(canvas);
//			}
//		}

		// Draw zombies for debugging
//		for (int i=0; i < numZombiesVisible; i++){
//			zombieSprites[i].draw(canvas);
//		}
		
		// Show zombies and towers
        if(game != null){
            try{
                ArrayList<GameObject> gameObjects = game.getGameObjects();
                Log.v(TAG, String.format("%d gameObjects returned", gameObjects.size()));
                for (GameObject o : gameObjects){
                    int health = o.getHealth();
                    if (health <= 0){
                        continue; // Don't draw
                    }
//                    //TODO
//        			if (o.isBeingAttacked()){
//        				// Draw health bar
//        				
//        			}
                    o.draw(canvas);
                }
            } catch (ConcurrentModificationException e){
                // The collection was changed on another thread
                // Ignore, for now, which will look weird because no objects will be drawn, but it's better than force close
            }
        }
        
	}

	int numZombiesVisible = 0;

	public void updateStates() {
		// Update game
        if (game != null) {
		    game.update();
            Shared.gameData = game.getGameData();
        }
		
//		// occasionally add a zombie to the screen
//		if (numZombiesVisible < MAX_SPRITE_COUNT && random.nextInt(30) == 0) {
//			numZombiesVisible++;
//		}
	}
	
	private boolean isFirstTower = true; // for game start indication

	@Override
	public boolean onTouchEvent(MotionEvent event) {
        if (game == null) {
            throw new IllegalStateException(
                    "game was null in onTouchEvent. we expected it to be initialized in onSurfaceChanged");
        }
		int x = (int) event.getX();
		int y = (int) event.getY();

		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.d(TAG, "touch event action was down");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.d(TAG, "touch event action was move");
//			tower.setX(x);
//			tower.setY(y);
			break;
		case MotionEvent.ACTION_UP:
			Log.d(TAG, "touch event action was up");
			if (game.canBuyTowerAt(x,y)) {
                // Start the game if this is the first tower placement
                if(isFirstTower){
                    isFirstTower = false;
                    game.start();
                    Log.d(TAG, "started game");
                }
				game.buyTowerAt(x,y);
				Log.d(TAG, String.format("bought tower at %d,%d", x, y));
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			Log.d(TAG, "touch event action was cancel");
			break;
		case MotionEvent.ACTION_OUTSIDE:
			Log.d(TAG, "touch event action was outside");
			break;
		default:
		}

		// http://www.youtube.com/watch?v=U4Bk5rmIpic&feature=player_detailpage#t=3359s
		// Perf tip from above url is to sleep for >= 16ms to slow
		// the onrush of motion events
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public void surfaceCreated(SurfaceHolder holder) {
		myCanvas_w = getWidth();
		myCanvas_h = getHeight();
		myCanvasBitmap = Bitmap.createBitmap(myCanvas_w, myCanvas_h, Bitmap.Config.ARGB_8888);
		myCanvas = new Canvas();
		myCanvas.setBitmap(myCanvasBitmap);
		
		// Pre-create zombies at random locations
		final Bitmap zombieBitmapFull = BitmapFactory.decodeResource(
				getResources(), 
				R.drawable.zombie_facing_down_8bit);
		// Rescale bitmap to be smaller - don't know if this is a KLUDGE, but it works
		final Bitmap zombieBitmap = Bitmap.createScaledBitmap(
				zombieBitmapFull, 50, 50, false);
		GameObject.zombieBitmap = zombieBitmap;
		for (int i = 0; i < MAX_SPRITE_COUNT; i++) {
			Sprite zombie = new Sprite(zombieBitmap, 
							random.nextInt(myCanvas_w),
							random.nextInt(myCanvas_h));
			zombieSprites[i] = zombie;
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	public void onResume() {
		random = new Random();
		surfaceHolder = getHolder();
		getHolder().addCallback(this); // will this add us multiple times?

		// Create and start background Thread
		myGameThread = new GameThread(this);
		myGameThread.running = true;
		myGameThread.start();
	}

	public void onPause() {
		//TODO: There is a bug when you pause before starting, but this doesn't fix it
//		if (!game.isStarted()) {
//			return;
//		}
		// Kill the background Thread
		boolean retry = true;
		myGameThread.running = false;

		while (retry) {
			try {
				myGameThread.join();
				retry = false;
			} catch (InterruptedException e) {
				Log.e(TAG, "error when trying to join the game thread", e);
			}
		}
	}

	public void updateView() {
		// The function run in background thread, not ui thread.
		Canvas canvas = null;
		try {
			canvas = surfaceHolder.lockCanvas();
			if (canvas == null) {
				return; // Happens in emulator, not sure what else to do
			}
			synchronized (surfaceHolder) {
				updateStates();
				onDraw(canvas);
			}
		} finally {
			if (canvas != null) {
				surfaceHolder.unlockCanvasAndPost(canvas);
				checkStatsChanged();
                checkGameEnd();
			}
		}
	}

    public void setOnStatsChangedListener(OnStatsChangedListener value) {
        onStatsChangedListener = value;
    }

    private void checkStatsChanged() {
        if (onStatsChangedListener != null && game != null && game.isStarted()) {
            GameData stats = game.getGameData();
            onStatsChangedListener.onStatsChanged(stats);
        }
    }

    public void setOnGameEndListener(OnGameEndListener onGameEndListener) {
        this.onGameEndListener = onGameEndListener;
    }

    private void checkGameEnd() {
        if (onGameEndListener != null && game != null){
            final GameState gameState = game.getGameState();
            switch (gameState) {
                case LOST:
                case WON:
                    onGameEndListener.onGameEnd(gameState);
                    break;
            }
        }
    }
}
