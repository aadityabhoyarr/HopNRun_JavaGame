import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Random;


// ------------------------ BASE CLASS (player, ground tile) ------------------------
class GameObject {
    ImageView image;
    int x, y, w, h;

    public GameObject(String img, int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        image = new ImageView(new Image(img));
        image.setFitWidth(w);
        image.setFitHeight(h);
        image.setLayoutX(x);
        image.setLayoutY(y);
    }

    void moveX(int speed) {
        x += speed;
        image.setLayoutX(x);
    }

    void setX(int newX) {
        x = newX;
        image.setLayoutX(x);
    }

    void setY(int newY) {
        y = newY;
        image.setLayoutY(y);
    }
}


// ------------------------ PLAYER ------------------------
class Player extends GameObject {
    boolean jumping = false;
    double jumpSpeed = 0;
    double gravity = 0.25;

    public Player(String img, int x, int y) {
        super(img, x, y, 40, 40);
    }

    void jump() {
        if (!jumping) {
            jumping = true;
            jumpSpeed = -9;
            gravity = 0.15;
        }
    }

    void update(int groundY) {
        if (jumping) {
            jumpSpeed += gravity;
            y += jumpSpeed;

            if (y >= groundY - h) {
                y = groundY - h;
                jumping = false;
                jumpSpeed = 0;
                gravity = 0.25;
            }

            image.setLayoutY(y);
        }
    }

    void resetJump() {
        jumping = false;
        jumpSpeed = 0;
        gravity = 0.25;
    }
}


// ------------------------ GROUND TILE ------------------------
class GroundTile extends GameObject {
    public GroundTile(String img, int x, int y, int size) {
        super(img, x, y, size, size);
    }

    void scroll(int speed, int totalTiles, int tileSize) {
        moveX(speed);

        if (x < -tileSize) {
            x += tileSize * totalTiles;
            image.setLayoutX(x);
        }
    }
}


// ------------------------ OBSTACLE ------------------------
class Obstacle {
    Pane box = new Pane();
    int x, y, size, height;

    public Obstacle(String img, int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;

        resetHeight(img);
        box.setLayoutX(x);
        box.setLayoutY(y);
    }

    void resetHeight(String img) {
        height = new Random().nextInt(2) + 1; // 1 or 2 blocks
        box.getChildren().clear();

        for (int i = 0; i < height; i++) {
            ImageView block = new ImageView(new Image(img));
            block.setFitWidth(size);
            block.setFitHeight(size);
            block.setLayoutY(-i * size);
            box.getChildren().add(block);
        }
    }

    void move(int speed) {
        x += speed;
        box.setLayoutX(x);
    }

    void reset(String img, int screenWidth) {
        resetHeight(img);
        x = screenWidth + (120 + new Random().nextInt(200));
        box.setLayoutX(x);
    }
}


// ------------------------ MAIN GAME ------------------------
public class Main extends Application {

    int WIDTH = 800;
    int HEIGHT = 400;
    int TILE = 40;
    int groundY;

    Pane screen = new Pane();
    Scene scene;   // <-- FIXED (now global variable)

    Player player;
    Obstacle obstacle;
    ArrayList<GroundTile> ground = new ArrayList<>();

    boolean gameOver = false;

    int score = 0;
    Text scoreText;
    ImageView coinIcon;

    Text gameOverText;
    Button restartBtn;

    Font smallFont, bigFont, buttonFont;


    @Override
    public void start(Stage stage) {

        // fonts
        smallFont = Font.loadFont("file:Assets/pixle.ttf", 24);
        bigFont = Font.loadFont("file:Assets/pixle.ttf", 40);
        buttonFont = Font.loadFont("file:Assets/pixle.ttf", 22);

        // scene  (stored globally)
        scene = new Scene(screen, WIDTH, HEIGHT, Color.SKYBLUE);

        // create game objects
        createGround();
        createPlayer();
        createObstacle();
        createScore();

        // jump
        scene.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("SPACE") && !gameOver) {
                player.jump();
            }
        });

        // game loop
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                // 1. collision
                if (!gameOver &&
                    player.image.getBoundsInParent().intersects(
                    obstacle.box.getBoundsInParent())) {
                    gameOver = true;
                    showGameOver();
                }

                if (gameOver) return;

                // 2. update
                player.update(groundY);
                updateGround();
                updateObstacle();

                // 3. score
                score++;
                scoreText.setText(String.valueOf(score));
            }
        }.start();

        stage.setScene(scene);
        stage.setTitle("HopNRun");
        stage.show();
    }


    // ------------------------ CREATE ELEMENTS ------------------------

    void createGround() {
        groundY = HEIGHT - TILE;
        int tilesNeeded = WIDTH / TILE + 2;

        for (int i = 0; i < tilesNeeded; i++) {
            GroundTile t = new GroundTile("file:Assets/tile.png", i * TILE, groundY, TILE);
            ground.add(t);
            screen.getChildren().add(t.image);
        }
    }

    void updateGround() {
        for (GroundTile t : ground)
            t.scroll(-2, ground.size(), TILE);
    }

    void createPlayer() {
        player = new Player("file:Assets/player.png", 100, groundY - 40);
        screen.getChildren().add(player.image);
    }

    void createObstacle() {
        obstacle = new Obstacle("file:Assets/tile.png", WIDTH + 150, groundY - 40, TILE);
        screen.getChildren().add(obstacle.box);
    }

    void updateObstacle() {
        obstacle.move(-2);

        if (obstacle.x < -TILE)
            obstacle.reset("file:Assets/tile.png", WIDTH);
    }

    void createScore() {
        coinIcon = new ImageView(new Image("file:Assets/coin.png"));
        coinIcon.setFitWidth(30);
        coinIcon.setFitHeight(30);
        coinIcon.setLayoutX(WIDTH - 130);
        coinIcon.setLayoutY(20);

        scoreText = new Text("0");
        scoreText.setFont(smallFont);
        scoreText.setFill(Color.BLACK);
        scoreText.setLayoutX(WIDTH - 90);
        scoreText.setLayoutY(42);

        screen.getChildren().addAll(coinIcon, scoreText);
    }


    // ------------------------ GAME OVER ------------------------

    void showGameOver() {
        gameOverText = new Text("GAME OVER");
        gameOverText.setFont(bigFont);
        gameOverText.setFill(Color.RED);
        gameOverText.setLayoutX(WIDTH / 2 - 140);
        gameOverText.setLayoutY(HEIGHT / 2 - 20);

        restartBtn = new Button("RESTART");
        restartBtn.setFont(buttonFont);
        restartBtn.setLayoutX(WIDTH / 2 - 60);
        restartBtn.setLayoutY(HEIGHT / 2 + 20);
        restartBtn.setPrefWidth(120);
        restartBtn.setOnAction(e -> restartGame());

        screen.getChildren().addAll(gameOverText, restartBtn);
    }

    void restartGame() {
        gameOver = false;

        player.resetJump();
        player.setX(100);
        player.setY(groundY - 40);

        obstacle.reset("file:Assets/tile.png", WIDTH);
        obstacle.box.setLayoutY(groundY - 40);

        score = 0;
        scoreText.setText("0");

        screen.getChildren().removeAll(gameOverText, restartBtn);

        screen.setStyle("-fx-background-color: skyblue;"); // this is because whne the game resart it should be blue again
    }


    public static void main(String[] args) {
        launch();
    }
}
