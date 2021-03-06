package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Character;
import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

import static uet.oop.bomberman.entities.Sound.no_bom;
import static uet.oop.bomberman.entities.Sound.soundtrack;

public class Bomb extends Character {
    private int timeCounter = 0;
    int radius;
    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setLayer(2);
        this.radius = 1;
    }

    public Bomb(int xUnit, int yUnit, Image img, int radius) {
        super(xUnit, yUnit, img);
        setLayer(2);
        this.radius = radius;
    }
    @Override
    public void update() {
        if (timeCounter ++ == 120) {
            explodeUpgrade();
        }
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, timeCounter, 60).getFxImage();


    }

    public void explodeUpgrade() {
        Flame e = new Flame(x, y);
        e.setRadius(radius);
        e.render_explosion();
        alive = false;
        no_bom.play();
        no_bom.seek(no_bom.getStartTime());
    }


    public boolean isAllowedToPassThrough(Character e) {
        Rectangle r1 = getBounds();
        Rectangle r2;
        if (e instanceof Bomber) {
            Bomber bomber = (Bomber) e;
            r2 = new Rectangle(bomber.getX() + 4, bomber.getY() + 4, Sprite.SCALED_SIZE * 3 / 4, Sprite.SCALED_SIZE * 3 / 4);
        } else {
            r2 = new Rectangle(e.getX(), e.getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        }
        return r1.intersects(r2);
    }
}