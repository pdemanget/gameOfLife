package pdemanget.gameoflife;

import java.util.ArrayList;
import java.util.List;

import pdemanget.gameoflife.utils.IntStack;

public class GameOfLife {
	final long[][] img;
	int myColor = 0xFFFFFF;
	int size;
	int step = 0;
	
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
		initValue();
		GameOfLifeListener dataListener = (color,i,j)->img[i][j]=color;
		
		listener = (color,i,j)->listeners.forEach(l->l.changed(color, i, j));
		listeners.add(dataListener);
	}
	
	public void addListener(GameOfLifeListener listener) {
		listeners.add(listener);
	}

	private void initValue() {
		// TODO make parametrable
		
		img[11][ 9] = myColor;
		img[11][10] = myColor;
		img[11][11] = myColor;
		img[11][12] = myColor;
		
		
		img[10][ 9] = myColor;
		img[10][10] = myColor;
		img[10][11] = myColor;
		img[10][12] = myColor;
		
		img[8][10] = myColor;
		img[8][11] = myColor;
		img[8][12] = myColor;
		img[8][13] = myColor;
	}

	
	
	private void initValue2() {
		// TODO make parametrable
		img[10][ 9] = myColor;
		img[10][10] = myColor;
		img[10][11] = myColor;
		img[10][12] = myColor;
		
		img[8][10] = myColor;
		img[8][11] = myColor;
		img[8][12] = myColor;
		img[8][13] = myColor;
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
		step++;
		while(added.size()>0) {
			listener.changed(myColor, added.popInt(), added.popInt());
		}
		while(removed.size()>0) {
			listener.changed(0, removed.popInt(), removed.popInt());
		}
		return step;
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

}
