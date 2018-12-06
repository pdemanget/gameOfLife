package pdemanget.gameoflife;

import org.junit.Assert;
import org.junit.Test;


public class GameOfLifeTest {
	@Test
	public void testInit() {
		GameOfLife game = new GameOfLife(20);
		System.out.println(game);
		int count = game.count(11, 11);
		Assert.assertEquals(3,count);
		game.nextStep();
		System.out.println(game);
		game.nextStep();
		System.out.println(game);
		game.nextStep();
		System.out.println(game);
		game.nextStep();
		System.out.println(game);
		game.nextStep();
		System.out.println(game);
		game.nextStep();
		System.out.println(game);
	}

}
