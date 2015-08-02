package game;

import java.awt.Color;
import java.util.LinkedList;

/* Todo: forward -> Schhuss verschwindet 
 * -thread + deltatime
 * 
 * bugs fixen, gutes UML, Menu+End, playable, design, ordentliche Map,
 * 
 * bugs:
 * schuss geht noch 1 pixel tief
 * fressen: eigener shibing teilweise weg
 * start: manchmal(!) nur links obere Ecke(vor Fenster vergrößern sichtbar
 * letzter shibing von blau gefressen -> exception
 * in wall laufen
 * gleichzeitig gedrückt halten
 * shibings mit schuss getroffen -> nicht tot, nur wenn in schuss gelaufen.
 *
 *  0= frei, 1 = p1 einheit , 2 = p2 einheit, 5 = RahmenDicke, 6 Schuss
 * 
 * playable:
 * 
 schnell laufen, Melee, Schuss(an aus), wand werden, Mehrere anw�hlen, schieben

 design:
 Textur fuer alle Objects
 Animationen: Move, Turn, shoot, die
 Musik + Sounds

 addon: Wegfindung + Maus + Netzwerk

 Spaeter: Objectives, Income + kaufen, Upgrades,
 late: lanes, Heroes, Split screen + Nebel des Krieges, network
 */

public class Game { 
	private Player Players[] = new Player[2];
	private Player Player0 = new Player();
	private Player Player1 = new Player();
	private LinkedList<Wall> Walls = new LinkedList<Wall>();
	private int mapsizeX = 600, mapsizeY = 600, RahmenDicke = 50,
			kollision[][] = new int[mapsizeX + 1][mapsizeY + 1]; // leer=0,p1=1,p2=2,Schussp1=3,Schussp1=4,object=5
	private int StartLocation1X = RahmenDicke + 30;
	private int StartLocation1Y = RahmenDicke + 30;
	private int StartLocation2X = mapsizeX - RahmenDicke - 30;
	private int StartLocation2Y = mapsizeY - RahmenDicke - 30;
	private Color p1color = Color.red;
	private Color p2color = Color.blue;
	private Color Wallcolor = Color.black;
	private Color Shotcolor = Color.green;
	private int KeyForward1 = 87, KeyForward2 = 38, KeyLeft1 = 65,
			KeyLeft2 = 37;
	private int KeyRight1 = 68, KeyRight2 = 39, KeySwitch1 = 83,
			KeySwitch2 = 40;
	private int KeyClose = 27, KeyShowMap = 32, KeyShot1 = 69, KeyShot2 = 70;
	public Shibing DeleteShibing = null;
	public int StelleInListe = 0;
	public boolean GameOver = false;
	public LinkedList<Wall> WallsToCreate = new LinkedList<Wall>();
	private int Schussbreite = 5;

	private int[] ShotPrintData = new int[5];

	public int[] getShotPrintData() {
		return ShotPrintData;
	}

	public void setShotPrintData(int[] shotPrintData) {
		ShotPrintData = shotPrintData;
	}

	public Player[] getPlayers() {
		return Players;
	}

	public void setPlayers(Player[] players) {
		Players = players;
	}

	public int getMapsizeX() {
		return mapsizeX;
	}

	public void setMapsizeX(int mapsizeX) {
		this.mapsizeX = mapsizeX;
	}

	public int getMapsizeY() {
		return mapsizeY;
	}

	public void setMapsizeY(int mapsizeY) {
		this.mapsizeY = mapsizeY;
	}

	public Color getP2color() {
		return p2color;
	}

	public void setP2color(Color p2color) {
		this.p2color = p2color;
	}

	public int getKeyForward1() {
		return KeyForward1;
	}

	public void setKeyForward1(int keyForward1) {
		KeyForward1 = keyForward1;
	}

	public int getKeyForward2() {
		return KeyForward2;
	}

	public void setKeyForward2(int keyForward2) {
		KeyForward2 = keyForward2;
	}

	public int getKeyLeft1() {
		return KeyLeft1;
	}

	public void setKeyLeft1(int keyLeft1) {
		KeyLeft1 = keyLeft1;
	}

	public int getKeyLeft2() {
		return KeyLeft2;
	}

	public void setKeyLeft2(int keyLeft2) {
		KeyLeft2 = keyLeft2;
	}

	public int getKeyRight1() {
		return KeyRight1;
	}

	public void setKeyRight1(int keyRight1) {
		KeyRight1 = keyRight1;
	}

	public int getKeyRight2() {
		return KeyRight2;
	}

	public void setKeyRight2(int keyRight2) {
		KeyRight2 = keyRight2;
	}

	public int getKeySwitch1() {
		return KeySwitch1;
	}

	public void setKeySwitch1(int keySwitch1) {
		KeySwitch1 = keySwitch1;
	}

	public int getKeySwitch2() {
		return KeySwitch2;
	}

	public void setKeySwitch2(int keySwitch2) {
		KeySwitch2 = keySwitch2;
	}

	public int getKeyClose() {
		return KeyClose;
	}

	public void setKeyClose(int keyClose) {
		KeyClose = keyClose;
	}

	public int getKeyShowMap() {
		return KeyShowMap;
	}

	public void setKeyShowMap(int keyShowMap) {
		KeyShowMap = keyShowMap;
	}

	public boolean ShibingCreated = false;

	public Game() {
		mapStart();
	}

	/*
	 * erstellt Rand, schafft Spieler, erstellt x Shibing für Spieler
	 */
	public void mapStart() {
		// Rahmen erstellen

		createWall(0, 0, RahmenDicke, mapsizeY);
		createWall(0, 0, mapsizeX, RahmenDicke);
		createWall(mapsizeX - RahmenDicke, 0, RahmenDicke, mapsizeY);
		createWall(0, mapsizeY - RahmenDicke, mapsizeX, RahmenDicke);

		createWall(195, 64, 18, 69);
		createWall(80, 153, 139, 12);
		createWall(241, 121, 15, 55);
		createWall(176, 335, 187, 20);
		createWall(410, 438, 131, 10);
		createWall(359, 423, 22, 103);
		createWall(282, 411, 17, 143);
		createWall(281, 44, 17, 110);
		createWall(364, 383, 71, 15);
		createWall(425, 361, 70, 5);
		createWall(508, 349, 10, 67);
		createWall(400, 247, 8, 66);
		createWall(396, 335, 49, 11);
		createWall(470, 317, 12, 33);
		createWall(430, 289, 121, 13);
		createWall(434, 161, 105, 10);
		createWall(527, 124, 28, 8);
		createWall(526, 193, 6, 29);
		createWall(447, 194, 29, 71);
		createWall(204, 221, 115, 16);
		createWall(44, 182, 80, 14);
		createWall(160, 191, 13, 103);
		createWall(109, 197, 12, 32);
		createWall(116, 219, 22, 17);
		createWall(86, 265, 78, 9);
		createWall(213, 254, 17, 57);
		createWall(314, 235, 8, 64);
		createWall(87, 397, 12, 95);
		createWall(155, 473, 88, 8);
		createWall(145, 386, 19, 63);
		createWall(205, 408, 14, 40);
		createWall(213, 451, 0, 22);
		createWall(213, 446, 5, 26);
		createWall(163, 445, 11, 29);
		createWall(213, 448, 18, 22);
		createWall(222, 469, 7, 5);
		createWall(279, 260, 36, 8);
		createWall(361, 130, 11, 43);
		createWall(297, 151, 51, 17);
		createWall(272, 173, 13, 49);
		createWall(93, 326, 58, 12);
		createWall(148, 515, 30, 23);
		createWall(223, 507, 17, 52);
		createWall(198, 486, 4, 19);
		createWall(246, 352, 3, 78);
		createWall(222, 309, 4, 33);
		createWall(346, 288, 57, 10);

		Players[0] = Player0;
		Players[1] = Player1;
		createShibing(5, Player0);
		createShibing(5, Player1);
	}

	public void createWall(int positionX, int positionY, int laengeX,
			int laengeY) {
		Walls.addLast(new Wall(positionX, positionY, laengeX, laengeY));
		WallsToCreate.addFirst(Walls.getLast());
		for (int i = 0; i < laengeX; i++) {
			for (int j = 0; j < laengeY; j++) {
				kollision[positionX + i][positionY + j] = 5;
			}
		}
	}

	/*
	 * erstellt #amount Shibing für Spieler p. testet ob Startlocation frei,
	 * sonst testet daneben (X) macht alle Kollisionsfelder = Spielernummer
	 */
	public void createShibing(int amount, Player p) {
		int X;
		int Y;

		for (int i = 0; i < amount; i++) {
			if (p == Player0) {
				X = StartLocation1X;
				Y = StartLocation1Y;
				while ((kollision[X][Y] > 0
						|| kollision[X + Shibing.fatness][Y] > 0
						|| kollision[X][Y + Shibing.fatness] > 0 || kollision[X
						+ Shibing.fatness][Y + Shibing.fatness] > 0)
						&& X < mapsizeX - RahmenDicke - Shibing.fatness) {
					X += Shibing.fatness * 2;
				}
				for (int j = 0; j < Shibing.fatness; j++) {
					for (int k = 0; k < Shibing.fatness; k++) {
						kollision[X + j][Y + k] = 1;
					}
				}

			} else {
				X = StartLocation2X;
				Y = StartLocation2Y;
				while ((kollision[X][Y] > 0
						|| kollision[X + Shibing.fatness][Y] > 0
						|| kollision[X][Y + Shibing.fatness] > 0 || kollision[X
						+ Shibing.fatness][Y + Shibing.fatness] > 0)
						&& X < mapsizeX - RahmenDicke - Shibing.fatness) {
					X -= Shibing.fatness * 2;

				}
				for (int j = 0; j < Shibing.fatness; j++) {
					for (int k = 0; k < Shibing.fatness; k++) {
						kollision[X + j][Y + k] = 2;
					}
				}
			}
			p.getUnits().addLast(new Shibing(X, Y));
			ShibingCreated = true;
		}
	}

	public void forward(Shibing iShibing, int p) {

		for (int j = 0; j < Shibing.fatness; j++) {
			for (int k = 0; k < Shibing.fatness; k++) {
				kollision[(int) iShibing.getPositionX() + j][(int) iShibing
						.getPositionY() + k] = 0;
			}
		}

		iShibing.setPositionX((int) (iShibing.getPositionX() + (Math
				.sin(iShibing.getRichtung()) * Shibing.speed)));
		iShibing.setPositionY((int) (iShibing.getPositionY() + (Math
				.cos(iShibing.getRichtung()) * Shibing.speed)));

		for (int j = 0; j < Shibing.fatness; j++) {
			for (int k = 0; k < Shibing.fatness; k++) {
				kollision[(int) iShibing.getPositionX() + j][(int) iShibing
						.getPositionY() + k] = p + 1;
			}
		}
	}

	public void deleteShot(Shibing iShibing) {
		// delete kollide
		int x = iShibing.getShotData()[1];
		int y = iShibing.getShotData()[2];
		int x2 = iShibing.getShotData()[3];
		int y2 = iShibing.getShotData()[4];
		while (x < x2) {
			for (int j = 0; j < Schussbreite; j++) {
				kollision[(int) x][(int) y + j] = 0;
			}
			x += 1;
		}
		while (x > x2) {
			for (int j = 0; j < Schussbreite; j++) {
				kollision[(int) x][(int) y + j] = 0;
			}
			x -= 1;
		}
		while (y < y2) {
			for (int j = 0; j < Schussbreite; j++) {
				kollision[(int) x + j][(int) y] = 0;
			}
			y += 1;
		}
		while (y > y2) {
			for (int j = 0; j < Schussbreite; j++) {
				kollision[(int) x + j][(int) y] = 0;
			}
			y -= 1;
		}
		for (int i = 0; i < 5; i++) {
			iShibing.setShotData(i, 0);
		}
		// delete print -> auf view verweisen (attribut)
	}

	public boolean getKollide(int p, Shibing iShibing) {
		boolean kollide = false;
		int wohinX = (int) ((iShibing.getPositionX()) + Math.sin(iShibing
				.getRichtung()));
		int wohinY = (int) ((iShibing.getPositionY()) + Math.cos(iShibing
				.getRichtung() * Shibing.speed));

		// check Schuss(6) oder eigener Spieler(p+1) oder Wand(5)
		if (kollisionsCheck(6, p, iShibing) == true) {
			ShibingDeleteSelf(p, iShibing);
			checkgameover();
			kollide = true;
		} else {
			if (kollisionsCheck(p + 1, p, iShibing) == true
					|| kollisionsCheck(5, p, iShibing) == true)

			{
				kollide = true;
			} else {
				int Gegner = (p == 0) ? 2 : 1;
				if (kollisionsCheck(Gegner, p, iShibing) == true) {
					Shibingtot(wohinX, wohinY, p, iShibing);
				}

			}
		}
		return kollide;
	}

	public boolean kollisionsCheck(int a, int p, Shibing iShibing) {
		boolean kollide = false;
		int wohinX = (int) ((iShibing.getPositionX()) + Math.sin(iShibing
				.getRichtung() * Shibing.speed)); // NEW
		int wohinY = (int) ((iShibing.getPositionY()) + Math.cos(iShibing
				.getRichtung() * Shibing.speed));

		if (
		// Punkt linksoben
		(Math.sin(iShibing.getRichtung()) == -1 || Math.cos(iShibing
				.getRichtung()) == -1)
				&& kollision[wohinX][wohinY] == a
				||

				// Punkt rechtsoben
				(Math.sin(iShibing.getRichtung()) == 1 || Math.cos(iShibing
						.getRichtung()) == -1)
				&& kollision[wohinX + Shibing.fatness - 1][wohinY] == a

				||

				// Punkt linksunten
				(Math.sin(iShibing.getRichtung()) == -1 || Math.cos(iShibing
						.getRichtung()) == 1)
				&& kollision[wohinX][wohinY + Shibing.fatness - 1] == a

				||

				// Punkt rechtsunten
				(Math.sin(iShibing.getRichtung()) == 1 || Math.cos(iShibing
						.getRichtung()) == 1)
				&& kollision[wohinX + Shibing.fatness - 1][wohinY
						+ Shibing.fatness - 1] == a) {
			kollide = true;
		}
		return kollide;
	}

	// wird aufgerufen, wenn Shibing auf Shibing von anderem Spieler läuft.
	// Organisiert tot+löschen
	public void Shibingtot(double wohinX, double wohinY, int p, Shibing iShibing) {
		// p ist noch aktiver Spieler. Anderen Spieler berechnen
		int Gegner = (p == 0) ? 1 : 0;
		ShibingDeleteEat(Gegner, werShibing(wohinX, wohinY, Gegner, iShibing));
		checkgameover();
	}	

	// schaut welcher Shibing von player p an Ort xy ist. p ist schon Gegner
	public Shibing werShibing(double x, double y, int p, Shibing iShibing) {
		// Shibing iShibing = game1.Players[p].getUnits().get(
		// game1.Players[p].getClicked());
		for (int i = 0; Players[p].getUnits().size() > i; i++) {
			if ((
			// Punkt linksoben
			Math.sin(iShibing.getRichtung()) == -1 || Math.cos(iShibing
					.getRichtung()) == -1)
					&& Players[p].getUnits().get(i).getPositionX() <= x
					&& Players[p].getUnits().get(i).getPositionX()
							+ Shibing.fatness - 1 >= x
					&& Players[p].getUnits().get(i).getPositionY() <= y
					&& Players[p].getUnits().get(i).getPositionY()
							+ Shibing.fatness - 1 >= y
					||

					// Punkt rechtsoben
					(Math.sin(iShibing.getRichtung()) == 1 || Math.cos(iShibing
							.getRichtung()) == -1)
					&& Players[p].getUnits().get(i).getPositionX() <= x
							+ Shibing.fatness - 1
					&& Players[p].getUnits().get(i).getPositionX()
							+ Shibing.fatness - 1 >= x + Shibing.fatness - 1
					&& Players[p].getUnits().get(i).getPositionY() <= y
					&& Players[p].getUnits().get(i).getPositionY()
							+ Shibing.fatness - 1 >= y

					||

					// Punkt linksunten
					(Math.sin(iShibing.getRichtung()) == -1 || Math
							.cos(iShibing.getRichtung()) == 1)
					&& Players[p].getUnits().get(i).getPositionX() <= x
					&& Players[p].getUnits().get(i).getPositionX()
							+ Shibing.fatness - 1 >= x
					&& Players[p].getUnits().get(i).getPositionY() <= y
							+ Shibing.fatness - 1
					&& Players[p].getUnits().get(i).getPositionY()
							+ Shibing.fatness - 1 >= y + Shibing.fatness - 1

					||

					// Punkt rechtsunten
					(Math.sin(iShibing.getRichtung()) == 1 || Math.cos(iShibing
							.getRichtung()) == 1)
					&& Players[p].getUnits().get(i).getPositionX() <= x
							+ Shibing.fatness - 1
					&& Players[p].getUnits().get(i).getPositionX()
							+ Shibing.fatness - 1 >= x + Shibing.fatness - 1
					&& Players[p].getUnits().get(i).getPositionY() <= y
							+ Shibing.fatness - 1
					&& Players[p].getUnits().get(i).getPositionY()
							+ Shibing.fatness - 1 >= y + Shibing.fatness - 1)

			{
				StelleInListe = i;
				return Players[p].getUnits().get(i);
			}
		}
		return null;
	}

	// löscht den eigenen Shibing
	public void ShibingDeleteSelf(int p, Shibing iShibing) {
		DeleteShibing = Players[p].getUnits().get(Players[p].getClicked());// View
																			// wird
		// gelöscht
		// Kollide[][] null setzen
		for (int j = 0; j <= Shibing.fatness; j++) {
			for (int k = 0; k <= Shibing.fatness; k++) {
				kollision[iShibing.getPositionX() + j][iShibing.getPositionY()
						+ k] = 0;
			}
		}
		// aus Player Liste löschen.
		Players[p].getUnits().remove(Players[p].getClicked());
		Players[p].setClicked(0);
	}

	// löscht den gegner
	public void ShibingDeleteEat(int p, Shibing iShibing) {
		DeleteShibing = Players[p].getUnits().get(StelleInListe);// View wird
																	// gelöscht
		// Kollide[][] null setzen
		for (int j = 0; j <= Shibing.fatness; j++) {
			for (int k = 0; k <= Shibing.fatness; k++) {
				kollision[iShibing.getPositionX() + j][iShibing.getPositionY()
						+ k] = 0;
			}
		}
		// aus Player Liste löschen.
		Players[p].getUnits().remove(StelleInListe);
		Players[p].setClicked(0);
	}

	// noch nicht ausgeführt
	public void checkgameover() {
		if (Player0.getUnits().size() <= 0 || Player1.getUnits().size() <= 0) {
			System.out.println("Game Over");
			GameOver = true;
		}
	}

	public Color getP1color() {
		return p1color;
	}

	public void setP1color(Color p1color) {
		this.p1color = p1color;
	}

	public LinkedList<Wall> getWalls() {
		return Walls;
	}

	public void setWalls(LinkedList<Wall> walls) {
		Walls = walls;
	}

	public Color getWallcolor() {
		return Wallcolor;
	}

	public void setWallcolor(Color wallcolor) {
		Wallcolor = wallcolor;
	}

	public int getKeyShot2() {
		return KeyShot2;
	}

	public void setKeyShot2(int keyShot2) {
		KeyShot2 = keyShot2;
	}

	public int getKeyShot1() {
		return KeyShot1;
	}

	public void setKeyShot1(int keyShot1) {
		KeyShot1 = keyShot1;
	}

	public int[] getKollideShot(Shibing iShibing) {
		boolean kollide = false;
		int[] aim = new int[2];
		int wohinX = (int) ((iShibing.getPositionX()) + Math.sin(iShibing
				.getRichtung()));
		int wohinY = (int) ((iShibing.getPositionY()) + Math.cos(iShibing
				.getRichtung() * Shibing.speed));
		while (kollide == false) {// +((Shibing.fatness-Schussbreite)/2)
			if ((Math.sin(iShibing.getRichtung()) == -1 || Math.cos(iShibing
					.getRichtung()) == -1)
					&& kollision[wohinX][wohinY] == 5
					||

					// Punkt rechtsoben
					(Math.sin(iShibing.getRichtung()) == 1 || Math.cos(iShibing
							.getRichtung()) == -1)
					&& kollision[wohinX + Shibing.fatness - 1][wohinY] == 5

					||

					// Punkt linksunten
					(Math.sin(iShibing.getRichtung()) == -1 || Math
							.cos(iShibing.getRichtung()) == 1)
					&& kollision[wohinX][wohinY + Shibing.fatness - 1] == 5

					||

					// Punkt rechtsunten
					(Math.sin(iShibing.getRichtung()) == 1 || Math.cos(iShibing
							.getRichtung()) == 1)
					&& kollision[wohinX + Shibing.fatness - 1][wohinY
							+ Shibing.fatness - 1] == 5) {
				kollide = true;
				aim[0] = wohinX;
				aim[1] = wohinY;
			} else {
				wohinX += (int) (Math.sin(iShibing.getRichtung()
						* Shibing.speed));
				wohinY += (int) (Math.cos(iShibing.getRichtung()
						* Shibing.speed));
			}
		}
		return aim;
	}

	public void shot(Shibing iShibing, int[] aim) {
		int x = iShibing.getPositionX();
		int y = iShibing.getPositionY();
		ShotPrintData[1] = x;
		iShibing.setShotData(1, x);
		ShotPrintData[2] = y;
		iShibing.setShotData(2, y);
		while (x < aim[0]) {
			for (int j = 0; j < Schussbreite; j++) {
				kollision[(int) x][(int) y + j] = 6;
			}
			x += (int) (Math.sin(iShibing.getRichtung() * Shibing.speed));
		}
		while (x > aim[0]) {
			for (int j = 0; j < Schussbreite; j++) {
				kollision[(int) x][(int) y + j] = 6;
			}
			x += (int) (Math.sin(iShibing.getRichtung() * Shibing.speed));
		}
		while (y < aim[1]) {
			for (int j = 0; j < Schussbreite; j++) {
				kollision[(int) x + j][(int) y] = 6;
			}
			y += (int) (Math.cos(iShibing.getRichtung() * Shibing.speed));
		}
		while (y > aim[1]) {
			for (int j = 0; j < Schussbreite; j++) {
				kollision[(int) x + j][(int) y] = 6;
			}
			y += (int) (Math.cos(iShibing.getRichtung() * Shibing.speed));
		}

		ShotPrintData[3] = x;
		ShotPrintData[4] = y;
		ShotPrintData[0] = 1;

		iShibing.setShotData(3, x);
		iShibing.setShotData(4, y);
		iShibing.setShotData(0, 1);
	}

	public int getSchussbreite() {
		return Schussbreite;
	}

	public void setSchussbreite(int schussbreite) {
		Schussbreite = schussbreite;
	}

	public Color getShotcolor() {
		return Shotcolor;
	}

	public void setShotcolor(Color shotcolor) {
		Shotcolor = shotcolor;
	}
}