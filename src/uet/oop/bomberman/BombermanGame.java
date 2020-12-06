package uet.oop.bomberman;

import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import uet.oop.bomberman.board.FileLevelLoader;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.entities.movable.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static uet.oop.bomberman.entities.Sound.soundtrack;

public class BombermanGame extends Application {

    public static int WIDTH;
    public static int HEIGHT;
    public static int level = 1;
    public static GraphicsContext gc;
    private Canvas canvas;
    public static Scanner scanner;
    public static final List<Enemy> enemies = new ArrayList<>();
    public static final List<Entity> stillObjects = new ArrayList<>();
    public static final List<Flame> flameList = new ArrayList<>();
    public static int startBomb = 1;
    public static int startSpeed = 5;
    public static int startFlame = 1;
    public static int time = 400;
    public static int score =0;
    public static int xStart;
    public static int yStart;
    public static Bomber bomberman;
    public static int cout = 3;
    public static int countTime =0;
    public static boolean check = false;
    public static AnchorPane ro = new AnchorPane();
    public static JPANEL jpanel = new JPANEL();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        FileLevelLoader.createMap();
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT+25);
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        ro.getChildren().addAll(new Rectangle(2,3));
        jpanel.setPanel();
        root.getChildren().add(ro);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        soundtrack.play();
        soundtrack.seek(soundtrack.getStartTime());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (check == true) {
                    FileLevelLoader.createMap();
                    check = false;
                }
                if (cout == 0) this.stop();
                render();
                update();
            }
        };
        timer.start();
        scene.setOnKeyPressed(event -> bomberman.handleKeyPressedEvent(event.getCode()));
        scene.setOnKeyReleased(event -> bomberman.handleKeyReleasedEvent(event.getCode()));
  }

    public void update() {
        if(countTime% 60==0){
            time--;
        }
        jpanel.setTimes(time);
        jpanel.setPoint(score);
        jpanel.setLives(cout);
        for (int i = 0; i < enemies.size(); i++)
            enemies.get(i).update();
        for (int i = 0; i < flameList.size(); i++)
            flameList.get(i).update();
        bomberman.update();
        List<Bomb> bombs = bomberman.getBombs();
        for (Bomb bomb : bombs) {
            bomb.update();
        }

        for (int i = 0; i < stillObjects.size(); i++)
            stillObjects.get(i).update();
        bomberman.handleCollisions();
        bomberman.checkCollisionFlame();

    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = stillObjects.size() - 1; i >= 0; i--) {
            stillObjects.get(i).render(gc);
        }
        enemies.forEach(g -> g.render(gc));
        List<Bomb> bombs = bomberman.getBombs();
        for (Bomb bomb : bombs) {
            bomb.render(gc);
        }
        coutTime();
        bomberman.render(gc);
        flameList.forEach(g -> g.render(gc));
    }
    public void coutTime() {
        if ( countTime<200*60) {
            countTime++;
        }
    }
}