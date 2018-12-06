package pdemanget.gameoflife;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 *
 * @author pdemanget
 * @version 26 févr. 2016
 */
public class App extends Application {

	private static final String LOGO = "/style/logo.png";
	// private ResourceBundle bundle = ResourceBundle.getBundle("i18n/app",
	// Locale.getDefault());

	public static void main(String[] args) {
		launch(args);
	}

	public void start0(Stage primaryStage) {
		primaryStage.setTitle("Drawing Operations Test");
		Group root = new Group();
		Canvas canvas = new Canvas(300, 250);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		new AppController().drawShapes(gc);
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public void start(Stage stage) throws IOException {

		MetaApp metaApp = new MetaApp();
		metaApp.setLogo(LOGO);
		metaApp.setScreen("App.fxml");
		metaApp.setTitle("Game of life");

//	    Injector.registerExistingAndInject (this);
//	    Injector.setModelOrService(this.getClass (),this);
		stage.setTitle(metaApp.getTitle());

		stage.getIcons().add(new Image(metaApp.getLogo()));
		Parent root = FXMLLoader.load(getClass().getResource(metaApp.getScreen()));
		Scene scene = new Scene(root);
		stage.setMaximized(true);
		stage.setScene(scene);
		stage.show();

	}

	@Override
	public void stop() {
		System.out.println("exiting");
	}
}
