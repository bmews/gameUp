package game;

public class Shibing {

	public final static int fatness = 10;
	public final static int speed = 5;
	final int startLocationRichtung = 0;
	private int CD;
	private int positionX;
	private int positionY;
	private double richtung;
	private int[] ShotData = new int[5]; // [0]: Schuss ja oder nein?, 1+2 startkoordinate, 3+4 Zielkoordinate

	public int[] getShotData() {
		return ShotData;
	}

	public void setShotData(int pos,int shotData) {
		ShotData[pos] = shotData;
	}

	public Shibing(int X, int Y) {
		this.positionX = X;
		this.positionY = Y;
		this.setRichtung(startLocationRichtung);
		// Shibinglist.add(); //?
	}

	public int getCD() {
		return CD;
	}

	public void setCD(int cD) {
		CD = cD;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public double getRichtung() {
		return richtung;
	}

	public void setRichtung(double richtung) {
		this.richtung = richtung;
	}

	// Move vorne (WandList, ShibingList Kollision, Shibinglist2 Schuss)
	// Drehen links, rechts (nur �nderung Richtung)

}