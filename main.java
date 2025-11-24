import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.Color;

public class Main extends Application {
    //tiles
    final int TILE_SIZE = 40;
    
    //map using tile
    int[][] map = {
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,2,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,2,0,0,0,1,2,1,2,1,0,0,0,0,0,1,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    public float x = 100;
    public float y = 400;
     
    boolean onGround=false;

    ImageView player;
    Pane pane = new Pane();
    Image tileimage = new Image("file:Assets/tile.png");
    Image obsimage = new Image("file:Assets/coin.png");
    

    public void start(Stage stage) {

        
        Scene scene = new Scene(pane, 960, 540);
	scene.setFill(Color.SKYBLUE);
       
	loadmap();  // for map loading
       

        
        Image playerimage = new Image("file:Assets/player.png"); // load pic
        player = new ImageView(playerimage); 

        player.setLayoutX(x); // player x-pos
        player.setLayoutY(y); // player y-pos
        
	pane.getChildren().add(player);

	movement(scene); // player movement

        stage.setScene(scene);
        stage.show();	
    }

    
    public void loadmap(){
        for(int row=0; row < map.length; row++){
            for(int col = 0; col < map[row].length; col++){
                
		// Tile 
		if(map[row][col]==1){
                    ImageView tile = new ImageView(tileimage);
                    tile.setLayoutX(col * TILE_SIZE);
                    tile.setLayoutY(row * TILE_SIZE);
                    pane.getChildren().add(tile);
                }

		// Coin
                else if(map[row][col]==2){
                    ImageView obs = new ImageView(obsimage);
                    obs.setLayoutX(col * TILE_SIZE);
                    obs.setLayoutY(row * TILE_SIZE);
                    pane.getChildren().add(obs);
                } 
            }
        }
    }
    
    void checkCollision() {

    onGround = false;

    double playerX = player.getLayoutX();
    double playerY = player.getLayoutY();
    double playerW = player.getImage().getWidth();
    double playerH = player.getImage().getHeight();

    for (int row = 0; row < map.length; row++) {
        for (int col = 0; col < map[row].length; col++) {

            if (map[row][col] == 1) {  // collide only with tile (1)

                double tileX = col * TILE_SIZE;
                double tileY = row * TILE_SIZE;

                // Check only **vertical (ground) collision**
                boolean horizontalOverlap =
                        playerX + playerW > tileX &&
                        playerX < tileX + TILE_SIZE;

                boolean feetTouch =
                        playerY + playerH <= tileY &&
                        playerY + playerH + 10 >= tileY; // small tolerance

                if (horizontalOverlap && feetTouch) {
                    player.setLayoutY(tileY - playerH);
                    onGround = true;
                    return;
                }
            }
        }
    }
}



     public void movement(Scene scene){

        scene.setOnKeyPressed((KeyEvent e) -> {

            if (e.getCode() == KeyCode.A) {
                x -= 10;   // move LEFT
		//x.setRotation(90);
            }

            if (e.getCode() == KeyCode.D) {
                x += 10;   // move RIGHT
            }

            player.setLayoutX(x); // update position
 	    checkCollision();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
