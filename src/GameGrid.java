import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class GameGrid extends JPanel{
	
	//  Grid Dimensions
	private int COURT_WIDTH;
	private int COURT_HEIGHT;
	
	public boolean playing = false;
	public boolean flagMode = false;
	private int difficulty;
	
	public class Tuple{
		public int x;
		public int y;
		public Tuple(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	
	private JLabel status;
	private ArrayList<Tuple> mines = new ArrayList<Tuple>();
	private Tile[][] grid;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;
	
	public GameGrid(final JLabel status, int difficulty){
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
		if(difficulty == 0)
			addMines(10);
		else
			addMines(70);
		setTileNeighbors();
		
		this.status = status;
		
		 addMouseListener(new MouseAdapter() {
             @Override
             public void mousePressed(MouseEvent e) {
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
                    					 if(t.hasMine()){
                                			 
                        					 playing = false;
                        					 status.setText("You Lose!");
                        				 }
                            			 if(!t.wasClicked){
                            				 t.clicked();
                            				 floodMines(t);		 
                            			 } 
                    				 }
                    				 
                    			 }
                    		 }
                    		
                     	 }
                     }
                 }
 
                 repaint();
             }
         });
	}
	
	public void addMines(int numMines){
		int n  = 0;
		while(n < numMines){
			int r = (int) (Math.random()*9*difficulty);
			int c = (int) (Math.random()*9*difficulty);
			System.out.println(r + " " + c);
			if(!grid[r][c].hasMine()){
				grid[r][c].addMine();
				mines.add(new Tuple(r,c));
				n++;
			}
		}
	}
	
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
	
	
	public void showTileNeighbors(Tile t){
		for(int j = -1; j <= 1; j++){
			for(int k = -1; k <= 1; k++){
				if( (j + t.xGrid) >= 0 && (j + t.xGrid) < 9*difficulty &&
						(k + t.yGrid) >=0 && (k + t.yGrid) < 9*difficulty){
					if(!grid[t.xGrid + j][t.yGrid + k].wasClicked)
						grid[t.xGrid + j][t.yGrid + k].clicked();
				}
			}
		}
	}
	
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
			addMines(70);
		setTileNeighbors();
		playing = true;
		status.setText("Playing");
		repaint();

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