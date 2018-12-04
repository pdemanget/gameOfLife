package pdemanget.gameoflife;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

public class AppController {
	@FXML
	private Canvas canvas;
	
	@FXML
	public void initialize() {
		canvas.getGraphicsContext2D().fillRect(10, 10, 20, 20);
	}

}
