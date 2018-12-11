package pdemanget.gameoflife;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	private static App instance;
	private Stage stage;
	AppController appController;
	// private ResourceBundle bundle = ResourceBundle.getBundle("i18n/app",
	// Locale.getDefault());

	public static void main(String[] args) {
		launch(args);
	}


	public void start(Stage stage) throws IOException {
		instance=this;
		this.stage=stage;
		MetaApp metaApp = new MetaApp();
		metaApp.setLogo(LOGO);
		metaApp.setScreen("App.fxml");
		metaApp.setTitle("Game of life");

//	    Injector.registerExistingAndInject (this);
//	    Injector.setModelOrService(this.getClass (),this);
		stage.setTitle(metaApp.getTitle());

		stage.getIcons().add(new Image(metaApp.getLogo()));
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(metaApp.getScreen()));
		//Parent root = FXMLLoader.load(getClass().getResource(metaApp.getScreen()));
		Parent root = fxmlLoader.load();
		appController = fxmlLoader.getController();
		Scene scene = new Scene(root);
		stage.setMaximized(true);
		stage.setScene(scene);
		stage.show();

	}

	@Override
	public void stop() {
		System.out.println("exiting");
		appController.stop();
	}

	public static App getInstance() {
		return instance;
	}

	public Stage getStage() {
		return stage;
	}
}
