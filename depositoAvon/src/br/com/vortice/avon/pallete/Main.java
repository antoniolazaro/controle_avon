package br.com.vortice.avon.pallete;
	
import br.com.vortice.avon.pallete.sqlloader.CreateDatabaseStructure;
import br.com.vortice.avon.pallete.view.menu.MenuBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			CreateDatabaseStructure.initDatabase();
			BorderPane root = new BorderPane();
		    Scene scene = new Scene(root, 800, 600, Color.WHITE);
		    MenuBuilder menuBuilder = new  MenuBuilder(primaryStage, root);
			menuBuilder.buildMenu();
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
