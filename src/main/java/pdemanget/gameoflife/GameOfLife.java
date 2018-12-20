package pdemanget.gameoflife;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import pdemanget.gameoflife.utils.IntStack;

public class GameOfLife {
	final long[][] img;
	SimpleIntegerProperty step = new SimpleIntegerProperty();
	int myColor = 0xFFFFFF;
	int size;
	
	IntStack added = new IntStack();
	IntStack removed = new IntStack();

	// listener on pixels change (xyc)
	GameOfLifeListener listener;
	List<GameOfLifeListener> listeners = new ArrayList<>();
	
	
	public GameOfLife(int size) {
		img = new long[size][];
		for (int i = size - 1; i >= 0; i--) {
			img[i] = new long[size];
		}
		this.size = size;
		GameOfLifeListener dataListener = (color,i,j)->img[i][j]=color;
		
		// Listener is a composite Listner wrapper of all listeners
		listener = (color,i,j)->listeners.forEach(l->l.changed(color, i, j));
		listeners.add(dataListener);
	}
	
	public void addListener(GameOfLifeListener listener) {
		listeners.add(listener);
	}
	
	public void loadExample() {
		BoardFile board = BoardFile.getGolBoardFile();
		try {
			board.loadResource("/game.gol.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		loadBoard(board);
	}

	public void loadBoard(BoardFile board) {
		int x=(getWidth()-board.getWidth())/2;
		int y=(getHeight()-board.getHeight())/2;
		
		
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				img[x+i][y+j] = board.get(i,j);
			}
		}
	}

	

	protected int count(int x, int y) {
		int count = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (i == 0 && j == 0)
					continue;
				if (img[x+i][y+j] == myColor)
					count++;
			}
		}
		return count;
	}

	public int nextStep() {
		for (int i = size - 2; i >= 1; i--) {
			for (int j = size - 2; j >= 1; j--) {
				int count = count(i, j);
				if (img[i][j] == myColor && (count < 2 || count > 3) ) {
					remove(i,j);
				}
				if (img[i][j] == 0 && count == 3) {
					add(i,j);
				}
			}
		}
		Platform.runLater(()->{
			step.set(step.get()+1);
		});
		while(added.size()>0) {
			listener.changed(myColor, added.popInt(), added.popInt());
		}
		while(removed.size()>0) {
			listener.changed(0, removed.popInt(), removed.popInt());
		}
		return getStep();
	}

	private void remove(int i, int j) {
		removed.addInt(j);		
		removed.addInt(i);
	}

	private void add(int i, int j) {
		added.addInt(j);
		added.addInt(i);
	}

	public long[][] getImg() {
		return img;
	}
	
	
	
	public String toString() {
		StringBuilder res = new StringBuilder();
		String line="";
		for (int i = size + 1; i >= 0; i--) {
			line+='-';
		}
		res.append(line);
		res.append('\n');
		for (int i = size - 1; i >= 0; i--) {
			res.append("|");
			for (int j = size - 1; j >= 0; j--) {
				res.append(img[i][j]==0?' ':'X');
			}
			res.append("|\n");
			
		}
		res.append(line);
		return res.toString();
	}

	public final SimpleIntegerProperty countProperty() {
		return this.step;
	}
	
	public int getWidth() {
		return size;
	}
	
	public int getHeight() {
		return size;
	}

	public final int getCount() {
		return this.countProperty().get();
	}
	

	public final void setCount(final int count) {
		this.countProperty().set(count);
	}

	public final SimpleIntegerProperty stepProperty() {
		return this.step;
	}
	

	public final int getStep() {
		return this.stepProperty().get();
	}
	

	public final void setStep(final int step) {
		this.stepProperty().set(step);
	}
	
	

}
