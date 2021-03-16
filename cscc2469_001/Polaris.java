package cscc2469_001;
import robocode.*;
import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Polaris - a robot by Tomas Kasparaitis and Helen Baker
 */
public class Polaris extends Robot
{
	/**
	 * run: Polaris's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// Set colors of body, gun, radar, bullet and scan arc
		setColors(Color.green,Color.green,Color.green,Color.green,Color.green);

		// Robot main loop
		while(true) {
			ahead(100);
			turnGunRight(360);
			back(100);
			turnGunRight(360);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if (getGunHeat() == 0) {
			fire(1);
		}
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		back(10);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		back(20);
	}	
}
