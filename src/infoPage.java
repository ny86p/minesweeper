import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



public class infoPage implements Runnable{
	public static int difficulty;
	public void run() {
		// NOTE : recall that the 'final' keyword notes inmutability
		// even for local variables.

		// Top-level frame in which game components live
		// Be sure to change "TOP LEVEL FRAME" to the name of your game
		final JFrame frame = new JFrame("Information Page");
		frame.setLocation(300, 300);

		// Status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		final JLabel status = new JLabel("Information Page");
		status_panel.add(status);

		// Main playing area
		JLabel choice = new JLabel("Choose the Difficulty");
		
		JOptionPane.showMessageDialog(frame, " Welcome to Minesweeper. \n"
				+ "Click on the gray tiles to reveal either numbers or mines. \n"
				+ "If a tile is clicked and a number is revealed, that number represents \n"
				+ "the number of mines adjacent to that tile. Note that a mine is considered\n"
				+ "adjacent to a tile if it is in any of the 8 neighboring tiles. You can\n"
				+ "click the flag button in order to 'flag' tiles, which is done to mark a tile\n"
				+ "you think contains a mine."
				+ "Click the reset button to reset the board.\n"
				+ "There is a timer that displays how long the current game has lasted. \n"
				+ "Highscores are time based: the faster you win, the better your time and \n"
				+ "high score ranking. High scores can be found by clicking the high score button.\n"
				+ "\n Losing occurs whenever you click on a mine. The way to win is to click on \n"
				+ "and reveal all the tiles that are not containing a mine. Click ok when you\n"
				+ "are ready to continue, and from there choose the difficulty at which you want\n"
				+ "to play. An easy game is on a 9x9 board with 10 mines. If you want to challenge\n"
				+ "yourself, the difficult game is an 18x18 board with 70 mines."
				+ " Best of luck and enjoy :)");
		frame.add(choice, BorderLayout.CENTER);

		// Reset button
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.SOUTH);

		// Note here that when we add an action listener to the reset
		// button, we define it as an anonymous inner class that is
		// an instance of ActionListener with its actionPerformed()
		// method overridden. When the button is pressed,
		// actionPerformed() will be called.
		final JButton easy = new JButton("Easy");
		easy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(new Game(1));
			}
		});
		control_panel.add(easy);
		final JButton diff = new JButton("Difficult");
		diff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(new Game(2));
			}
		});
		control_panel.add(diff);
		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new infoPage());
		
	}
}