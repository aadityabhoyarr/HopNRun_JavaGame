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
        {0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,1,2,1,2,1,0,0,0,0,2,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    public float x = 100;
    public float y = 400;
     

    ImageView player;
    Pane pane = new Pane();
    
    Image tileImage = new Image("file:Assets/tile.png");
    Image coinImage = new Image("file:Assets/coin.png");

    public void start(Stage stage) {

        
        Scene scene = new Scene(pane, 960, 540);
	scene.setFill(javafx.scene.paint.Color.SKYBLUE);

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
			// ground tile
                	ImageView tile = new ImageView(tileImage);
                	tile.setLayoutX(col * TILE_SIZE);
                	tile.setLayoutY(row * TILE_SIZE);
                	pane.getChildren().add(tile);
                } 

		if(map[row][col]==2){
			//coin tile
			ImageView coin = new ImageView(coinImage);
			coin.setLayoutX(col * TILE_SIZE);
			coin.setLayoutY(row * TILE_SIZE);
			pane.getChildren().add(coin);
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

	    if (e.getCode() == KeyCode.W){
		//y += 80;
		//sleep .5
	    }

            player.setLayoutX(x); // update position
            player.setLayoutY(y); // update position
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
