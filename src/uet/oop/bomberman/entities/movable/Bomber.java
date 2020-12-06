package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import uet.oop.bomberman.entities.Character;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.item.BombItem;
import uet.oop.bomberman.entities.item.FlameItem;
import uet.oop.bomberman.entities.item.Item;
import uet.oop.bomberman.entities.item.SpeedItem;
import uet.oop.bomberman.entities.movable.enemy.*;
import uet.oop.bomberman.entities.still.Portal;
import uet.oop.bomberman.graphics.Sprite;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.entities.Sound.*;

public class Bomber extends Character {
    private int bombRemain;
    private boolean placeBombCommand = false;
    private final List<Bomb> bombs = new ArrayList<>();
    private int radius;
    private KeyCode direction = null;
    private int timeAfterDie = 0;

    private int power;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        setLayer(1);
        setSpeed(2);
        setBombRemain(1);
        setPower(1);
        setRadius(1);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void update() {

        if (direction == KeyCode.LEFT) {
            goLeft();
        }
        if (direction == KeyCode.RIGHT) {
            goRight();
        }
        if (direction == KeyCode.UP) {
            goUp();
        }
        if (direction == KeyCode.DOWN) {
            goDown();
        }
        if (placeBombCommand) {
            placeBomb();
        }
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (!bomb.isAlive()) {
                bombs.remove(bomb);
                bombRemain++;
            }
        }
        //animate();
        if(!isAlive()) {
            timeAfterDie ++;
            die();
        }
    }

    public void handleKeyPressedEvent(KeyCode keyCode) {

        if (keyCode == KeyCode.LEFT || keyCode == KeyCode.RIGHT
                || keyCode == KeyCode.UP || keyCode == KeyCode.DOWN) {
            this.direction = keyCode;
        }
        if (keyCode == KeyCode.SPACE) {
            placeBombCommand = true;
        }
    }

    public void handleKeyReleasedEvent(KeyCode keyCode) {
        if (direction == keyCode) {
            if (direction == KeyCode.LEFT) {
                img = Sprite.player_left.getFxImage();
            }
            if (direction == KeyCode.RIGHT) {
                img = Sprite.player_right.getFxImage();
            }
            if (direction == KeyCode.UP) {
                img = Sprite.player_up.getFxImage();
            }
            if (direction == KeyCode.DOWN) {
                img = Sprite.player_down.getFxImage();
            }
            direction = null;
        }
        if (keyCode == KeyCode.SPACE) {
            placeBombCommand = false;
        }
    }

    public void goLeft() {
        super.goLeft();
        img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, left++, 20).getFxImage();
    }

    public void goRight() {
        super.goRight();
        img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, right++, 20).getFxImage();
    }

    public void goUp() {
        super.goUp();
        img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, up++, 20).getFxImage();
    }

    public void goDown() {
        super.goDown();
        img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, down++, 20).getFxImage();
    }

//    public void placeBomb() {
//        if (bombRemain > 0) {
//            int xB = (int) Math.round((x + 4) / (double) Sprite.SCALED_SIZE);
//            int yB = (int) Math.round((y + 4) / (double) Sprite.SCALED_SIZE);
//            for (Bomb bomb : bombs) {
//                if (xB * Sprite.SCALED_SIZE == bomb.getX() && yB * Sprite.SCALED_SIZE == bomb.getY()) return;
//            }
//            bombs.add(new Bomb(xB, yB, Sprite.bomb.getFxImage()));
//            bombRemain--;
//        }
//    }

    public void placeBomb() {
        if (bombRemain > 0) {
            int xB = (int) Math.round((x + 4) / (double) Sprite.SCALED_SIZE);
            int yB = (int) Math.round((y + 4) / (double) Sprite.SCALED_SIZE);
            for (Bomb bomb : bombs) {
                if (xB * Sprite.SCALED_SIZE == bomb.getX() && yB * Sprite.SCALED_SIZE == bomb.getY()) return;
            }
            bombs.add(new Bomb(xB, yB, Sprite.bomb.getFxImage(), radius));
            bombRemain--;
            dat_bom.play();
            dat_bom.seek(dat_bom.getStartTime());
        }
    }

    public int getBombRemain() {
        return bombRemain;
    }

    public void setBombRemain(int bombRemain) {
        this.bombRemain = bombRemain;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public boolean isAlive() {
        return alive;
    }

    public void die() {
        if (timeAfterDie == 20) cout --;
        if(timeAfterDie <= 45) {
            img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2,
                    Sprite.player_dead3, timeAfterDie, 20).getFxImage();
        }

    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Rectangle getBounds() {
        return new Rectangle(desX + 2, desY +5, Sprite.SCALED_SIZE - 10, Sprite.SCALED_SIZE * 3/4);
    }

    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void handleCollisions(){
        List<Bomb> bombs = bomberman.getBombs();
        Rectangle r1 = bomberman.getBounds();
        //Bomber vs stillObject
        for (Entity stillObject : stillObjects) {
            Rectangle r2 = stillObject.getBounds();
            if (r1.intersects(r2)) {
                if (bomberman.getLayer() == stillObject.getLayer() && stillObject instanceof Item) {
                    if(stillObject instanceof BombItem) {
                        eatItem.play();
                        eatItem.seek(eatItem.getStartTime());
                        startBomb ++;
                        bomberman.setBombRemain(startBomb);
                        stillObjects.remove(stillObject);
                    } else if(stillObject instanceof SpeedItem) {
                        eatItem.play();
                        eatItem.seek(eatItem.getStartTime());
                        startSpeed += 1;
                        bomberman.setSpeed(startSpeed);
                        stillObjects.remove(stillObject);
                    } else if(stillObject instanceof FlameItem) {
                        eatItem.play();
                        eatItem.seek(eatItem.getStartTime());
                        startFlame ++;
                        System.out.println(startFlame);
                        bomberman.setRadius(startFlame);
                        stillObjects.remove(stillObject);
                    }
                    bomberman.stay();
                } else if(bomberman.getLayer() == stillObject.getLayer() && stillObject instanceof Portal) {
                    if(enemies.size() == 0) {
                        //pass level
                        level++;
                        levelUp.play();
                        levelUp.seek(levelUp.getStartTime());
                        check =true;
                    }
                } else if(bomberman.getLayer() >= stillObject.getLayer()) {
                    bomberman.move();
                }
                else {
                    bomberman.stay();
                }
                break;
            }
        }
        //Bomber vs Enemies
        for (Enemy enemy : enemies) {
            Rectangle r2 = enemy.getBounds();
            if (r1.intersects(r2)) {

                bomberman.setAlive(false);
                BomberDie.play();
                BomberDie.seek(BomberDie.getStartTime());
                startBomb = 1;
                startFlame = 1;
                startSpeed = 1;

                if(bomberman.isAlive() == false){
                    if(cout>=0) {
                        Timer count = new Timer();
                        count.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
                                count.cancel();
                            }
                        }, 500, 1);

                        //System.out.println("ddd");
                    }


                }
            }
        }
        //Enemies vs Bombs
        for (Enemy enemy : enemies) {
            Rectangle r2 = enemy.getBounds();
            for (Bomb bomb : bombs) {
                Rectangle r3 = bomb.getBounds();
                if (!bomb.isAllowedToPassThrough(enemy) && r2.intersects(r3)) {
                    enemy.stay();
                    break;
                }
            }
        }
        //Enemies vs StillObjects
        for (Enemy enemy : enemies) {
            Rectangle r2 = enemy.getBounds();
            for (Entity stillObject : stillObjects) {
                Rectangle r3 = stillObject.getBounds();
                if (r2.intersects(r3)) {
                    if (enemy.getLayer() >= stillObject.getLayer()) {
                        enemy.move();
                    } else {
                        enemy.stay();
                    }
                    break;
                }
            }
        }
    }
    public void checkCollisionFlame() {
        //if(explosionList != null){
        for (int i = 0; i < flameList.size(); i++) {
            Rectangle r1 = flameList.get(i).getBounds();
            for (int j = 0; j < stillObjects.size(); j++) {
                Rectangle r2 = stillObjects.get(j).getBounds();
                if (r1.intersects(r2) && !(stillObjects.get(j) instanceof Item))
                    stillObjects.get(j).setAlive(false);
            }
            for (int j = 0; j < enemies.size(); j++) {
                Rectangle r2 = enemies.get(j).getBounds();
                if (r1.intersects(r2)){
                    if(enemies.get(j) instanceof Kondoria ||enemies.get(j) instanceof Doll) score+=5;
                    else if(enemies.get(j) instanceof Oneal) score+=2;
                    else score+=1;
                    enemyDie.play();
                    enemyDie.seek(levelUp.getStartTime());
                    enemies.get(j).setAlive(false);
                }
            }
            Rectangle r2 = bomberman.getBounds();
            if (r1.intersects(r2)) {
                bomberman.setAlive(false);
                BomberDie.play();
                BomberDie.seek(BomberDie.getStartTime());
                startBomb = 1;
                startFlame = 1;
                startSpeed = 1;
                if (bomberman.isAlive() == false) {
                    if (cout >= 0) {
                        Timer count = new Timer();
                        count.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
                                count.cancel();
                            }
                        }, 800, 1);
                    }

                }
            }
        }
    }


}
