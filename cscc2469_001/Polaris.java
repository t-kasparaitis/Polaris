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
	 * After being hit by a bullet, turn left 90 degrees from the direction bullet came.
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		turnLeft(90 - e.getBearing());
	}
	
	/**
	 * After hitting a wall, turn 135 degrees to the right and then move forward 150 pixels.
	 */
	public void onHitWall(HitWallEvent e) {
		turnRight(135);
		ahead(150);
	}
	
	/**
	 * After hitting a robot, get the bearings to the hit robot.  If robot is in the way, 
	 * within 180 degrees of robot's heading, turn right 45 degrees and then move back 100 pixels.  
	 * Otherwise turn left 45 degrees and then move forward 100 pixels.
	 */
	public void onHitRobot(HitRobotEvent e) {
		if (e.getBearing() > -90 && e.getBearing() <= 90) {
			turnRight(45);
			back(100);
		} else {
			turnLeft(45);
			ahead(100);
		  }
	}	
}
