package pdemanget.gameoflife;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Reader for rle format. Simple ascii format.
 * 
 * Simple LL1 parser using theses token:
 *  b: dead cell
 *  o: alive cell
 *  <int>: count of next token
 *  $: line return (next line)
 *  !: end of stream
 * 
 * 
 * #N 8-engine Cordership #O Dean Hickerson #C
 * http://conwaylife.com/wiki/8-engine_Cordership #C
 * http://conwaylife.com/patterns/8enginecordership.rle x = 83, y = 83, rule =
 * B3/S23 46bo$41bo2bobo2bo3bo$41bo4b2ob5o$41bo3bo4bobo2$43b3o$44bo18b2o$63b2o6$
 * 
 * In this version anything beginning with a # or a x will be ignored.
 * 
 * 
 * @author pdemanget
 */
public class RleBoardFile extends BoardFile {

	
	private static class Parser{
		public static int DEAD=0;
		public static int ALIVE=-1;
		public static int NEWLINE=-2;
		public static int EMPTY=-127;
		
		final char[] chars;
		int pos=0;
		
		public Parser(char[] chars) {
			super();
			this.chars = chars;
		}
		/**
		 * >0 values: count
		 * 0 dead cell
		 * -1 live cell
		 * -2 eofline
		 * -3 buffer empty
		 * @return int representing the token
		 */
		int nextToken() {
			if(pos>=chars.length) {
				pos++;
				return EMPTY;
			}
			if(chars[pos]=='b') {
				pos++;
				return DEAD;
			}
			if(chars[pos]=='o') {
				pos++;
				return ALIVE;
			}
			if(chars[pos]=='$') {
				pos++;
				return NEWLINE;
			}
			if(chars[pos]=='!') {
				pos++;
				return EMPTY;
			}
			int count=1;
			while(pos+count<chars.length && chars[pos+count]>='0'&& chars[pos+count]<='9') {
				count++;
			}
			String number=new String(chars,pos,count);
			pos+=count;
			return Integer.parseInt(number);
		}
	}

	public RleBoardFile(int color) {
		super("b", "o", "x#", color);
	}

	protected void loadReader(Reader preader) throws FileNotFoundException {
		try (BufferedReader reader = new BufferedReader(preader)) {
			String line = null;
			List<Integer> pixLine = new ArrayList<>();
			board.add(pixLine);
			int nextRepetition=1;
			while ((line = reader.readLine()) != null) {
				if (comment.indexOf(line.charAt(0)) > -1) {
					continue;
				}
				char[] chars = new char[line.length()];
				line.getChars(0, line.length(), chars, 0);
				Parser parser = new Parser(chars);
				int tok = parser.nextToken();
				while(tok>-127) {
					if(tok>0) {
						nextRepetition=tok;
					}else {
						for(int i=0;i<nextRepetition;i++) {
							if(tok==Parser.NEWLINE) {
								pixLine = new ArrayList<>();
								board.add(pixLine);
							}else {
								pixLine.add(tok==0?0:color);
							}
						}
						nextRepetition=1;
						width = Math.max(width, pixLine.size());
					}
					tok = parser.nextToken();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveFile(Path path) throws IOException {
		// TODO Auto-generated method stub
		super.saveFile(path);
	}
	
	

}
