package uet.oop.bomberman;

//import com.sun.webkit.dom.EntityImpl;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.event.MouseEvent;
import java.io.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {

    public static final int WIDTH = 40;
    public static final int HEIGHT = 30;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    Map m = new Map();




    @Override
    public void start(Stage stage) throws IOException {
        // Tao Canvas
        m.createMap(stillObjects);
        canvas = new Canvas(Sprite.SCALED_SIZE * m.width, Sprite.SCALED_SIZE * m.height);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        // Tao scene
        Scene scene = new Scene(root);

//        scene.addEventFilter(KeyEvent.KEY_PRESSED, key);
        // Them scene vao stage
        stage.setTitle("Bomberman Game");
        stage.setScene(scene);
        stage.show();



        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case UP: case W:
                        if(m.checkUp(bomberman)) {
                            System.out.println("bomberUp");
                            bomberman.moveUp();
                        }
                        break;
                    case DOWN: case S:
                        if(m.checkDown(bomberman)) {
                            System.out.println("bomberDown");
                            bomberman.moveDown();
                        }
                        break;
                    case LEFT: case A:
                        if(m.checkLeft(bomberman)) {
                            System.out.println("bomberLeft");
                            bomberman.moveLeft();
                        }
                        break;
                    case RIGHT: case D:
                        if(m.checkRight(bomberman)) {
                            System.out.println("bomberRight");
                            bomberman.moveRight();
                        }
                        break;
                    case SPACE:
                        bomberman.setBomb(bomberman.getX() / 32, bomberman.getY() / 32, m, stillObjects);
                        break;
                    default:
                        break;
                }
            }
        });
        entities.add(bomberman);
        addBalloom();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();
    }

    public void addBalloom() {
        for(Entity entity : m.getObjects()) {
            if (entity instanceof Balloom) {
                entities.add(entity);
            }
        }
    }

    public void update() {
        entities.forEach(entity -> entity.update(m));
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
