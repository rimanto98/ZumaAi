package zuma;

import java.awt.Font;
import sprite.*;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Zuma extends Application {

    public static final double WINDOW_WIDTH = 1200;
    public static final double WINDOW_HEIGHT = 700;

    private AnimationTimer timer;

    private static Group root;
    private Moon moon;
    private Sun sun;

    private static List<Ball> balls = new ArrayList<>();
    private static List<Shot> shots = new ArrayList<>();

    private Scene scene;
    private long t = 0;
    private int sec = 0;
    private Text time;
    private static boolean end = false;
    private static int balls_cnt = 0;
    private Polygon z = null;
    private int points = 0;
    private Text score;
    private Background background;
    private ArrayList<Circle> coins = new ArrayList<Circle>();
    private Text coinText;
    
    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        background = new Background(WINDOW_WIDTH, WINDOW_HEIGHT);
        sun = new Sun(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);
        moon = new Moon((3.0 / 4) * WINDOW_WIDTH, WINDOW_HEIGHT / 2);
        balls.add(new Ball());
        root.getChildren().addAll(background, sun, moon, balls.get(0));
        
        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setOnMouseMoved(sun);
        scene.setOnMouseClicked(k -> {
            Zuma.makeShot(new Shot(sun));
            sun.setRandomMouthColor();
        });
        primaryStage.setTitle("Borba svetlosti");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();  
            }
        };
        timer.start();
        
        score = new Text("Score: " + points);
        score.setFill(Color.WHITE);
        score.setX(WINDOW_WIDTH-WINDOW_WIDTH/10);
        score.setY(20);
        root.getChildren().add(score);
        
        time = new Text();
        time.setText("Time: " + sec);
        time.setFill(Color.WHITE);
        time.setX(WINDOW_WIDTH/2);
        time.setY(20);
        root.getChildren().add(time);
    }

    public Polygon makeZ(double x,double y){
        double top = y - 15;
        double bottom = y + 15;
        double left = x - 10;
        double right = x + 10;
        double offset = 5;
        Polygon z = new Polygon(left, top, 
                right , top, 
                right, top + offset, 
                left + offset, bottom  - offset, 
                right , bottom - offset, 
                right, bottom, 
                left, bottom, 
                left, bottom - offset,
                right - offset, top + offset, 
                left, top + offset);
        
        z.setFill(Color.WHITE);
        z.setStrokeWidth(2);
        z.setStroke(Color.BLACK);
        return z;
        
    }
    
    
    public void update() {
        if (end){
            for (int i = 0; i < shots.size(); i++) {
                Shot shot = shots.get(i);
                root.getChildren().remove(shot);
                shots.remove(shot);
            }
            return;
        }
        
        // Adding time.
        if (System.currentTimeMillis() - t > 1000){
            sec++;
            t = System.currentTimeMillis();

            // make Z
            if (Math.random()*100 > 75){
                z = makeZ(moon.getX(),moon.getY());

                RotateTransition rt = new RotateTransition(Duration.seconds(1), z);
                double angle = Math.random()*80;
                rt.setFromAngle(angle);
                rt.setToAngle(-angle);

                rt.setAutoReverse(true);
                rt.setCycleCount(Animation.INDEFINITE);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(3), z);
                tt.setByY(-WINDOW_HEIGHT/2 - 40);
                tt.setInterpolator(Interpolator.LINEAR);
                rt.play();
                tt.play();
                root.getChildren().add(z);
            }

            if (sun.getClosingEyes()!=null && Math.random()*100 > 75){
                z = makeZ(sun.getX(),sun.getY());

                RotateTransition rt = new RotateTransition(Duration.seconds(1), z);
                double angle = Math.random()*80;
                rt.setFromAngle(angle);
                rt.setToAngle(-angle);

                rt.setAutoReverse(true);
                rt.setCycleCount(Animation.INDEFINITE);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(3), z);
                tt.setByY(-WINDOW_HEIGHT/2 - 40);
                tt.setInterpolator(Interpolator.LINEAR);
                rt.play();
                tt.play();
                root.getChildren().add(z);
            }

            if (Math.random()*100 > 50){                 
                int division = (int) (Math.random()*4);
                double x, y;
                x = Math.random()*(WINDOW_WIDTH-100) + 50;
                y = Math.random()*(WINDOW_HEIGHT-100) + 50;
                switch (division){
                        case 0:
                            x = Zuma.WINDOW_WIDTH / 8 - Zuma.WINDOW_WIDTH / 16 -5;
                            break;
                        case 1:
                            y = Zuma.WINDOW_HEIGHT * (1 - 1.0 / 8) + Zuma.WINDOW_HEIGHT / 16 + 5;
                            break;
                        case 2:
                            x = Zuma.WINDOW_WIDTH * (1 - 1.0 / 8) + Zuma.WINDOW_WIDTH  / 16 + 5;
                            break;
                        case 3:
                            y = Zuma.WINDOW_HEIGHT / 8 - Zuma.WINDOW_HEIGHT / 16 - 5;
                            x = Math.random()*(WINDOW_WIDTH-100 - Zuma.WINDOW_WIDTH / 8) + 50 + Zuma.WINDOW_WIDTH / 8 + 5;
                            break;
                }

                Circle [] currCoins = coins.toArray(new Circle[coins.size()]);
                boolean intersected = false;
                for (int i = 0; i < currCoins.length; i++){
                    if (Math.sqrt(Math.pow(y - currCoins[i].getCenterY(), 2) + Math.pow(x - currCoins[i].getCenterX(), 2)) < 40){
                        intersected = true;
                        break;
                    }
                }
                
                if (!intersected){
                    Circle coin = new Circle(x,y,20);
                    coin.setFill(new ImagePattern(new Image("coin.png")));
                    TranslateTransition tt = new TranslateTransition(Duration.seconds(1),coin);
                    tt.setByY(3);
                    tt.setAutoReverse(true);
                    tt.setCycleCount((int) (Math.random()*3) + 3);
                    tt.play();
                    tt.setOnFinished(e->{
                        root.getChildren().remove(coin);   
                        coins.remove(coin);
                    });
                    coins.add(coin);

                    root.getChildren().add(coin);
                }

            }
        }
               
        
      
        time.setText("Time: " + sec);
        
        for (int j = 0; j < shots.size(); j++){
            for (int i=0; i < coins.size();){
                 if (coins.get(i).getBoundsInParent().intersects(shots.get(j).getBoundsInParent())){
                     root.getChildren().remove(coins.get(i));
                     coins.remove(i);
                     points += 3;
                     score.setText("Score: " + points);
                 }
                 else{
                     i++;
                 }
            }
           
        }
        
        if (!balls.isEmpty() && balls.get(0).getBoundsInParent().intersects(moon.getBoundsInParent())) {
            //root.getChildren().remove(balls.get(0));
            //balls.remove(0);
            
            //delete shots and stop
            for (int i = 0; i < shots.size(); i++) {
                Shot shot = shots.get(i);
                root.getChildren().remove(shot);
                shots.remove(shot);
            }
            scene.setOnMouseClicked(null);
            end = true;
            moon.openEyes();
        }
        if (balls.size() != 0 && balls.get(balls.size() - 1).getTranslateY() >= Ball.getRadius() * 2) {
            //max 100 balls
            if (balls_cnt<100){
                Ball ball = new Ball();
                ball.setShield();
                balls.add(ball);
                root.getChildren().add(ball);
                balls_cnt++;
            }
        }

        balls.forEach(ball -> ball.update());

        for (int i = 0; i < shots.size(); i++) {
            Shot shot = shots.get(i);
            shot.update();
            if (shot.getTranslateX() < 0 || shot.getTranslateX() > WINDOW_WIDTH
                    || shot.getTranslateY() < 0 || shot.getTranslateY() > WINDOW_HEIGHT) {
                root.getChildren().remove(shot);
                shots.remove(shot);
            } else {
                for (int j = 0; j < balls.size(); j++) {
                    if (shot.getBoundsInParent().intersects(balls.get(j).getBoundsInParent())) {
                        crashLogic(shot, j);
                        break;
                    }
                }
            }
        }
        
        sun.update();
        //ako su sve kugle unistene ne moze da puca vise
        if (balls.size()==0 && balls_cnt == 100){
            end = true;
            
            //ne treba 
            scene.setOnMouseClicked(null);
        }
        background.addStar(balls.get(0));
        
    }

    //logika za unistavanje pokretnih kugli - NE MENJATI!
    public void crashLogic(Shot shot, int j) {
        
        Ball hitBall = balls.get(j);
        Ball prevBall = (j - 1 >= 0) ? balls.get(j - 1) : null;
        Ball nextBall = (j + 1 < balls.size()) ? balls.get(j + 1) : null;
        if (nextBall != null && shot.getColor().equals(nextBall.getColor())) {
            j++;
            hitBall = nextBall;
            nextBall = (j + 1 < balls.size()) ? balls.get(j + 1) : null;
            prevBall = (j - 1 >= 0) ? balls.get(j - 1) : null;
        }
        if (shot.getColor().equals(hitBall.getColor())
                && ((prevBall != null && shot.getColor().equals(prevBall.getColor()))
                || (nextBall != null && shot.getColor().equals(nextBall.getColor())))) {
            root.getChildren().remove(shot);
            int sameColorCnt = 0, k;
            //next
            for (k = j + 1; k < balls.size() && balls.get(k).getColor().equals(shot.getColor());k++) {
                // counting
            }
            int destroyed = 0;
            int totalCurrScore = 1;
            if (shot.getColor()==Color.YELLOW){
                totalCurrScore = 2;
            }
            for (int m = k - 1; m >= j + 1 ; m--){
                if (!balls.get(m).checkShield()){
                    root.getChildren().remove(balls.get(m));
                    
                    int currScore = 1;  
                    if (balls.get(m).getColor()==Color.YELLOW){
                        currScore = 2;
                    }
                    if (balls.get(m).getShielded()){
                        currScore *= 2 ;
                    }
                    totalCurrScore += currScore;
                    
                    balls.remove(m);   
                    destroyed++;          
                }
                else{
                    balls.get(m).reverse(destroyed);
                }
                
            }
            //previous
            for (k = j; k >= 0 && balls.get(k).getColor().equals(shot.getColor()); k--) {
                if (!balls.get(k).checkShield()){
                    root.getChildren().remove(balls.get(k));
                    
                    int currScore = 1;  
                    if (balls.get(k).getColor()==Color.YELLOW){
                        currScore = 2;
                    }
                    if (balls.get(k).getShielded()){
                        currScore *=2 ;
                    }
                    totalCurrScore += currScore;
                    
                    balls.remove(k);
                    destroyed++;
                }
                else{
                    balls.get(k).reverse(destroyed);
                }
            }
            //reverse
            for (int m = k; m >= 0; m--) {
                balls.get(m).reverse(destroyed);
            }
            
            points += totalCurrScore;
            score.setText("Score: " + points);

        } else {
            if (nextBall != null) {
                shot.becomeMoving(nextBall);
                balls.add(j + 1, shot);
                for (int k = j + 2; k < balls.size(); k++) {
                    Ball hit = balls.get(k);
                    hit.reverse(1);
                }
            } else {
                shot.becomeMoving(balls.get(balls.size() - 1));
                balls.add(balls.size(), shot);
                shot.reverse(1);
            }
        }
        shots.remove(shot);
    }

    public static void makeShot(Shot shot) {
        if (end)
            return;
        shots.add(shot);
        root.getChildren().add(shot);
        Color color = shot.getColor();
        Stop []stops = {
            new Stop(0,Color.WHITE),
            new Stop(1,color)
        };
        RadialGradient rg = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, stops);
        
        shot.getBody().setFill(rg);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
