package uet.oop.bomberman.entities.movable.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.movable.enemy.ai.AIHigh;
import uet.oop.bomberman.entities.movable.enemy.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.bomberman;

public class Doll extends Enemy {

    public Doll(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setLayer(1);
        setSpeed(2);
        _ai = new AIMedium(bomberman,this);
        direction =_ai.caculateDirection();
        alive = true;
    }
    int n=0;
    @Override
    public void update() {
        if (isAlive()) {
            if (direction == 3) goLeft();
            if (direction == 1) goRight();
            if (direction == 0) goUp();
            if (direction == 2) goDown();
            if(n==32) {
                direction = _ai.caculateDirection();
                n = 0;
            }
        } else if (animated < 30) {
            animated++;
            img = Sprite.doll_dead.getFxImage();
        } else
            BombermanGame.enemies.remove(this);
        //
    }

    public void goLeft() {
        super.goLeft();
        img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, left++, 18).getFxImage();
    }

    public void goRight() {
        super.goRight();
        img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, right++, 18).getFxImage();
    }

    public void goUp() {
        super.goUp();
        img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, up++, 18).getFxImage();
    }

    public void goDown() {
        super.goDown();
        img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, down++, 18).getFxImage();
    }

    @Override
    public void stay() {
        super.stay();
        direction = _ai.caculateDirection();
    }
}
