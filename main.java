


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.image.*;
import javafx.scene.input.*;

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
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,2,0,0,0,0,0,0},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    public float x = 100;
    public float y = 350;
     
    boolean onGround=false;

    ImageView player;
    Pane pane = new Pane();
    Image tileimage = new Image("file:Assets/tile.png");
    Image obsimage = new Image("file:Assets/tile.png");
    public void start(Stage stage) {

        
        Scene scene = new Scene(pane, 960, 540);

        loadmap();  

        
        Image img = new Image("file:Assets/player.png"); // load pic
        player = new ImageView(img); 

        player.setLayoutX(x); // player x-pos
        player.setLayoutY(y); // player y-pos
        
	pane.getChildren().add(player);

	movement(scene);

        stage.setScene(scene);
        stage.show();	
    }

    
    public void loadmap(){
        for(int row=0; row < map.length; row++){
            for(int col = 0; col < map[row].length; col++){
                if(map[row][col]==1){
                    ImageView tile = new ImageView(tileimage);
                    tile.setLayoutX(col * TILE_SIZE);
                    tile.setLayoutY(row * TILE_SIZE);
                    pane.getChildren().add(tile);
                
                }
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

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == 1 || map[row][col] == 2) {
                    double tileX = col * TILE_SIZE;
                    double tileY = row * TILE_SIZE;

                    boolean touches=player.getBoundsInParent().intersects(tileX, tileY, TILE_SIZE, TILE_SIZE); 
                        
                    if (touches) {
                            player.setLayoutY(tileY - player.getImage().getHeight());
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
            }

            if (e.getCode() == KeyCode.D) {
                x += 10;   // move RIGHT
            }

            player.setLayoutX(x); // update position
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
