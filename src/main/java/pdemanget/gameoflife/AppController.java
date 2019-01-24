package pdemanget.gameoflife;

import static java.util.Arrays.asList;
import static pdemanget.gameoflife.utils.ByteUtils.longToBytesBE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class AppController {
	private static final boolean VIDEO_INVERSE = true;
	public static int PERIOD = 200;
	@FXML
	Canvas canvas;
	@FXML
	Label count;
	@FXML
	TextField text;
	int PIXEL_SIZE = 5;
	int GAME_SIZE = 256;
	int SCREENSIZE = GAME_SIZE*PIXEL_SIZE;
	GameOfLife game = new GameOfLife(GAME_SIZE);
	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	ScheduledFuture<?> scheduled = null;
	private File initialDirectory = new File(".");
	@FXML ScrollPane scrollPane; 
			
	@FXML
	public void initialize() {

		// canvas.getGraphicsContext2D().fill
		canvas.setWidth(SCREENSIZE);
		canvas.setHeight(SCREENSIZE);
		GraphicsContext gContext = canvas.getGraphicsContext2D();
		gContext.setFill(Color.GREY);
		gContext.fillRect(0, 0, SCREENSIZE, SCREENSIZE);
		scrollPane.setVvalue(0.5);
		scrollPane.setHvalue(0.5);
		scrollPane.setOnScroll(e->{
			double sceneX = e.getSceneX();
			System.out.println(e);
		});
		clear();
		game.loadExample();
		drawGrid();
		
		canvas.setOnMouseClicked(e->{
			System.out.println(e);
			int i= (int)(e.getX() /PIXEL_SIZE);
			int j= (int)(e.getY()/PIXEL_SIZE);
//			int i= (int)(e.getSceneX() /PIXEL_SIZE);
//			int j= (int)(e.getSceneY()/PIXEL_SIZE);
			game.getListener().changed(game.getImg()[i][j]==0?game.myColor:0,i,j);
		});
	}	

	private void drawGrid() {
		for (int i = GAME_SIZE - 1; i >= 0; i--) {
			for (int j = GAME_SIZE - 1; j >= 0; j--) {
				drawPoint(game.getImg()[i][j], i, j);
			}
		}
	}

	private void drawPoint(long color, int i, int j) {
		if( VIDEO_INVERSE) color = ~color & 0xFFFFFF;
		byte[] colors = longToBytesBE(color);
		GraphicsContext gContext = canvas.getGraphicsContext2D();
		int BORDER_WIDTH = 1;
		gContext.setFill(Color.rgb(colors[0] & 0xFF, colors[1] & 0xFF, colors[2] & 0xFF));
		gContext.fillRect(BORDER_WIDTH + i * PIXEL_SIZE, BORDER_WIDTH + j * PIXEL_SIZE, PIXEL_SIZE-BORDER_WIDTH, PIXEL_SIZE-BORDER_WIDTH);

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
		File file = fileChoose();
		if(file.isDirectory()) {
			initialDirectory = file;
		}else {
			if(file.getParentFile().exists() && file.getParentFile().isDirectory()) {
				initialDirectory = file.getParentFile();	
			}
		}
		if (file != null) {
			String ext = file.getName().substring(file.getName().lastIndexOf('.')+1);
			BoardFile board= "gol".equals(ext)?BoardFile.getGolBoardFile():
				asList("cell","cells").contains(ext)?BoardFile.getCellBoardFile():
				asList("lif","life").equals(ext)?BoardFile.getLifeBoardFile():
				"rle".equals(ext)?BoardFile.getRleBoardFile():
				BoardFile.getGolBoardFile();
			try {
				Path path = file.toPath();
				board.loadFile(path);
				App.getInstance().getStage().setTitle(file.getName());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			game.loadBoard(board);
			drawGrid();
		}

	}

	private File fileChoose() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.setInitialDirectory(initialDirectory);
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All Files", "*.*"),
				new FileChooser.ExtensionFilter(".lif or .life 1.05", "*.life", "*.lif"),
				new FileChooser.ExtensionFilter(".cell", "*.cell", "*.cells"),
				new FileChooser.ExtensionFilter(".rle", "*.rle"),				
				new FileChooser.ExtensionFilter("gol", "*.gol", "*.gol.txt")
				);
		Stage stage = App.getInstance().getStage();
		File file = fileChooser.showOpenDialog(stage);
		return file;
	}

	@FXML
	public void save() {
		File file = fileChoose();
		if(file.isDirectory()) {
			initialDirectory = file;
		}else {
			if(file.getParentFile().exists() && file.getParentFile().isDirectory()) {
				initialDirectory = file.getParentFile();	
			}
		}
		if (file != null) {
			if (file.exists()) {
				Alert alert = new Alert(AlertType.WARNING,"File "+file+" exists, do you want to overwrite?", 
						ButtonType.OK,ButtonType.CANCEL);
				Optional<ButtonType> showAndWait = alert.showAndWait();
				if(! showAndWait.orElse(ButtonType.CANCEL).equals(ButtonType.OK)) {
					return;
				}
			}
			String ext = file.getName().substring(file.getName().lastIndexOf('.')+1);
			BoardFile board= "gol".equals(ext)?BoardFile.getGolBoardFile():
				asList("cell","cells").contains(ext)?BoardFile.getCellBoardFile():
				asList("lif","life").contains(ext)?BoardFile.getLifeBoardFile():
				"rle".equals(ext)?BoardFile.getRleBoardFile():
				BoardFile.getGolBoardFile();

				try {
					game.saveBoard(board);
					board.saveFile(file.toPath());
					Alert alert = new Alert(AlertType.CONFIRMATION,"File "+file+" saved.",ButtonType.OK);
					alert.show();

				} catch (Exception e) {
					e.printStackTrace();//IOException
					Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
					alert.show();
				}

		}
	}

	@FXML public void pause() {
		if(scheduled!=null) {
			scheduled.cancel(true);
		}
	}
	@FXML public void clear() {
		pause();
		game = new GameOfLife(GAME_SIZE);
		drawGrid();
		game.addListener(this::drawPoint);
		count.textProperty().bindBidirectional(game.stepProperty(), new NumberStringConverter());

	}


	public void stop() {
		executor.shutdownNow();
	}

}
