//import javax.swing.text.html.ImageView; //wtf is this swing??? per q 😭
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.image.*;
import javafx.scene.input.*;

public class Main extends Application {
    //tiles
    int TILE_SIZE = 40;

    //map using tile
    int[][] map = {
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1}
    };

    public float x = 100;
    public float y = 350;

    ImageView player;

    public void start(Stage stage) {

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 960, 540);

        // loadimage();  // not working bro

        // Image tileimage = new tileimage("file:Assets/player.png"); // not working bro

        Image img = new Image("file:Assets/player.png"); // load pic
        player = new ImageView(img); // show pic

        player.setLayoutX(x); // player x-pos
        player.setLayoutY(y); // player y-pos
        
	pane.getChildren().add(player);

	movement(scene);

        stage.setScene(scene);
        stage.show();	
    }

    /*
    public void loadimage(){
        for(int row=0; row < map.length; row++){
            for(int col = 0; col < map[row].length; col++){
                ImageView tile = new ImageView(tileimage);
                tile.setLayoutX(col * TILE_SIZE);
                tile.setLayoutY(row * TILE_SIZE);
                pane.getChildren().add(tile);
            }
        }
    }
    */

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
