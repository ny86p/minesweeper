import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;


public class Tile{
	
	// Set Tile Dimensions
	private int width;
	private int height;
	public int xPos;
	public int yPos;

	public int xGrid;
	public int yGrid;
	// Is the current tile flagged?
	public boolean isFlagged = false;
	
	// Was tile clicked?
	public boolean wasClicked = false;

	public  int mineNeighbors = 0;
	public ArrayList<Tile> tileNeighbors = new ArrayList<Tile>();
	private boolean hasMine = false;
	
	public Tile(int w, int h, int xPos, int yPos){
		width = w;
		height = h;
		this.xPos = xPos;
		this.yPos = yPos;
				
	}
	
	public void clicked(){
		wasClicked = true;
	}
	
	public void addMine(){
		hasMine = true;
	}
	
	public boolean hasMine(){
		return hasMine;
	}
		
	public ArrayList<Tile> nullNeighbors(){
		ArrayList<Tile> nullNeighbor = new ArrayList<Tile>();
		for(Tile t : tileNeighbors){
			if(t.mineNeighbors == 0){
				nullNeighbor.add(t);
			}
		}
		return nullNeighbor;
	}
	
	public boolean neighborsClicked(){
		for(Tile t : tileNeighbors)
			if(!t.wasClicked)
				return false;
		return true;
	}
	

	
	public void draw(Graphics g){
//TODO:	add logic for wasClicked, if it was, just display the number over white backdrop

		
		if(!wasClicked && isFlagged){
			BufferedImage buttonIcon = null;
			try {
				buttonIcon = ImageIO.read(new File("flag1.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			g.setColor(Color.black);
			g.fillRect(xPos, yPos, width, height);
			g.setColor(Color.gray);
			g.fillRect(xPos-1, yPos-1, width-1, height-1);

			g.drawImage(buttonIcon, xPos, yPos,null);
		}
		else if(wasClicked && hasMine){
			g.setColor(Color.black);
			g.fillRect(xPos, yPos, width, height);
			g.setColor(Color.red);
			g.fillRect(xPos-1, yPos-1, width-1, height-1);
			BufferedImage mine = null;
			try {
				mine = ImageIO.read(new File("mine.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.drawImage(mine, xPos+9, yPos+10,null);

		}
		else if(wasClicked){
			g.setColor(Color.black);
			g.fillRect(xPos, yPos, width, height);
			g.setColor(Color.white);
			g.fillRect(xPos-1, yPos-1, width-1, height-1);
			g.setColor(Color.black);
			g.setFont(new Font("Verdana", Font.BOLD, 22));
			if(mineNeighbors > 0)
				g.drawString(mineNeighbors + "", xPos+12, yPos+25);
		}
		else{
			g.setColor(Color.black);
			g.fillRect(xPos, yPos, width, height);
			g.setColor(Color.gray);
			g.fillRect(xPos-1, yPos-1, width-1, height-1);

		}
	}
}