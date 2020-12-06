package uet.oop.bomberman.board;

import uet.oop.bomberman.Layer;
import uet.oop.bomberman.entities.item.FlameItem;
import uet.oop.bomberman.entities.item.SpeedItem;
import uet.oop.bomberman.entities.item.BombItem;
import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.entities.movable.enemy.Balloom;
import uet.oop.bomberman.entities.movable.enemy.Doll;
import uet.oop.bomberman.entities.movable.enemy.Kondoria;
import uet.oop.bomberman.entities.movable.enemy.Oneal;
import uet.oop.bomberman.entities.still.Brick;
import uet.oop.bomberman.entities.still.Grass;
import uet.oop.bomberman.entities.still.Portal;
import uet.oop.bomberman.entities.still.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.net.URL;
import java.util.Scanner;
import java.util.StringTokenizer;

import static uet.oop.bomberman.BombermanGame.*;


public class FileLevelLoader {
    public static void createMap() {

        try {
            scanner = new Scanner(new FileReader("res/levels/level" + level + ".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner.nextInt();
        HEIGHT = scanner.nextInt() ;
        WIDTH = scanner.nextInt() ;
        scanner.nextLine();
        for (int i = 0; i < HEIGHT; i++) {
            String r = scanner.nextLine();
            for (int j = 0; j < WIDTH; j++) {
                if (r.charAt(j) == '#') {
                    stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));
                } else {
                    stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    if (r.charAt(j) == '*') {
                        stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    }
                    if (r.charAt(j) == 'x') {
                        stillObjects.add(new Portal(j, i, Sprite.portal.getFxImage()));
                        stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    }
                    if (r.charAt(j) == '1') {
                        enemies.add(new Balloom(j, i, Sprite.balloom_left1.getFxImage()));
                    }
                    if (r.charAt(j) == '2') {
                        enemies.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
                    }
                    if (r.charAt(j) == '3') {
                        enemies.add(new Doll(j, i, Sprite.doll_left1.getFxImage()));
                    }
                    if (r.charAt(j) == '4') {
                        enemies.add(new Kondoria(j, i, Sprite.doll_left1.getFxImage()));
                    }
                    if (r.charAt(j) == 'b') {
                        stillObjects.add(new BombItem(j, i, Sprite.powerup_bombs.getFxImage()));
                        stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    }
                    if (r.charAt(j) == 'f') {
                        stillObjects.add(new FlameItem(j, i, Sprite.powerup_flames.getFxImage()));
                        stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    }
                    if (r.charAt(j) == 's') {
                        stillObjects.add(new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()));
                        stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    }
                    if (r.charAt(j) == 'p') {
                        bomberman = new Bomber(j, i, Sprite.player_right.getFxImage());
                        xStart = j;
                        yStart = i;
                    }
                }
            }
        }
        stillObjects.sort(new Layer());
    }

/*
    public void createMap(String path) {
        try {
            URL absPath = FileLevelLoader.class.getResource("/levels/Level" + path + ".txt");
            BufferedReader r = null;
            try {
                r = new BufferedReader(
                        new InputStreamReader(absPath.openStream()));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            String data = r.readLine();
            StringTokenizer s = new StringTokenizer(data);
            level = Integer.parseInt(s.nextToken());
            HEIGHT = Integer.parseInt(s.nextToken())+1;
            WIDTH = Integer.parseInt(s.nextToken());
            for (int i = 0; i < HEIGHT; i++) {
                r = r.readLine();
                for (int j = 0; j < WIDTH; j++) {
                    if (r.charAt(j) == '#') {
                        stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));
                    } else {
                        stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        if (r.charAt(j) == '*') {
                            stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        }
                        if (r.charAt(j) == 'x') {
                            stillObjects.add(new Portal(j, i, Sprite.portal.getFxImage()));
                            stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        }
                        if (r.charAt(j) == '1') {
                            enemies.add(new Balloom(j, i, Sprite.balloom_left1.getFxImage()));
                        }
                        if (r.charAt(j) == '2') {
                            enemies.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
                        }
                        if (r.charAt(j) == '3') {
                            enemies.add(new Doll(j, i, Sprite.doll_left1.getFxImage()));
                        }
                        if (r.charAt(j) == 'b') {
                            stillObjects.add(new BombItem(j, i, Sprite.powerup_bombs.getFxImage()));
                            stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        }
                        if (r.charAt(j) == 'f') {
                            stillObjects.add(new FlameItem(j, i, Sprite.powerup_flames.getFxImage()));
                            stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        }
                        if (r.charAt(j) == 's') {
                            stillObjects.add(new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()));
                            stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        }
                        if (r.charAt(j) == 'p') {
                            myBomber = new Bomber(j, i, Sprite.player_right.getFxImage());
                            xStart = j;
                            yStart = i;
                        }
                    }
                }
            }
            stillObjects.sort(new Layer());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
            /*String[] lineTiles_ = new String[HEIGHT];
            for (int i = 1; i < HEIGHT; i++) {
                lineTiles_[i] = r.readLine().substring(0,WIDTH);
            }
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
