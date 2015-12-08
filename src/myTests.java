import java.awt.LayoutManager;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import javax.swing.*;

import org.junit.*;

/** Put your OWN test cases in this file, for all classes in the assignment. */
public class myTests {
	
	
	@Test
	public void gameLoseWhenMineClicked() {
		// Checks if game ends when mine is clicked
		JLabel status = new JLabel("cool");
		JLabel time = null;
		GameGrid g = new GameGrid(status, 1, time);
		int r = 0;
		int c = 0;
		for(GameGrid.Tuple t : g.mines){
			r = t.x;
			c = t.y; 
		}
		System.out.println(r + " " + c);
		g.mineClickedCheck(g.grid[r][c]);
		assertTrue("Problem?",g.lose == true);
		assertTrue(g.win == false);

		
		
	}
	
	@Test
	public void gameWinsCheck() {
		// Checks if game ends when the number of clicks is the total tiles on the board
		// minus the number of mines
		JLabel status = new JLabel("cis120 is da bomb");
		JLabel time = new JLabel("cis120 is da bomb");
		GameGrid g = new GameGrid(status, 1, time);
		g.numClicked = 9*9 - 10;
		g.winGameCheck();
		assertTrue("Works?",g.win == true);

		
		
	}
	
//	@Test
//	public void minesFloodWorks() {
//		// Checks if the algorithm to remove tiles when a blank tile is selected works properly
//		GameGrid g = new GameGrid();
//		
//
//		
//		
//	}
}