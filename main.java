import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.Random;

// this is parent class for all game things
class GameObject {
    ImageView image;
    int posX;
    int posY;
    int width;
    int height;

    // constructor to make game object
    public GameObject(String imagePath, int startX, int startY, int objWidth, int objHeight) {
        image = new ImageView(new Image(imagePath));
        image.setFitWidth(objWidth);
        image.setFitHeight(objHeight);
        posX = startX;
        posY = startY;
        width = objWidth;
        height = objHeight;
        image.setLayoutX(posX);
        image.setLayoutY(posY);
    }

    // move object left or right
    public void moveObject(int speed) {
        posX = posX + speed;
        image.setLayoutX(posX);
    }

    // get the image
    public ImageView getImage() {
        return image;
    }

    // get x position
    public int getXPosition() {
        return posX;
    }

    // set x position
    public void setXPosition(int newX) {
        posX = newX;
        image.setLayoutX(posX);
    }

    // set y position
    public void setYPosition(int newY) {
        posY = newY;
        image.setLayoutY(posY);
    }
}

// this is the player class
class Player extends GameObject {
    boolean isJumping;
    double jumpSpeed;
    double fallSpeed;

    // make player
    public Player(String imagePath, int startX, int startY) {
        super(imagePath, startX, startY, 40, 40);
        isJumping = false;
        jumpSpeed = 0;
        fallSpeed = 0.25;
    }

    // make player jump
    public void jump() {
        if (isJumping == false) {
            isJumping = true;
            jumpSpeed = -9;
            fallSpeed = 0.15;
        }
    }

    // update player position when jumping
    public void updatePosition(int groundLevel) {
        if (isJumping == true) {
            jumpSpeed = jumpSpeed + fallSpeed;
            posY = (int)(posY + jumpSpeed);

            // check if player hit ground
            if (posY >= groundLevel - 40) {
                posY = groundLevel - 40;
                isJumping = false;
                jumpSpeed = 0;
                fallSpeed = 0.25;
            }

            image.setLayoutY(posY);
        }
    }

    // check if jumping
    public boolean checkJumping() {
        return isJumping;
    }

    // stop jump
    public void stopJumping() {
        isJumping = false;
        jumpSpeed = 0;
        fallSpeed = 0.25;
    }
}

// this is ground tile class
class GroundTile extends GameObject {
    // make ground tile
    public GroundTile(String imagePath, int startX, int startY, int tileSize) {
        super(imagePath, startX, startY, tileSize, tileSize);
    }

    // scroll ground tile
    public void scrollTile(int scrollSpeed, int maxWidth, int totalTiles, int tileSize) {
        moveObject(scrollSpeed);
        // if tile goes off screen move it back
        if (posX < -tileSize) {
            posX = posX + (tileSize * totalTiles);
            image.setLayoutX(posX);
        }
    }
}

// this is obstacle class
class Obstacle {
    Pane obstacleBox;
    int obstacleX;
    int obstacleY;
    int obstacleHeight;
    int blockSize;

    // make obstacle
    public Obstacle(String imagePath, int startX, int startY, int size) {
        obstacleBox = new Pane();
        obstacleX = startX;
        obstacleY = startY;
        blockSize = size;
        Random rand = new Random();
        obstacleHeight = rand.nextInt(2) + 1;

        createObstacle(imagePath);
        obstacleBox.setLayoutX(obstacleX);
        obstacleBox.setLayoutY(obstacleY);
    }

    // create obstacle blocks
    public void createObstacle(String imagePath) {
        obstacleBox.getChildren().clear();
        for (int i = 0; i < obstacleHeight; i++) {
            ImageView blockPart = new ImageView(new Image(imagePath));
            blockPart.setFitWidth(blockSize);
            blockPart.setFitHeight(blockSize);
            blockPart.setLayoutY(-i * blockSize);
            obstacleBox.getChildren().add(blockPart);
        }
    }

    // move obstacle
    public void moveObstacle(int speed) {
        obstacleX = obstacleX + speed;
        obstacleBox.setLayoutX(obstacleX);
    }

    // reset obstacle position
    public void resetObstacle(String imagePath, int windowWidth) {
        Random rand = new Random();
        obstacleHeight = rand.nextInt(2) + 1;
        createObstacle(imagePath);
        int randomGap = 120 + rand.nextInt(200);
        obstacleX = windowWidth + randomGap;
        obstacleBox.setLayoutX(obstacleX);
    }

    // get obstacle box
    public Pane getObstacleBox() {
        return obstacleBox;
    }

    // get x position
    public int getObstacleX() {
        return obstacleX;
    }
}

// main game class
public class Main extends Application {

    // window size
    int windowWidth = 800;
    int windowHeight = 400;
    int tileSize = 40;

    Pane gameScreen = new Pane();

    // game objects
    Player player;
    Obstacle obstacle;
    ArrayList<GroundTile> groundTiles = new ArrayList<>();

    // game state
    boolean isGameOver = false;

    // score variables
    int score = 0;
    Text scoreText;
    ImageView coinImage;

    // game over stuff
    Text gameOverText;
    Button restartButton;

    int groundLevel;

    // fonts
    Font smallFont;
    Font bigFont;
    Font buttonFont;

    @Override
    public void start(Stage stage) {

        // load fonts
        smallFont = Font.loadFont("file:Assets/pixle.ttf", 24);
        bigFont = Font.loadFont("file:Assets/pixle.ttf", 40);
        buttonFont = Font.loadFont("file:Assets/pixle.ttf", 22);

        // create game scene
        Scene gameScene = new Scene(gameScreen, windowWidth, windowHeight, Color.SKYBLUE);

        // setup game elements
        createGround();
        createPlayer();
        createObstacle();
        createScoreDisplay();

        // handle spacebar for jump
        gameScene.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("SPACE") && isGameOver == false) {
                player.jump();
            }
        });

        // game loop - runs every frame
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                // check if player hit obstacle
                if (isGameOver == false && player.getImage().getBoundsInParent().intersects(obstacle.getObstacleBox().getBoundsInParent())) {
                    isGameOver = true;
                    showGameOver();
                }

                // stop game if dead
                if (isGameOver == true) {
                    return;
                }

                // update game
                player.updatePosition(groundLevel);
                updateGround();
                updateObstacle();

                // increase score
                score = score + 1;
                scoreText.setText(String.valueOf(score));
            }
        }.start();

        stage.setScene(gameScene);
        stage.setTitle("HopNRun");
        stage.show();
    }

    // create ground tiles
    private void createGround() {
        int numberOfTiles = windowWidth / tileSize;
        groundLevel = windowHeight - tileSize;

        for (int i = 0; i < numberOfTiles + 2; i++) {
            GroundTile tile = new GroundTile("file:Assets/tile.png", i * tileSize, groundLevel, tileSize);
            groundTiles.add(tile);
            gameScreen.getChildren().add(tile.getImage());
        }
    }

    // update ground scrolling
    private void updateGround() {
        for (GroundTile tile : groundTiles) {
            tile.scrollTile(-2, -tileSize, groundTiles.size(), tileSize);
        }
    }

    // create player
    private void createPlayer() {
        player = new Player("file:Assets/player.png", 100, windowHeight - tileSize - 40);
        gameScreen.getChildren().add(player.getImage());
    }

    // create obstacle
    private void createObstacle() {
        obstacle = new Obstacle("file:Assets/tile.png", windowWidth + 150, groundLevel - 40, tileSize);
        gameScreen.getChildren().add(obstacle.getObstacleBox());
    }

    // update obstacle movement
    private void updateObstacle() {
        obstacle.moveObstacle(-2);

        // if obstacle goes off screen reset it
        if (obstacle.getObstacleX() < -tileSize) {
            obstacle.resetObstacle("file:Assets/tile.png", windowWidth);
        }
    }

    // create score display
    private void createScoreDisplay() {
        // coin icon
        coinImage = new ImageView(new Image("file:Assets/coin.png"));
        coinImage.setFitWidth(30);
        coinImage.setFitHeight(30);
        coinImage.setLayoutX(windowWidth - 130);
        coinImage.setLayoutY(20);

        // score text
        scoreText = new Text("0");
        scoreText.setFont(smallFont);
        scoreText.setFill(Color.BLACK);
        scoreText.setLayoutX(windowWidth - 90);
        scoreText.setLayoutY(42);

        gameScreen.getChildren().addAll(coinImage, scoreText);
    }

    // show game over screen
    private void showGameOver() {
        // game over text
        gameOverText = new Text("GAME OVER");
        gameOverText.setFont(bigFont);
        gameOverText.setFill(Color.RED);
        gameOverText.setLayoutX(windowWidth / 2 - 140);
        gameOverText.setLayoutY(windowHeight / 2 - 20);

        // restart button
        restartButton = new Button("RESTART");
        restartButton.setFont(buttonFont);
        restartButton.setLayoutX(windowWidth / 2 - 60);
        restartButton.setLayoutY(windowHeight / 2 + 20);
        restartButton.setPrefWidth(120);
        restartButton.setOnAction(event -> restartGame());

        gameScreen.getChildren().addAll(gameOverText, restartButton);
    }

    // restart game
    private void restartGame() {
        // reset game state
        isGameOver = false;
        player.stopJumping();
        player.setXPosition(100);
        player.setYPosition(groundLevel - 40);

        // reset obstacle
        obstacle.resetObstacle("file:Assets/tile.png", windowWidth);
        obstacle.getObstacleBox().setLayoutY(groundLevel - 40);

        // reset score
        score = 0;
        scoreText.setText("0");

        // remove game over elements
        gameScreen.getChildren().remove(gameOverText);
        gameScreen.getChildren().remove(restartButton);

        // fix background color
        gameScreen.setStyle("-fx-background-color: skyblue;");
    }

    public static void main(String[] args) {
        launch();
    }
}