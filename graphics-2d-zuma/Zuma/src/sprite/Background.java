/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprite;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.*;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import zuma.Zuma;

/**
 *
 * @author km183142m
 */
public class Background extends Group{
    
    private static List<Polygon> stars = new ArrayList<>();
        
    
    private static double left = -20;
    private static double right = 20;
    private static double height = 50;
    private static double points[] = {
        left,0,
        right,0,
        left + 7,height*2/3,
        0,-height/3,
        right-7,height*2/3,
        left,0              
    };
    
    private Path createPath(double x, double y) {

        int loc = (int) (Math.random()*(Zuma.WINDOW_WIDTH/2) + Zuma.WINDOW_WIDTH);

        Path path = new Path();

        path.getElements().add(new MoveTo(x,y+8));
        CubicCurveTo cubicCurveTo = new CubicCurveTo();
        
        double xx = Zuma.WINDOW_WIDTH/2+Math.random()*(Zuma.WINDOW_WIDTH/2-60);
        double yy = Math.random()*(Zuma.WINDOW_HEIGHT-120)+60;
        cubicCurveTo.setControlX1(xx); 
        cubicCurveTo.setControlY1(yy); 
        xx = Zuma.WINDOW_WIDTH/2+Math.random()*(Zuma.WINDOW_WIDTH/2-60);
        yy = Math.random()*(Zuma.WINDOW_HEIGHT-120)+60;
        cubicCurveTo.setControlX2(xx); 
        cubicCurveTo.setControlY2(yy);
        xx = Zuma.WINDOW_WIDTH/2+Math.random()*(Zuma.WINDOW_WIDTH/2-60);
        yy = Math.random()*(Zuma.WINDOW_HEIGHT-120)+60;
        cubicCurveTo.setX(xx); 
        cubicCurveTo.setY(yy);  

        path.getElements().add(cubicCurveTo);
        
        //path.getElements().add(new LineTo(xx,yy));
        return path;
    }
    
    
    public Background(double width, double height){
        Rectangle background = new Rectangle(0, 0, width + 10, height + 10);
        
        
        Stop []stops = {
            new Stop(0,Color.WHITE),
            new Stop(1,Color.BLACK)
        };
        
        LinearGradient lg = new LinearGradient(0,0,1,0,true,CycleMethod.NO_CYCLE,stops);
        background.setFill(lg);
        
        //background.setFill(Color.BLACK);
        getChildren().add(background);
        
        /*for (int i=0;i<3;i++){
            Polygon star = new Polygon(points);
            star.setFill(Color.YELLOW);
            double x = Zuma.WINDOW_WIDTH/2+Math.random()*Zuma.WINDOW_WIDTH/2-60;
            double y = Math.random()*(Zuma.WINDOW_HEIGHT-120)+60;
            star.setTranslateX(x);
            star.setTranslateY(y);
            getChildren().add(star);
            stars.add(star);
   
            PathTransition pr = new PathTransition();
            
            pr.setDuration(Duration.seconds(5));
            pr.setNode(star);
     
            pr.setPath(createPath(x,y));
            pr.setOnFinished(e -> {
                double maxx1 = Zuma.WINDOW_WIDTH/2 - star.getTranslateX();
                double minx1 = - star.getTranslateX();
                double maxy1 = Zuma.WINDOW_HEIGHT/2 - star.getTranslateY();
                double miny1 = - star.getTranslateY();
                    
                pr.setPath(createPath(star.getTranslateX(),star.getTranslateY()));
                pr.play();
            });
            pr.play();
        }*/
    }
    
    public void addStar(Ball ball){
        int cnt = ball.getCnt();
        
        int starsCnt = stars.size();
        
        if (cnt + starsCnt == 10){
            Polygon star = new Polygon(points);
            star.setFill(Color.YELLOW);
            double x = Zuma.WINDOW_WIDTH/2+Math.random()*Zuma.WINDOW_WIDTH/2-60;
            double y = Math.random()*(Zuma.WINDOW_HEIGHT-120)+60;
            star.setTranslateX(x);
            star.setTranslateY(y);
            getChildren().add(star);
            stars.add(star);
   
            PathTransition pr = new PathTransition();
            
            pr.setDuration(Duration.seconds(5));
            pr.setNode(star);
     
            pr.setPath(createPath(x,y));
            pr.setOnFinished(e -> {
                double maxx1 = Zuma.WINDOW_WIDTH/2 - star.getTranslateX();
                double minx1 = - star.getTranslateX();
                double maxy1 = Zuma.WINDOW_HEIGHT/2 - star.getTranslateY();
                double miny1 = - star.getTranslateY();
                    
                pr.setPath(createPath(star.getTranslateX(),star.getTranslateY()));
                pr.play();
            });
            pr.play();
        }
    }
    
}
