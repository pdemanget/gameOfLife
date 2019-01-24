package pdemanget.gameoflife;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;

public class BoarFileTest {
	
	@Test
	public void testBoardFile() throws IOException {
		BoardFile golBoardFile = BoardFile.getCellBoardFile();
		golBoardFile.loadFile(Paths.get("conway.cells"));
		BoardFile dest = BoardFile.getGolBoardFile();
		dest.copyBoard(golBoardFile);
		dest.saveFile(Paths.get("conway.gol"));
		
	}

}
