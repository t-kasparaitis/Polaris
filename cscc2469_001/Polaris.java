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
	 * onScannedRobot: After seeing a robot, we check if the gun is overheated, if it is not we start our calculations.
	 * I want my bullet to reach the enemy's position in as many frames as it takes for the gun to cool, so it can fire again shortly after.
	 * Heating divided by cooling, gets you the frames or "ticks" it takes to cool the gun. Take the distance and divide it by these frames.
	 * Which, gives us the needed bullet speed to have the gun cooled down by the time it reaches the intended destination.
	 * Thus our equation is neededBulletSpeed = dist / (heat / cooling); But, we can only get heat if we alredy know our firepower.
	 * As we cannot solve for 2 unknowns (neededBulletSpeed, firepower), we assume a firepower and iterate to get the optimal fire rate.
	 * We know the speed needed to travel that distance, and we get the realBulletSpeed (which is the actual speed of the bullet at that firepower).
	 * If the bullet is
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if (getGunHeat() == 0 && getOthers() < 5) {
			double dist = e.getDistance(); // this is the distance from the scanned opponent
			double power = 3.0; // let's assume we can take the most powerful shot and have gun cooled by when it lands
			double heat = 1.0 + (power/5); // this is how much heat the shot will generate
			double cooling = getGunCoolingRate(); // though cooling rate is 0.1 by default, this can be altered for different battles!
			double neededBulletSpeed; // how fast the bullet needs to be, to have gun cooled by when it hits intended coordinates
			double realBulletSpeed = 20 - power * 3; // this is the actual speed of the bullet at given power
			
			// let's try bullet power 3 heat = 1.6, dist 200, cooling 0.1 neededBulletSpeed => 

			// Our equation is neededBulletSpeed = dist / (heat / cooling), we have to assume firepower to get heat.
			// By starting at the highest power (3.0) we iterate down to (0.1) which covers all valid increments.
			// If at any point neededBulletSpeed is less than realBulletSpeed we can fire. Using less rather than less or equal than to not have precision issues.
			while (power > 0) { 
				/* Our ranking in 20+ battles fell from 4th/5th to 8th -- surprising given the elaborate approach.
				 * So let's try adding some more power to the shots by adding a magic number to heat/cooling which is our frames/ticks 
				 */
				neededBulletSpeed = dist / ((heat / cooling)+5); // Basically heat/cooling is frames, and we're okay with 5 extra frames for cooldown
				if (neededBulletSpeed < realBulletSpeed) { /* This is when our gun will cool by the time the bullet hits intended coordinates */
					fire(power);
					return; // make sure to get out of this
				}
				power -= 0.1; // subtract 0.1 from power each iteration, power > 0 & start of 3.0 prevents invalid values
			}
			fire(0.1); // if we find something so far away, that it's not optimal, we still want to fire
		} else if (getGunHeat() == 0) { // reintroduced some old logic here since we were doing worse overall
			if (e.getDistance() < 400 && getGunHeat() == 0) {
				fire(3);
			} else if (e.getDistance() > 399 && getGunHeat() == 0){
				fire(1.8);
			}
		}
	}

	/**
	 * onHitByBullet: After being hit by a bullet, turn left 90 degrees from the direction bullet came.
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		turnLeft(90 - e.getBearing());
	}	

	/**
	 * onHitWall: After hitting a wall, turn 135 degrees to the right and then move forward 150 pixels.
	 */
	public void onHitWall(HitWallEvent e) {
		turnRight(135);
		ahead(150);
	}
	
	/**
	 * onHitRobot: After hitting a robot, get the bearings to the hit robot. If robot is in the way, 
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
