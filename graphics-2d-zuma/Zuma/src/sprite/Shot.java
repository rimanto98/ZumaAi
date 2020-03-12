/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprite;

/**
 *
 * @author km183142m
 */
public class Shot extends Ball {

    private static final double SHOT_VELOCITY = -10;

    private enum ShotState {
        SHOT, MOVING
    }

    private double velocityX, velocityY;
    private ShotState shotState = ShotState.SHOT;

    public Shot(Sun sun) {
    	super();
        this.color = sun.getMouthColor();
        this.body.setFill(color);
        velocityX = SHOT_VELOCITY * Math.sin(Math.toRadians(sun.getRotate()));
        velocityY = -SHOT_VELOCITY * Math.cos(Math.toRadians(sun.getRotate()));
        setTranslateX(sun.getTranslateX() - Sun.SUN_RADIUS / 4.0 * Math.sin(Math.toRadians(sun.getRotate())));
        setTranslateY(sun.getTranslateY() + Sun.SUN_RADIUS / 4.0 * Math.cos(Math.toRadians(sun.getRotate())));
    }
    
  

    public ShotState getShotState() {
		return shotState;
	}



	public void becomeMoving(Ball neighbor) { //QUANDO ENTRA NELLA CODA = MOVING
        shotState = ShotState.MOVING;
        velocity = neighbor.velocity;
        velocityState = neighbor.velocityState;
        cnt = neighbor.cnt;
        division = neighbor.division;
        setTranslateX(neighbor.getTranslateX());
        setTranslateY(neighbor.getTranslateY());
    }

    
    @Override
    public void update() {
        if (shotState.equals(ShotState.SHOT)) {
            double x = getTranslateX();
            double y = getTranslateY();
            setTranslateX(x + velocityX);
            setTranslateY(y + velocityY);
        } else {
            super.update();
        }
    }

}
