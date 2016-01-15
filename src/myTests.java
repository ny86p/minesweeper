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
		g.mineClickedCheck(g.getGrid()[r][c]);
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
	
	@Test
	public void minesFloodWorks() {
		// X 1 0 0 0 0 0 0 0
		// 1 1 0 0 0 0 0 0 0
		// 0 0 0 0 0 0 0 0 0
		// 0
		// Checks if the algorithm to remove tiles when a blank tile is selected works properly
		// All of the following tiles 
		GameGrid g = new GameGrid();
		g.floodMines(g.getGrid()[2][0]);
		assertTrue(g.getGrid()[2][0].wasClicked == true);
		System.out.println(g.getGrid()[2][1].wasClicked);
		assertTrue(g.getGrid()[2][1].wasClicked == true);
		assertTrue(g.getGrid()[2][2].wasClicked == true);
		assertTrue(g.getGrid()[1][2].wasClicked == true);
		assertTrue(g.getGrid()[0][2].wasClicked == true);
		
		

		
		
	}
	
	@Test
	public void minesFloodWorksNonBlank() {
		// X 1 0 0 0 0 0 0 0
		// 1 1 0 0 0 0 0 0 0
		// 0 0 0 0 0 0 0 0 0
		// 0
		// Checks if the algorithm doesn't remove tile when non blank is clicked
		GameGrid g = new GameGrid();
		g.floodMines(g.getGrid()[1][0]);
		assertTrue(g.getGrid()[2][0].wasClicked == false);
		assertTrue(g.getGrid()[2][1].wasClicked == false);
		assertTrue(g.getGrid()[2][2].wasClicked == false);
		assertTrue(g.getGrid()[1][2].wasClicked == false);
		assertTrue(g.getGrid()[0][2].wasClicked == false);
		
		
	}
}