package game;

import java.util.LinkedList;

public class Player{
	

	private LinkedList<Shibing> Units = new LinkedList<Shibing>();
	private int clicked = 0;
	//farbe
	
	public Player(){
		
	}

	public int getClicked() {
		return clicked;
	}

	public void setClicked(int clicked) {
		this.clicked = clicked;
	}

	public LinkedList<Shibing> getUnits() {
		return Units;
	}

	public void setUnits(LinkedList<Shibing> units) {
		Units = units;
	}
}