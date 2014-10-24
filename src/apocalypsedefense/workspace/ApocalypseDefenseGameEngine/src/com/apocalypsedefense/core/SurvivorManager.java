package com.apocalypsedefense.core;

public class SurvivorManager {
	Game game;

	public SurvivorManager(Game game) {
		this.game = game;

		// TODO Auto-generated constructor stub
	}

	public void buySurvivorAt(int x, int y) throws Exception {
		if (!canBuySurvivorAt(x, y)) {
			throw new Exception(
					"CANNOT BUY SURVIVOR, DID YOU CHECK canBuySurvivorAt()?"); // TODO:
																				// PUT
																				// A
																				// REAL
																				// ERROR
																				// HERE
		}
		game.survivors.add(new Survivor(new Point(x, y))); // Add that survivor
															// to our current
															// list //TODO
															// FInish logic here
	}

	public Boolean canBuySurvivorAt(int x, int y) {
		return !game.map.isBlocked(new Point(x, y)); // false if blocked, true
														// if not blocked
	}

	public void update() {

		// For each survivor in the list of survivors:
		for (int s = 0; s < game.survivors.size(); s++) {
			// If the survivor has a target and the target is still alive:
			Survivor survivor = game.survivors.get(s);
			if (survivor.target != null
					&& game.zombies.contains(survivor.target)) {
				// Shoot the target:
				survivor.target.takeDamage(survivor.attack());
				// If the target is killed:
				if (!survivor.target.isAlive) {
					// Remove the zombie:
					System.out.print(survivor.target.name + " killed!");
					game.zombies.remove(survivor.target);
					game.zombieKillCount++; // Add kill to zombie kill list
					System.out.print("Zombie Killer Counter: "
							+ game.zombieKillCount);
					game.gold += survivor.target.worth; // Add gold from kill
					System.out.print("Gold: " + game.gold);
					// Reset survivor's target:
					survivor.target = null;
				}
			}
			// Otherwise, if the survivor doesn't have a target or the target
			// died and there are still zombies:
			else if ((survivor.target == null || !game.zombies
					.contains(survivor.target)) && game.zombies.size() != 0) {
				// Set the survivor's target to null to skip checking next loop:
				survivor.target = null;
				// Find the closest zombie:
				float minDist = 99999;
				Zombie closestTarget = null;
				for (int z = 0; z < game.zombies.size(); z++) {
					float dist = survivor.position.getDistance(game.zombies
							.get(z).position);
					// If we found a new minimum distance AND the zombie is
					// within attack range:
					if (dist < minDist && dist < survivor.weapon.range) {
						minDist = dist;
						closestTarget = game.zombies.get(z);
					}
				}
				if (closestTarget != null) {

					// Set the survivor's target to the closest zombie:
					survivor.target = closestTarget;
					// If the survivor targets the zombie, the zombie will
					// automatically target the survivor:
					closestTarget.target = survivor;

				}
			}
		}

	}
}
