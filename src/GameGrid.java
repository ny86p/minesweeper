import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.*;

public class GameGrid extends JPanel{
	
	//  Grid Dimensions
	private int COURT_WIDTH;
	private int COURT_HEIGHT;
	
	//Game state
	public boolean playing = false;
	public boolean win = false;
	public boolean lose = false;
	
	
	public boolean flagMode = false;
	//difficulty multiplier, defaulted to easy
	private int difficulty = 1;
	
	//number of tiles clicked
	public int numClicked = 0;
	private int numMines = 0;
	
	public class Tuple{
		public int x;
		public int y;
		public Tuple(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	private JLabel status;
	private JLabel time;
	
	//arraylist of mine locations
	public ArrayList<Tuple> mines = new ArrayList<Tuple>();
	
	// 2d game grid
	private Tile[][] grid;
	// Update interval for timer, in milliseconds
	public int gameTime = 0;
	public static final int INTERVAL = 500;
	private Timer timer;
	
	//For testing purposes:
	public GameGrid(){
		this.grid = new Tile[9][9];

		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				COURT_WIDTH = 360;
				COURT_HEIGHT = 360;
				grid[i][j] = new Tile(40, 40, i*40, j*40);
				grid[i][j].xGrid = i;
				grid[i][j].yGrid = j;
			}
		}
		grid[0][0].addMine();
		setTileNeighbors();

	}
	
	public GameGrid(final JLabel status, int difficulty, final JLabel time){
		// Setting up the grid
		this.difficulty = difficulty;
		int adj_size = 9*difficulty;
		this.grid = new Tile[adj_size][adj_size];

		for(int i = 0; i < adj_size; i++){
			for(int j = 0; j < adj_size; j++){
				COURT_WIDTH = 360*difficulty;
				COURT_HEIGHT = 360*difficulty;
				grid[i][j] = new Tile(40, 40, i*40, j*40);
				grid[i][j].xGrid = i;
				grid[i][j].yGrid = j;
			}
		}
		if(difficulty == 1)
			numMines = 10;
		else
			numMines = 50;
		addMines(numMines);
		setTileNeighbors();
		
		this.status = status;
		this.time = time;
		
		final int numTotal = 9*9*difficulty*difficulty;
		
		timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
				time.setText(Integer.toString(gameTime));
			}
		});
		
		 addMouseListener(new MouseAdapter() {
             @Override
             public void mousePressed(MouseEvent e) {
         		 if(!lose)
         			 timer.start();
                 int xPos = e.getX();
                 int yPos = e.getY();
                 xPos = xPos - (xPos % 40);
                 yPos = yPos - (yPos % 40);
                 
                 if(playing){
                     for(Tile[] a : grid){
                    	 for(Tile t : a){
                    		 if(t.xPos == xPos && t.yPos == yPos){
                    			 if(flagMode){
                    				 t.isFlagged = !t.isFlagged;
                    			 }
                    			 else{
                    				 if(!t.isFlagged){
                    					 if(!t.wasClicked){
                            				 t.clicked();
                            				 numClicked++;
                            				 floodMines(t);		 
                            			 } 
                    					 // Check if a mine is clicked- if it is, game over
                    					 mineClickedCheck(t);
                            			 
                    				 }
                    				 
                    			 }
                			 
                    		 }
                    		 
                		
                     	 }
                     }
                     // Check if game is won - save high score and winner name if it is
                     if(winGameCheck()){
                    	 JTextField firstName = new JTextField();
         	    		final JComponent[] inputs = new JComponent[] {
         	    				new JLabel("Type in Your Name!"),
         	    				firstName,
         	    		};
         				JOptionPane.showMessageDialog(null, inputs, 
         						"You Won!", JOptionPane.PLAIN_MESSAGE);
         				saveWinScore(firstName.getText() + ": " + time.getText());
                     }
                 }
 
                 repaint();
             }
         });
	}
	
	/**
	 * 
	 * @param numMines - Number of mines to add to game grid
	 * Adds the number of mines specified to the game grid
	 */ 
	public void addMines(int numMines){
		int n  = 0;
		while(n < numMines){
			int r = (int) (Math.random()*9*difficulty);
			int c = (int) (Math.random()*9*difficulty);
			if(!grid[r][c].hasMine()){
				grid[r][c].addMine();
				mines.add(new Tuple(r,c));
				n++;
			}
		}
	}
	
	
	/**
	 * For each tile finds the number of mine neighbors it has
	 */
	public void setTileNeighbors(){
		for(Tile[] a: grid){
			for(Tile t : a){
				for(int j = -1; j <= 1; j++){		
					for(int k = -1; k <= 1; k++){
						if( (j + t.xGrid) >= 0 && (j + t.xGrid) < 9*difficulty && 
								(k + t.yGrid) >=0 && (k + t.yGrid) < 9*difficulty){				
							if(grid[t.xGrid + j][t.yGrid + k].hasMine())
								t.mineNeighbors++;
							if(!(j == 0 && k == 0))
								t.tileNeighbors.add(grid[t.xGrid + j][t.yGrid + k]);							
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * Checks if a tile is null, and if so, reveals all its neighbors. Does so recursively, until 
	 * a ring of non-null tiles forms around the null one.
	 * @param t - A tile clicked on game board
	 */ 
	public void floodMines(Tile t){
		if(t.mineNeighbors == 0){
			showTileNeighbors(t);
			if(t.nullNeighbors().size() > 0){
				for(Tile a : t.nullNeighbors()){
					if(!a.neighborsClicked())
						floodMines(a);
				}
			}
		}
	}
	
	//Reveals all the neighbors of a tile
	public void showTileNeighbors(Tile t){
		for(int j = -1; j <= 1; j++){
			for(int k = -1; k <= 1; k++){
				if( (j + t.xGrid) >= 0 && (j + t.xGrid) < 9*difficulty &&
						(k + t.yGrid) >=0 && (k + t.yGrid) < 9*difficulty){
					if(!grid[t.xGrid + j][t.yGrid + k].wasClicked){
						grid[t.xGrid + j][t.yGrid + k].clicked();
						numClicked++;
					}
						
				}
			}
		}
	}
	
	//Checks if game is won
	public boolean winGameCheck(){
		final int numTotal = 9*9*difficulty*difficulty;
		// If the number of tiles clicked is equal to the total number of tiles - num mines, win
		if(numClicked == numTotal - numMines){
			playing = false;
			if(!lose){
				timer.stop();
				status.setText("Winner!");
				win = true;
				repaint();
			}
			return true;
		}
		return false;
	}
		
	// Save high score and name out to proper file
	public void saveWinScore(String c){
		try {

			String content = c;
			File file;
			if(difficulty == 1)
				file = new File("highscores.txt");
			else
				file = new File("highscoresDiff.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file,true); 
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("\n");
			bw.write(content);
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	// Reset the game board to default original state
	public void reset() {
		for(int i = 0; i < 9*difficulty; i++){
			for(int j = 0; j < 9*difficulty; j++){
				grid[i][j] = new Tile(40, 40, 40*i, 40*j);
				grid[i][j].xGrid = i;
				grid[i][j].yGrid = j;
			}
		}
		if(difficulty == 1)
			addMines(10);
		else
			addMines(50);
		
		setTileNeighbors();
		playing = true;
		status.setText("Playing");
		numClicked = 0;
		gameTime = 0;
		time.setText("0");
		timer.stop();

		try{
		}
		catch(Exception e){
			
		}
		status.setText("Playing");
		win = false;
		lose = false;
		repaint();

	}
	
	//Game over check - if a mine is clicked end game, stop timer, and let user know he lost
	public void mineClickedCheck(Tile t){
		if(t.hasMine()){
			 playing = false;
			 timer.stop();
			 gameTime = 0;
			 lose = true;
			 status.setText("You Lose!");
		}
	}
	
	/**
	 * Iterates the timer
	 */
	public void tick(){
		gameTime++;
	}
	
	public Tile[][] getGrid(){
		return grid;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		for(int i = 0; i < 9*difficulty; i++){
			for(int j = 0; j < 9*difficulty; j++){
				grid[i][j].draw(g);
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
	
}