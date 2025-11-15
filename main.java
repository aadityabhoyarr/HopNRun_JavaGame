import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class Main extends Application{

	public void start (Stage stage){
		stage.setScene(new Scene(new Pane(), 800, 600));  // show screen
    		stage.show();
	}


	public static void main(String[] argh){
		launch();
	}
}
