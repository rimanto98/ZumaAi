/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprite;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.util.Duration;

/**
 *
 * @author km183142m
 */
public class Sun extends Sprite implements EventHandler<MouseEvent> {

    public static final double SUN_RADIUS = 70;

    public static final Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};

    private Circle body;
    private Circle mouth,mouthBack;
    private Color mouthColor, mounthColorBack;
    private int i=0;
    private long lastMoved = 0;
    private ScaleTransition closingEyes;
    private TranslateTransition closingEyestr;
    private Group eyes = new Group();
    private Group closedEyes = new Group();
    private double x,y;
    
    public Sun(double x, double y) {
        this.x = x;
        this.y = y;
        body = new Circle(SUN_RADIUS);
        body.setFill(Color.GOLD);
        
        body.setStrokeWidth(1);
        body.setStroke(Color.ORANGE);
        mouth = new Circle(SUN_RADIUS / 3);
        mouthBack = new Circle(SUN_RADIUS / 3);
        
        mouthColor = colors[(int) (Math.random() * colors.length)];
        mounthColorBack = colors[(int) (Math.random() * colors.length)];
        
        
        Stop []stops = {
            new Stop(0,Color.WHITE),
            new Stop(1,mouthColor)
        };
        RadialGradient rg = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, stops);
        mouth.setFill(rg);
        mouth.setStroke(Color.WHITE);
        mouth.setTranslateY(SUN_RADIUS / 4);
        
        
        mouthBack.setTranslateY(SUN_RADIUS / 4);
        mouthBack.setFill(null);
        mouthBack.setStrokeWidth(10);
        mouthBack.setStroke(mounthColorBack);
        
        
        Arc leftEye = new Arc(-SUN_RADIUS/3.3 + 3,-SUN_RADIUS/2 -5,10,25,180,180);
        Arc rightEye = new Arc(SUN_RADIUS/3.3 - 3,-SUN_RADIUS/2 -5,10,25,180,180);
      
        Circle left = new Circle(-SUN_RADIUS/3.3 + 3,-SUN_RADIUS/2 + 15,5);
        Circle right = new Circle(SUN_RADIUS/3.3 - 3,-SUN_RADIUS/2 + 15,5);
        
        leftEye.setStroke(Color.BLACK);
        leftEye.setFill(Color.WHITE);
        leftEye.setType(ArcType.CHORD);
        leftEye.setRotate(180);
        
        rightEye.setStroke(Color.BLACK);
        rightEye.setFill(Color.WHITE);
        rightEye.setType(ArcType.CHORD);
        rightEye.setRotate(180);
        
        Rectangle lefto = new Rectangle( -SUN_RADIUS/2 - 5 - 2, -7,15 , 10);
        lefto.setFill(Color.ORANGE);
        lefto.setArcHeight(5);
        lefto.setArcWidth(5);
        
        Rectangle righto = new Rectangle(SUN_RADIUS/2 - 5, -7, 15 , 10 );
        righto.setFill(Color.ORANGE);
        righto.setArcHeight(5);
        righto.setArcWidth(5);
        
        
        
        
        Rectangle rec1 = new Rectangle(-SUN_RADIUS, -SUN_RADIUS, SUN_RADIUS*2,SUN_RADIUS*2);
        rec1.setFill(Color.YELLOW);
        Rectangle rec2 = new Rectangle(-SUN_RADIUS, -SUN_RADIUS, SUN_RADIUS*2,SUN_RADIUS*2);
        rec2.setFill(Color.YELLOW);
        rec2.setRotate(45);
        
        Group recs = new Group();
        recs.getChildren().addAll(rec1,rec2);
        
        eyes.getChildren().addAll(leftEye,rightEye,left,right);
        
        Duration t = Duration.seconds(1);
        ScaleTransition st = new ScaleTransition(t,recs);
        st.setFromX(1);
        st.setToX(1.1);
        st.setFromY(1);
        st.setToY(1.1);
        st.setAutoReverse(true);
        st.setCycleCount(Timeline.INDEFINITE);
        st.play();
        
        getChildren().addAll(recs,body, mouthBack, mouth, eyes,lefto,righto);
        setTranslateX(x);
        setTranslateY(y);
        
        
        Line lineLeft = new Line(-SUN_RADIUS/3.3 + 3 -10,-SUN_RADIUS/2 + 20,-SUN_RADIUS/3.3 + 3 +10,-SUN_RADIUS/2 + 20);
        lineLeft.setFill(Color.BLACK);
        lineLeft.setStroke(Color.BLACK);
        Line lineRight = new Line(SUN_RADIUS/3.3 - 3 - 10,-SUN_RADIUS/2 + 20,SUN_RADIUS/3.3 - 3 + 10,-SUN_RADIUS/2 + 20);
        lineRight.setFill(Color.BLACK);
        lineRight.setStroke(Color.BLACK);
        closedEyes.getChildren().addAll(lineLeft,lineRight);
        getChildren().addAll(closedEyes);
    }

    public void setRandomMouthColor() {
        mouthColor = mounthColorBack;
        mounthColorBack = colors[(int) (Math.random() * colors.length)];
        
        Stop []stops = {
            new Stop(0,Color.WHITE),
            new Stop(1,mouthColor)
        };
        RadialGradient rg = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, stops);
        mouth.setFill(rg);
        mouth.setStroke(Color.WHITE);
        mouthBack.setStroke(mounthColorBack);
    }

    public Color getMouthColor() {
        return mouthColor;
    }

    @Override
    public void update() {
        if (System.currentTimeMillis()-lastMoved>10000 && closingEyes==null){
            closingEyes = new ScaleTransition(Duration.seconds(4), eyes);
            closingEyes.setFromY(1);
            closingEyes.setToY(0);
            closingEyes.setInterpolator(Interpolator.LINEAR);
            closingEyes.play();
            closingEyestr = new TranslateTransition(Duration.seconds(4), eyes);
            closingEyestr.setFromY(0);
            closingEyestr.setToY(12.5);
            closingEyestr.setInterpolator(Interpolator.LINEAR);
            closingEyestr.play();
        }
    }

    @Override
    public void handle(MouseEvent event) {/*
        double x = event.getX(), y = event.getY();
        double dx = getTranslateX() - x, dy = y - getTranslateY();
        double alpha = 90 - Math.toDegrees(Math.atan(dy / dx));
        if (x > getTranslateX()) {
            alpha -= 180;
        }
        setRotate(alpha);
        lastMoved = System.currentTimeMillis();
        if (closingEyes!=null){
            closingEyes.stop();
            closingEyestr.stop();
            eyes.setScaleY(1);
            eyes.setTranslateY(0);
            closingEyes = null;
            closingEyestr = null;
        }
        */
    }
    
    public void dirSun(int x1, int y1) {
        double x = x1, y = y1;
        double dx = getTranslateX() - x, dy = y - getTranslateY();
        double alpha = 90 - Math.toDegrees(Math.atan(dy / dx));
        if (x > getTranslateX()) {
            alpha -= 180;
        }
        setRotate(alpha);
        lastMoved = System.currentTimeMillis();
        if (closingEyes!=null){
            closingEyes.stop();
            closingEyestr.stop();
            eyes.setScaleY(1);
            eyes.setTranslateY(0);
            closingEyes = null;
            closingEyestr = null;
        }
        
    }

    public ScaleTransition getClosingEyes() {
        return closingEyes;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
}
