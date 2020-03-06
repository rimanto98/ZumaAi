/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprite;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import zuma.Zuma;

/**
 *
 * @author km183142m
 */

@Id("ball")
public class Ball extends Sprite {

    protected static final double BALL_VELOCITY = 0.90;
    protected static final double BALL_RADIUS = 16;
    private static final int BORDER_DIVISION = 8;

    private static final Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};

    public enum VelocityState {
        UP, DOWN, LEFT, RIGHT
    }
    protected VelocityState velocityState = VelocityState.DOWN;
    protected double velocity;
    
    protected Circle body;
    protected int division = BORDER_DIVISION;
    protected int cnt = BORDER_DIVISION - 1;
    
    private boolean shielded = false;

    private Circle shield = null;
    private ScaleTransition shieldScale;
    
    @Param(0)
    protected int x;
    
    @Param(1)
    protected int y;
    
    @Param(2)
    protected Color color;
    
    public void setColor(Color color) {
		this.color = color;
	}

	@Param(3)
    protected int position; 
    
    public Ball() {
        body = new Circle(BALL_RADIUS);
        color = colors[(int) (Math.random() * colors.length)];
        Stop []stops = {
            new Stop(0,Color.WHITE),
            new Stop(1,color)
        };
        RadialGradient rg = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, stops);
        
        body.setFill(rg);
        body.setStroke(Color.WHITE);
        
        getChildren().addAll(body);
        velocity = BALL_VELOCITY;
        setTranslateX(1 / 8.0 * Zuma.WINDOW_WIDTH);
        setTranslateY(0);
        x = (int) getTranslateX();
        y = (int) getTranslateY();
        
        
    }

    
    
    public int getPosition() {
		return position;
	}



	public void setPosition(int position) {
		this.position = position;
	}



	public int getX() {
		return x;
	}



	public void setX(int x) {
		this.x = x;
	}



	public int getY() {
		return y;
	}



	public void setY(int y) {
		this.y = y;
	}



	public Color getColor() {
        return color;
    }

    public VelocityState getVelocityState() {
        return velocityState;
    }

    public Circle getBody() {
        return body;
    }

    public void checkUpdate(boolean condition, boolean condVelocity, VelocityState state) {
        if (condition) {
            --cnt;
            if (condVelocity) {
                velocity = -velocity;
            }
            velocityState = state;
        }
    }

    @Override
    public void update() {
        division = (cnt < 5 ? 4 : 8);
        switch (velocityState) {
            case UP:
                setTranslateY(getTranslateY() + velocity);
                y = (int) getTranslateY();
                checkUpdate(getTranslateY() + velocity < (Zuma.WINDOW_HEIGHT / division),
                        false, Ball.VelocityState.LEFT);
                break;
            case DOWN:
                setTranslateY(getTranslateY() + velocity);
                y = (int) getTranslateY();
                checkUpdate(getTranslateY() + velocity > (Zuma.WINDOW_HEIGHT * (1 - 1.0 / division)),
                        false, Ball.VelocityState.RIGHT);
                break;
            case LEFT:
                setTranslateX(getTranslateX() + velocity);
                x = (int) getTranslateX();
                checkUpdate(getTranslateX() + velocity < (Zuma.WINDOW_WIDTH / division),
                        true, Ball.VelocityState.DOWN);
                break;
            case RIGHT:
                setTranslateX(getTranslateX() + velocity);
                x = (int) getTranslateX();
                checkUpdate(getTranslateX() + velocity > (Zuma.WINDOW_WIDTH * (1 - 1.0 / division)),
                        true, Ball.VelocityState.UP);
                break;
        }
    }

    public void reverse(int times) {
        for (int i = 0; i < times; i++) {
            double step = BALL_RADIUS * 2;
            division = cnt < 3 ? 4 : 8;
            while (step > 0) {
                step -= Math.abs(velocity);
                switch (velocityState) {
                    case UP:
                        setTranslateY(getTranslateY() - velocity);
                        if (getTranslateY() - velocity > (Zuma.WINDOW_HEIGHT * (1.0 - 1.0 / division))) {
                            cnt++;
                            velocityState = Ball.VelocityState.RIGHT;
                            velocity = -velocity;
                        }
                        break;
                    case DOWN:
                        setTranslateY(getTranslateY() - velocity);
                        if (cnt < BORDER_DIVISION - 1 && getTranslateY() - velocity < (Zuma.WINDOW_HEIGHT / division)) {
                            cnt++;
                            velocityState = Ball.VelocityState.LEFT;
                            velocity = -velocity;
                        }
                        break;
                    case LEFT:
                        setTranslateX(getTranslateX() - velocity);
                        if (getTranslateX() - velocity > (Zuma.WINDOW_WIDTH * (1.0 - 1.0 / division))) {
                            cnt++;
                            velocityState = Ball.VelocityState.UP;
                        }
                        break;
                    case RIGHT:
                        setTranslateX(getTranslateX() - velocity);
                        if (cnt < BORDER_DIVISION - 1 && getTranslateX() - velocity < Zuma.WINDOW_WIDTH / division) {
                            cnt++;
                            velocityState = Ball.VelocityState.DOWN;
                        }
                        break;
                }
            }
        }
    }

    public static double getRadius() {
        return BALL_RADIUS;
    }
    
    
    public void setShield(){
        if (Math.random()*100>70){
            shielded = true;
            shield = new Circle(BALL_RADIUS+3); 
            Color c= new Color(color.getRed(),color.getGreen(),color.getBlue(),1);
            shield.setFill(c);
            getChildren().remove(body);
            getChildren().add(shield);
            getChildren().add(body);
            shieldScale= new ScaleTransition(Duration.seconds(1),shield);
            shieldScale.setFromX(1);
            shieldScale.setToX(1.1);
            shieldScale.setFromY(1);
            shieldScale.setToY(1.1);
            shieldScale.setAutoReverse(true);
            shieldScale.setCycleCount(Animation.INDEFINITE);
            shieldScale.play();
        }
    }
    
    
    public boolean checkShield(){
        if (shield!=null && shield.getFill() != Color.WHITE){
            shield.setFill(Color.WHITE); 
            return true;
        }
        if (shield !=null){
            getChildren().remove(shield);
            shieldScale.stop();
            shield = null;
        }
        return false;
    }

    public boolean getShielded(){
        return shielded;
    }

    public int getCnt() {
        return cnt;
    }
    
    
}
