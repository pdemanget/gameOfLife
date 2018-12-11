package pdemanget.gameoflife;

import static pdemanget.gameoflife.utils.ByteUtils.longToBytesBE;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.control.ScrollPane;

public class AppController {
	public static int PERIOD = 30;
	@FXML
	Canvas canvas;
	@FXML
	Label count;
	@FXML
	TextField text;
	int PIXEL_SIZE = 10;
	int SCREENSIZE = 1920;
	int GAME_SIZE = SCREENSIZE / PIXEL_SIZE;
	GameOfLife game = new GameOfLife(GAME_SIZE);
	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	ScheduledFuture<?> scheduled = null;
	private File initialDirectory = new File(".");
	@FXML ScrollPane scrollPane; 
			
	@FXML
	public void initialize() {

		// canvas.getGraphicsContext2D().fill
		GraphicsContext gContext = canvas.getGraphicsContext2D();
		gContext.setFill(Color.GREY);
		gContext.fillRect(0, 0, SCREENSIZE, SCREENSIZE);
		game = new GameOfLife(GAME_SIZE);
		drawGrid();
		game.addListener(this::drawPoint);
		count.textProperty().bindBidirectional(game.stepProperty(), new NumberStringConverter());
		scrollPane.setVvalue(0.5);
	}	

	private void drawGrid() {
		for (int i = GAME_SIZE - 1; i >= 0; i--) {
			for (int j = GAME_SIZE - 1; j >= 0; j--) {
				drawPoint(game.getImg()[i][j], i, j);
			}
		}
	}

	private void drawPoint(long color, int i, int j) {
		byte[] colors = longToBytesBE(color);
		GraphicsContext gContext = canvas.getGraphicsContext2D();
		gContext.setFill(Color.rgb(colors[0] & 0xFF, colors[1] & 0xFF, colors[2] & 0xFF));
		int PIXEL_SIZE = 10;
		gContext.fillRect(1 + i * PIXEL_SIZE, 1 + j * PIXEL_SIZE, 8, 8);

	}

	/**
	 * Not used, exemples for other shapes
	 * @param gc
	 */
	public void drawShapes(GraphicsContext gc) {
		gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(5);
		gc.strokeLine(40, 10, 10, 40);
		gc.fillOval(10, 60, 30, 30);
		gc.strokeOval(60, 60, 30, 30);
		gc.fillRoundRect(110, 60, 30, 30, 10, 10);
		gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
		gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
		gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
		gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
		gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
		gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
		gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
		gc.fillPolygon(new double[] { 10, 40, 10, 40 }, new double[] { 210, 210, 240, 240 }, 4);
		gc.strokePolygon(new double[] { 60, 90, 60, 90 }, new double[] { 210, 210, 240, 240 }, 4);
		gc.strokePolyline(new double[] { 110, 140, 110, 140 }, new double[] { 210, 210, 240, 240 }, 4);
	}

	@FXML
	public void go() {
		scheduled = executor.scheduleAtFixedRate(this::next, 1000, PERIOD, TimeUnit.MILLISECONDS);
	}

	@FXML
	public void next() {
		game.nextStep();
	}

	@FXML
	public void open() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.setInitialDirectory(initialDirectory);
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Game of life file", "*.gol.txt"),
				new FileChooser.ExtensionFilter("All Files", "*.*"));
		Stage stage = App.getInstance().getStage();
		File file = fileChooser.showOpenDialog(stage);
		if(file.isDirectory()) {
			initialDirectory = file;
		}else {
			if(file.getParentFile().exists() && file.getParentFile().isDirectory()) {
				initialDirectory = file.getParentFile();	
			}
		}
		if (file != null) {
			BoardFile board= new BoardFile(0xFFFFFF);
			try {
				board.loadFile(file.toPath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			game.loadBoard(board);
			drawGrid();
		}

	}

	@FXML
	public void save() {
		System.out.println(game.toString());
	}

	@FXML public void pause() {
		if(scheduled!=null) {
			scheduled.cancel(true);
		}
	}
	@FXML public void clear() {
		pause();
		initialize();
	}


	public void stop() {
		executor.shutdownNow();
	}

}
