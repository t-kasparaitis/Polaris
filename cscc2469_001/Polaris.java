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
		// get the size of our battlefield in pixels:
		double y = getBattleFieldHeight();
		double x = getBattleFieldWidth();
		// get the size of our robot in pixels:
		double yRobot = getHeight();
		double xRobot = getWidth();

		// Setting colors with clearly named methods:
		setBodyColor(new Color(38,81,82)); // teal
		setGunColor(new Color(38,81,82)); // teal
		setRadarColor(new Color(252, 186, 3)); // gold
		setBulletColor(Color.white);
		setScanColor(Color.white); 

		// Robot main loop
		while(true) {
			/* We can customize movement later, based on battlefield size, robot size and robot location
			 * double yLoc = getY();
			 * double xLoc = getX();
			 */
			ahead(200);
			turnGunRight(360);
			back(300);
			turnGunRight(360);
		}
	}

	/**
	 * onScannedRobot: After seeing a robot, if gun is not overheated, fire.
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if (e.getDistance() < 400 && getGunHeat() == 0) {
			fire(3);
		} else if (e.getDistance() > 399 && getGunHeat() == 0){
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
	 * After hitting a wall, turn 135 degrees to the right and then move forward 400.
	 */
	public void onHitWall(HitWallEvent e) {
		turnRight(135);
		ahead(400);
	}	
}
