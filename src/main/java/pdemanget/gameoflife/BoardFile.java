package pdemanget.gameoflife;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Ascii file loading
 * @author pdemanget
 *
 */
public class BoardFile {

	protected final List<List<Integer>> board = new ArrayList<>();
	protected int color;
	protected int width = 0;
	protected String dead;
	protected String alive;
	protected String comment;
	
	public static final BoardFile getLifeBoardFile() {
		return new BoardFile(".","*","#",0xFFFFFF); 
	}
	
	public static final BoardFile getCellBoardFile() {
		return new BoardFile(".","O","!",0xFFFFFF); 
	}

	public static final BoardFile getGolBoardFile() {
		return new BoardFile(" ","Xx","#",0xFFFFFF); 
	}
	
	public static final BoardFile getRleBoardFile() {
		return new RleBoardFile(0xFFFFFF); 
	}

	
	public BoardFile(String dead,String alive, String comment, int color) {
		this.dead=dead;
		this.alive=alive;
		this.comment=comment;
		this.color = color;
	}

	public void loadResource(String name) throws FileNotFoundException {
		loadReader(new InputStreamReader(getClass().getResourceAsStream(name)));
	}

	public void loadFile(Path path) throws FileNotFoundException {
		loadReader(new FileReader(path.toFile()));
	}

	protected void loadReader(Reader preader) throws FileNotFoundException {
		try (BufferedReader reader = new BufferedReader(preader)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith(comment))
					continue;
				List<Integer> pixLine = new ArrayList<>(line.length());
				board.add(pixLine);
				char[] chars = new char[line.length()];
				line.getChars(0, line.length(), chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if ( alive.indexOf(chars[i]) > -1) {
						pixLine.add(color);
					} else if (dead.indexOf(chars[i]) > -1) {
						pixLine.add(0);
					} else if (chars[i] != '|' && chars[i] != '-' && chars[i] != '+') {
						// Throw SyntaxError
					}
				}
				width=Math.max(width, pixLine.size());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return board.size();
	}
	
	public int get(int x,int y) {
		List<Integer> line = board.get(y);
		if(line.size()>x) return line.get(x);
		return 0;
	}

}
