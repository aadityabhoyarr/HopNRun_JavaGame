import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.image.*;

public class Main extends Application {

    public void start(Stage stage) {

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 960, 540);

        Image img = new Image("file:Assets/player.png"); // load pic
        ImageView player = new ImageView(img); // show pic

        player.setLayoutX(100); // player x-pos
        player.setLayoutY(350); // player y-pos

        pane.getChildren().add(player);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
