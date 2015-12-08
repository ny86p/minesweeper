/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	public int gameDifficulty;
	TreeMap<Integer, String> highScoresEasy = new TreeMap<Integer, String>();
	TreeMap<Integer, String> highScoresHard = new TreeMap<Integer, String>();

	ArrayList<Integer> sortedEasyScores = new ArrayList<Integer>();
	ArrayList<Integer> sortedHardScores = new ArrayList<Integer>();

	
	public Game(int diff){
		gameDifficulty = diff;
	}
	public void run() {
		// NOTE : recall that the 'final' keyword notes immutability
		// even for local variables.

		
		// Top-level frame in which game components live
		// Be sure to change "TOP LEVEL FRAME" to the name of your game
		try {
			loadNewHighScores();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final JFrame frame = new JFrame("MineSweeper");
		frame.setLocation(300, 300);

		// Status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		JLabel status = new JLabel("Running...");
		status_panel.add(status);
		
		final JLabel timer = new JLabel("0");
		// Main playing area
		final GameGrid grid = new GameGrid(status,gameDifficulty,timer);
		frame.add(grid, BorderLayout.CENTER);

		grid.reset();
		// Reset button
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.NORTH);
		// Note here that when we add an action listener to the reset
		// button, we define it as an anonymous inner class that is
		// an instance of ActionListener with its actionPerformed()
		// method overridden. When the button is pressed,
		// actionPerformed() will be called.
		final JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.reset();
				try {
					loadNewHighScores();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		grid.flagMode = false;
	

		final JButton flagger = new JButton("Flag");
		flagger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.flagMode = !grid.flagMode;
				if(grid.flagMode){
					flagger.setBackground(Color.black);
					flagger.setForeground(Color.red);
				}
				else{
					flagger.setBackground(null);
					flagger.setForeground(null);
				}
			}
		});
		
		final JButton highScores = new JButton("High Scores");
		highScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					sortScores();
					if(gameDifficulty == 1){
						if(sortedEasyScores.size() > 0){
							String[] scores = new String[sortedEasyScores.size()+1];
							scores[0] = "High Scores:";
							int counter = 1;

							for(int score : sortedEasyScores){
								scores[counter] = highScoresEasy.get(score) + " " + score;
								counter++;
							}
							JOptionPane.showMessageDialog(null, scores);
						}
					}
					else{
						if(sortedHardScores.size() > 0){
							String[] scores = new String[sortedHardScores.size()+1];
							int counter = 1;
							scores[0] = "High Scores:";
							for(int score : sortedHardScores){
								scores[counter] = highScoresHard.get(score) + " " + score;
								counter++;
							}
							JOptionPane.showMessageDialog(null, scores);
						}
					}
						
					
				
				
			}
		});
		control_panel.add(reset); 
		control_panel.add(flagger);
		control_panel.add(timer);
		control_panel.add(highScores);


		// Put the frame on the screen
		frame.pack();
		frame.setVisible(true);
		
	
	}

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public void loadNewHighScores() throws IOException{
		BufferedReader br;
		if(gameDifficulty == 1)
			br = new BufferedReader(new FileReader("highscores.txt"));
		else 
			br = new BufferedReader(new FileReader("highscoresDiff.txt"));

		try {
		    String line = br.readLine();
		    

		    while (line != null) {
			    String namepass[] = line.split(": ");
			    if(gameDifficulty == 1){
			    	highScoresEasy.put(Integer.parseInt(namepass[1]),namepass[0]);
			    	boolean c = true;
			    	for(int s : sortedEasyScores){
			    		if(s == Integer.parseInt(namepass[1]))
			    			c = false;
			    	}
			    	if(c)
			    		sortedEasyScores.add(Integer.parseInt(namepass[1]));
			    }
			    else{
			    	highScoresHard.put(Integer.parseInt(namepass[1]),namepass[0]);
			    	boolean c = true;
			    	for(int s : sortedHardScores){
			    		if(s == Integer.parseInt(namepass[1]))
			    			c = false;
			    	}
			    	sortedHardScores.add(Integer.parseInt(namepass[1]));
			    }
		        line = br.readLine();
		    }
		}
		catch(FileNotFoundException e){
			
		}
		finally {
		    br.close();
		}

	}
	
	public void sortScores(){	
		if(gameDifficulty == 1){
			Collections.sort(sortedEasyScores);
//			Collections.reverse(sortedEasyScores);
		}
		else{
			Collections.sort(sortedHardScores);
//			Collections.reverse(sortedHardScores);
		}
	}
	
}
