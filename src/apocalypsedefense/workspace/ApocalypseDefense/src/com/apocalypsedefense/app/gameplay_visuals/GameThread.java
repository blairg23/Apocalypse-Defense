package com.apocalypsedefense.app.gameplay_visuals;

public class GameThread extends Thread {

	public volatile boolean running = false;
	private final GameSurfaceView parent;
	public long sleepTime = 100;
	
	public GameThread(GameSurfaceView parent) {
		super();
		this.parent = parent;
	}
	
	@Override
	public void run() {
		while(running) {
			try {
				sleep(sleepTime); // Is this necessary? Must be, because when it's not present the app crashes.
				//NOTE: If I set sleep to 50, it crashes; at 100 or higher, it doesn't (usually) crash.
				//TODO: investigate optimal sleep time to have a responsive but noncrashing app
				parent.updateView();
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}

}
