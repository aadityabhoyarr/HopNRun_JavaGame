import javax.swing.text.html.ImageView;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.image.*;

public class Main extends Application {
    //tiles
    int TILE_SIZE=40;
    //map using tile
    int[][] map={
        {0,0,0,0,0,0,0,0,0,0}
        {0,0,0,0,0,0,0,0,0,0}
        {0,0,0,0,0,0,0,0,0,0}
        {0,0,0,0,0,0,0,0,0,0}
        {0,0,0,0,0,0,0,0,0,0}
        {0,0,0,0,0,0,0,0,0,0}
        {1,1,1,1,1,1,1,1,1,1}
        {1,1,1,1,1,1,1,1,1,1}
    };
        
    public void start(Stage stage) {
 
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 960, 540);

        loadimage();

        Image tileimage = new tileimage("");//load tile


        Image img = new Image("file:Assets/player.png"); // load pic
        ImageView player = new ImageView(img); // show pic

        player.setLayoutX(100); // player x-pos
        player.setLayoutY(350); // player y-pos

        pane.getChildren().add(player);

        stage.setScene(scene);
        stage.show();
    }
    public void loadimage(){
        for(int row=0;row<map.length;row++){
            for(int col=0;col<map[row].length;col++){
                ImageView tile = new ImageView(tileimage)
                tile.setLayoutX(col*TILE_SIZE);
                tile.setLayoutY(row*TILE_SIZE);
                pane.getChildren.add(tile);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
