package com.apocalypsedefense.core;

public class ZombieManager {

	Game game;
	
	public ZombieManager(Game game) {
		this.game = game;
		// TODO Auto-generated constructor stub
	}
	
	public void createZombieAt(int x, int y){
		game.zombies.add(new Zombie(new Point(x,y)));
	}

	
	public void update(){
		Movement m = new Movement(game.map);
		// For each zombie in the list of zombies:
		for (int z = 0; z < game.zombies.size(); z++) {
			// If the zombie has a target and the target is still alive (some other zombie hasn't killed it):
			Zombie zombie = game.zombies.get(z);
			if (zombie.target != null
					&& game.survivors.contains(zombie.target)) {
				// If the zombie isn't attacking:
				if (zombie.state.equals("Walking")) {
					// Move toward target:
					zombie.position = m.getNewPosition(zombie.position,
							"Directed", zombie.target.position);
					// Check if the zombie can attack the target:
					if (zombie.position.getDistance(zombie.target.position) <= 1) {
						// If so, switch zombie to attack mode:
						zombie.state = "Attacking";
					}
				} else if (zombie.state.equals("Attacking")) {
					// Attack target:
					zombie.target.takeDamage(zombie.attack());
					// If the target is killed:
					if (!zombie.target.isAlive) {
						// Remove the survivor:
						game.survivors.remove(zombie.target);
						// For testing purposes:
						System.out.print(zombie.target.name
								+ " DIED! Position:["
								+ zombie.target.position.getCoords() + "]");
						System.out.println("Killer: " + zombie.name
                                + " Position:[" + zombie.position.getCoords()
                                + "]");

						// game.shop = new Shop(game.gold, "Survivor", "Buy", 1,
						// new Point(0,0), 1);
						// ArrayList<Survivor> survivors = game.shop.buyTower();
						// for(int s=0;s<survivors.size();s++){
						// Random r = new Random();
						// int x = r.nextInt(game.width);
						// int y = r.nextInt(game.height);
						// survivors.get(s).position = new Point(x,y);
						// Arrays.asList(game.survivors).add(survivors.get(s));

						// Set the zombie's target to null:
						zombie.target = null;

						// Set the zombie's state to "Walking":
						zombie.state = "Walking";
					}
				}
			}
			// Otherwise, if the zombie doesn't have a target or the target was killed by another zombie
			// and there are still targets:
			else if ((zombie.target == null
					|| !game.survivors.contains(zombie.target))
					&& game.survivors.size() != 0) {
				zombie.target = null;
				zombie.state = "Walking";
				// Randomly walk:
				zombie.position = m.getNewPosition(zombie.position, "Random",
						null);

				// Find the closest survivor:
				float minDist = 99999;
				Survivor closestTarget = null;
				for (int s = 0; s < game.survivors.size(); s++) {
					float dist = zombie.position.getDistance(game.survivors
							.get(s).position);
					if (dist < minDist) {
						minDist = dist;
						closestTarget = game.survivors.get(s);
					}
				}
				if (closestTarget != null){
					// Set the zombie's target to that closest survivor:
					zombie.target = closestTarget;					
				}
			}
		}
	}

}
