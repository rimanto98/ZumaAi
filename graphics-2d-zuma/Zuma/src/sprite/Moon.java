/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprite;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.util.Duration;
import static sprite.Sun.SUN_RADIUS;

/**
 *
 * @author km183142m
 */
public class Moon extends Sprite {

    private static final double MOON_RADIUS = 50;

    private Circle body;

    private double x;
    private double y;

    private Circle leftPupil;
    private Circle rightPupil;
    private Arc leftEye;
    private Arc rightEye;
    
    private long t = 0;
    private long cnt = 0;
    
    public Moon(double x, double y) {
        this.x = x;
        this.y = y;
        body = new Circle(MOON_RADIUS);
        
        Stop []stops = {
            new Stop(0,Color.WHITE),
            new Stop(1,Color.LIGHTBLUE)
        };
        LinearGradient lg = new LinearGradient(0,0,0.7,0,true,CycleMethod.NO_CYCLE,stops); 
        body.setFill(lg);
        
        
        leftEye = new Arc(-MOON_RADIUS/3.3,-MOON_RADIUS/2 -5,10,25,180,180);
        rightEye = new Arc(MOON_RADIUS/3.3,-MOON_RADIUS/2-5,10,25,180,180);

        leftEye.setStroke(Color.BLACK);
        leftEye.setFill(Color.GRAY);
        leftEye.setType(ArcType.CHORD);
        leftEye.setRotate(180);
        
        rightEye.setStroke(Color.BLACK);
        rightEye.setFill(Color.GRAY);
        rightEye.setType(ArcType.CHORD);
        rightEye.setRotate(180);
        
        leftPupil = new Circle(-MOON_RADIUS/3.3 ,-MOON_RADIUS/2 + 15,5);
        rightPupil = new Circle(MOON_RADIUS/3.3 ,-MOON_RADIUS/2 + 15,5);
        
        
        
        Circle mounth = new Circle(0,MOON_RADIUS/2, 5);
        mounth.setFill(Color.BLACK);
        ScaleTransition sc = new ScaleTransition(Duration.seconds(1), mounth);
        sc.setByX(1.1);
        sc.setByY(1.1);
        sc.setAutoReverse(true);
        sc.setCycleCount(Animation.INDEFINITE);
        sc.play();
        
        
        Polygon kapa = new Polygon(-MOON_RADIUS+5,0,0,-MOON_RADIUS,MOON_RADIUS-5,0);
        kapa.setTranslateY(-MOON_RADIUS +7);
        kapa.setFill(Color.DARKGRAY);
        Rectangle kaparec = new Rectangle(-MOON_RADIUS , -MOON_RADIUS + 7, MOON_RADIUS*2 , 10);
        kaparec.setArcWidth(10);
        kaparec.setArcHeight(10);
        kaparec.setFill(Color.WHITE);
        Circle kapacircle = new Circle(0, -MOON_RADIUS*2+7, 10, Color.WHITE);
        
        getChildren().addAll(body,leftEye,rightEye,mounth,kapa,kaparec, kapacircle);
        setTranslateX(x);
        setTranslateY(y);
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    
    public void openEyes(){
        
            
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (System.currentTimeMillis()-t>500){
                    t = System.currentTimeMillis();
                    if (cnt==4){
                        this.stop();
                    }
                    if (cnt%2==0){
                        rightEye.setFill(Color.WHITE);
                        leftEye.setFill(Color.WHITE);
                        getChildren().addAll(leftPupil,rightPupil);
                    }
                    else{
                        rightEye.setFill(Color.GREY);
                        leftEye.setFill(Color.GREY);
                        getChildren().removeAll(leftPupil,rightPupil);
                    }
                    cnt++;
                }
                      
            }
        }.start();
        
    }
    

}
