package pdemanget.gameoflife;


import javafx.fxml.FXML;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;

public class AppController {
	@FXML
	private Canvas canvas;
	@FXML TextField text;
	int size=20;
	GameOfLife game = new GameOfLife(size);
	
	@FXML
	public void initialize() {
		text.setStyle("-fx-border_width:1px;-fx-border-color: red;");

		//canvas.getGraphicsContext2D().fill
		GraphicsContext gContext = canvas.getGraphicsContext2D();
		gContext.setFill(Color.GREY);
		gContext.fillRect(0, 0, 1280, 720	);
		
		drawGrid();
		game.addListener(this::drawPoint);

	}
	private void drawGrid() {
		for (int i = size - 1; i >= 0; i--) {
			for (int j = size - 1; j >= 0; j--) {
				drawPoint(game.getImg()[i][j],i,j);
			}
			
		}
	}
	public static byte[] longToBytesBE(long l) {
	    byte[] result = new byte[8];
//	    for (int i = 7; i >= 0; i--) {
	    for (int i = 0; i < 8; i++) {
	        result[i] = (byte)(l & 0xFF);
	        l >>= 8;
	    }
	    return result;
	}

	public static long bytesToLongBE(byte[] b) {
	    long result = 0;
	    for (int i = 7; i >= 0; i--) {
	        result <<= 8;
	        result |= (b[i] & 0xFF);
	    }
	    return result;
	}
	
	private void drawPoint(long color, int i, int j) {
		byte[] colors = longToBytesBE(color);
		GraphicsContext gContext = canvas.getGraphicsContext2D();
		gContext.setFill(Color.rgb(colors[0]& 0xFF, colors[1]& 0xFF, colors[2]& 0xFF));
		gContext.fillRect(1+i*10, 1+j*10, 8, 8);
		
	}

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
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                       new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                         new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                          new double[]{210, 210, 240, 240}, 4);
    }
	
	@FXML
	public void go() {
		game.nextStep();
	}

}
