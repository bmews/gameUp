package game;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;


public class View extends Applet implements KeyListener {
	private static final long serialVersionUID = 1L;

	Game game1 = new Game();
	protected Graphics g;
	protected Thread th;

	protected int MouseX, MouseY;
	private boolean KeyShowMap = false;
	// s||arrow down
	private boolean KeySwitch[] = new boolean[2];

	private boolean KeyShot[] = new boolean[2];

	public boolean isKeyShowMap() {
		return KeyShowMap;
	}

	public void setKeyShowMap(boolean keyShowMap) {
		KeyShowMap = keyShowMap;
	}

	public boolean[] getKeySwitch() {
		return KeySwitch;
	}

	public boolean[] getKeyShot() {
		return KeyShot;
	}

	public void setKeySwitch(boolean[] keySwitch) {
		KeySwitch = keySwitch;
	}

	public void setKeyShot(boolean[] keyShot) {
		KeySwitch = keyShot;
	}

	public boolean[] getKeyForward() {
		return KeyForward;
	}

	public void setKeyForward(boolean[] keyForward) {
		KeyForward = keyForward;
	}

	public boolean[] getKeyLeRi() {
		return KeyLeRi;
	}

	public void setKeyLeRi(boolean[] keyLeRi) {
		KeyLeRi = keyLeRi;
	}

	// w|| arroKeyForward
	private boolean KeyForward[] = new boolean[2];
	// 0: p1 nach links, 1: p2 nach links, 2: p1 nach rechts, 3: p2 nach rechts
	private boolean KeyLeRi[] = new boolean[4];

	public static void main(String args[]) {
		//new Game();
	}

	public void init() {

		this.setSize(new Dimension(game1.getMapsizeX(), game1.getMapsizeY()));
		g = getGraphics();
		th = new Painter();
		addKeyListener(this);
		addMouseMotionListener(new MyMouseListener());
		th.start();
	}

	private class MyMouseListener extends MouseMotionAdapter {
		int x, y, b, h;
		boolean done = false;

		public void mouseMoved(MouseEvent e) {
			if (done == true) {
				b = Math.abs(e.getX() - x);
				h = Math.abs(e.getY() - y);
				System.out.println("createWall(" + x + "," + y + "," + b + ","
						+ h + ");");
				game1.createWall(x, y, b, h);
				done = false;
			}
			MouseX = e.getX();
			MouseY = e.getY();

		}

		// keine Auswirkungen sonst bis jetzt
		public void mouseDragged(MouseEvent e) {
			int newx = e.getX();
			int newy = e.getY();
			g.drawLine(MouseX, MouseY, newx, newy);
			MouseX = newx;
			MouseY = newy;

			if (done == false) {
				x = e.getX();
				y = e.getY();
				done = true;
			}

		}

	}

	// keylistener
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == game1.getKeyClose()) // esc
			System.exit(0);
		if (e.getKeyCode() == game1.getKeyShowMap()) // alt
			KeyShowMap = true;
		if (e.getKeyCode() == game1.getKeyLeft1())
			KeyLeRi[0] = true;
		if (e.getKeyCode() == game1.getKeyRight1())
			KeyLeRi[1] = true;
		if (e.getKeyCode() == game1.getKeyLeft2())
			KeyLeRi[2] = true;
		if (e.getKeyCode() == game1.getKeyRight2())
			KeyLeRi[3] = true;
		if (e.getKeyCode() == game1.getKeySwitch1())
			KeySwitch[0] = true;
		if (e.getKeyCode() == game1.getKeySwitch2())
			KeySwitch[1] = true;
		if (e.getKeyCode() == game1.getKeyForward1())
			KeyForward[0] = true;
		if (e.getKeyCode() == game1.getKeyForward2())
			KeyForward[1] = true;
		if (e.getKeyCode() == game1.getKeyShot1())
			KeyShot[0] = true;
		if (e.getKeyCode() == game1.getKeyShot2())
			KeyShot[1] = true;
	}

	public void keyReleased(KeyEvent arg0) {

	}

	public void keyTyped(KeyEvent arg0) {

	}

	public void setPlayerColor(int i) {
		if (i == 0) {
			g.setColor(game1.getP1color());
		} else {
			g.setColor(game1.getP2color());
		}
	}

	public void paintShibing(Shibing iShibing, int p) {
		setPlayerColor(p);
		g.fillOval(iShibing.getPositionX(), iShibing.getPositionY(),
				Shibing.fatness, Shibing.fatness);
	}

	public void deleteShibingView(Shibing iShibing, boolean moved) {
		g.setColor(Color.white);
		if (moved == true) {
			g.fillRect(
					(int) (iShibing.getPositionX() - Math.sin(iShibing
							.getRichtung()) * Shibing.speed),
					(int) (iShibing.getPositionY() - Math.cos(iShibing
							.getRichtung()) * Shibing.speed), Shibing.fatness,
					Shibing.fatness);
		} else {
			g.fillRect((int) (iShibing.getPositionX()),
					(int) (iShibing.getPositionY()), Shibing.fatness,
					Shibing.fatness);
		}
	}

	public void showMap() {
		if (KeyShowMap == true) {
			KeyShowMap = false;
			for (int i = 0; i < game1.getPlayers().length; i++) {
				setPlayerColor(i);
				for (int j = 0; game1.getPlayers()[i].getUnits().size() > j; j++) {
					g.fillOval(game1.getPlayers()[i].getUnits().get(j)
							.getPositionX(), game1.getPlayers()[i].getUnits()
							.get(j).getPositionY(), Shibing.fatness,
							Shibing.fatness);
				}
			}
		}
	}

	// TODO auch in printer ändern, walls nicht immer anzeigen!!
	public void showWalls(int positionX, int positionY, int laengeX, int laengeY) {
		g.setColor(Color.black);
		g.fillRect(positionX, positionY, laengeX, laengeY);
	}

	public void printgameOver() {
		g.setColor(Color.black);
		g.fillOval(game1.getMapsizeX() / 2, game1.getMapsizeY() / 2,
				Shibing.fatness * 3, Shibing.fatness * 3);
	}

	public void printShibingCreated() {
		if (game1.ShibingCreated == true) {
			for (int i = 0; i < game1.getPlayers().length; i++) {
				for (int j = 0; game1.getPlayers()[i].getUnits().size() > j; j++) {
					setPlayerColor(i);
					g.fillOval(game1.getPlayers()[i].getUnits().get(j)
							.getPositionX(), game1.getPlayers()[i].getUnits()
							.get(j).getPositionY(), Shibing.fatness,
							Shibing.fatness);
				}
			}
			game1.ShibingCreated = false;
		}
	}

	private void printWallcreated() {

		if (game1.WallsToCreate.isEmpty() == false) {
			g.setColor(game1.getWallcolor());
			g.fillRect(game1.WallsToCreate.getFirst().getxPos(),
					game1.WallsToCreate.getFirst().getyPos(),
					game1.WallsToCreate.getFirst().getBreite(),
					game1.WallsToCreate.getFirst().getHoehe());
			game1.WallsToCreate.remove(0);
		}

	}

	public void printShibingDelete() {
		if (game1.DeleteShibing != null) {
			deleteShibingView(game1.DeleteShibing, false);
			game1.DeleteShibing = null;
		}
	}

	public void keySwitch(int p) {
		for (p = 0; p < KeySwitch.length; p++) {

			if (KeySwitch[p] == true) {
				game1.getPlayers()[p]
						.setClicked((game1.getPlayers()[p].getUnits().size() - 1 > game1
								.getPlayers()[p].getClicked()) ? (game1
								.getPlayers()[p].getClicked() + 1) : 0);
				KeySwitch[p] = false;
			}
		}
	}

	public void keyLeRi(int p) {
		for (p = 0; p < KeyLeRi.length; p++) {
			int player = (p == 0 || p == 1) ? 0 : 1;
			Shibing iShibing = game1.getPlayers()[player].getUnits().get(
					game1.getPlayers()[player].getClicked());
			boolean r = (KeyLeRi[1] || KeyLeRi[3]) ? true : false;
			if (KeyLeRi[p] == true) {
				if (iShibing.getRichtung() > 3 && r == false) {
					iShibing.setRichtung(-Math.PI / 2);
				} else {
					if (iShibing.getRichtung() < -3 && r == true) {
						iShibing.setRichtung(+Math.PI / 2);
					} else {

						iShibing.setRichtung((r == true) ? iShibing
								.getRichtung() - (Math.PI / 2) : iShibing
								.getRichtung() + (Math.PI / 2));
					}
				}
			}
			KeyLeRi[p] = false;
		}
	}

	public void keyForward(int p) {
		for (p = 0; p < KeyForward.length; p++) {
			Shibing iShibing = game1.getPlayers()[p].getUnits().get(
					game1.getPlayers()[p].getClicked());
			if (KeyForward[p] == true) {
				if (iShibing.getShotData()[0] == 1) {
					System.out.println("deleteshot");
					deleteShotPrint(iShibing);
					game1.deleteShot(iShibing);
				}
				if (!game1.getKollide(p, iShibing)) {
					game1.forward(iShibing, p);
					deleteShibingView(iShibing, true);
					paintShibing(iShibing, p);
				}
			}
			KeyForward[p] = false;
		}
	}

	public void deleteShotPrint(Shibing iShibing) {
		g.setColor(Color.white);
		if (game1.getShotPrintData()[1] < game1.getShotPrintData()[3]) {
			g.fillRect(
					game1.getShotPrintData()[1] + Shibing.fatness,
					game1.getShotPrintData()[2]
							+ ((Shibing.fatness - game1.getSchussbreite()) / 2),
					Math.abs(game1.getShotPrintData()[3]
							- game1.getShotPrintData()[1]),
					game1.getSchussbreite());
		}

		if (game1.getShotPrintData()[1] > game1.getShotPrintData()[3]) {
			g.fillRect(
					game1.getShotPrintData()[3],
					game1.getShotPrintData()[4] + (Shibing.fatness / 2),
					Math.abs(game1.getShotPrintData()[3]
							- game1.getShotPrintData()[1]),
					game1.getSchussbreite());
		}

		if (game1.getShotPrintData()[2] < game1.getShotPrintData()[4]) {
			g.fillRect(
					game1.getShotPrintData()[1] + (Shibing.fatness / 2),
					game1.getShotPrintData()[2] + Shibing.fatness,
					game1.getSchussbreite(),
					Math.abs(game1.getShotPrintData()[4]
							- game1.getShotPrintData()[2]));
		}

		if (game1.getShotPrintData()[2] > game1.getShotPrintData()[4]) {
			g.fillRect(
					game1.getShotPrintData()[3] + (Shibing.fatness / 2),
					game1.getShotPrintData()[4],
					game1.getSchussbreite(),
					Math.abs(game1.getShotPrintData()[4]
							- game1.getShotPrintData()[2]));
		}
	}

	public void keyShot(int p) {
		for (p = 0; p < KeyShot.length; p++) {
			Shibing iShibing = game1.getPlayers()[p].getUnits().get(
					game1.getPlayers()[p].getClicked());
			if (KeyShot[p] == true) {
				int[] aim = game1.getKollideShot(iShibing);
				game1.shot(iShibing, aim);
			}
			KeyShot[p] = false;
		}
	}

	private void printShot() {

		if (game1.getShotPrintData()[0] == 1) {
			g.setColor(game1.getShotcolor());
			if (game1.getShotPrintData()[1] < game1.getShotPrintData()[3]) {
				g.fillRect(
						game1.getShotPrintData()[1] + Shibing.fatness,
						game1.getShotPrintData()[2]
								+ ((Shibing.fatness - game1.getSchussbreite()) / 2),
						Math.abs(game1.getShotPrintData()[3]
								- game1.getShotPrintData()[1]),
						game1.getSchussbreite());
			}

			if (game1.getShotPrintData()[1] > game1.getShotPrintData()[3]) {
				g.fillRect(
						game1.getShotPrintData()[3],
						game1.getShotPrintData()[4] + (Shibing.fatness / 2),
						Math.abs(game1.getShotPrintData()[3]
								- game1.getShotPrintData()[1]),
						game1.getSchussbreite());
			}

			if (game1.getShotPrintData()[2] < game1.getShotPrintData()[4]) {
				g.fillRect(
						game1.getShotPrintData()[1] + (Shibing.fatness / 2),
						game1.getShotPrintData()[2] + Shibing.fatness,
						game1.getSchussbreite(),
						Math.abs(game1.getShotPrintData()[4]
								- game1.getShotPrintData()[2]));
			}

			if (game1.getShotPrintData()[2] > game1.getShotPrintData()[4]) {
				g.fillRect(
						game1.getShotPrintData()[3] + (Shibing.fatness / 2),
						game1.getShotPrintData()[4],
						game1.getSchussbreite(),
						Math.abs(game1.getShotPrintData()[4]
								- game1.getShotPrintData()[2]));
			}

			game1.getShotPrintData()[0] = 0;
		}
	}

	/*
	 * komplette Anzeige + Aktionen (Ändern??). Anzeige am Anfang funktioniert
	 * bis jetzt nicht. space lässt alles erscheinen Alle "sleep timer" wird neu
	 * gecheckt ob gedrückt wurde. Dann Daten ändern. Anzeige wird nur verändert
	 * wenn KeyForward / nach vorne laufen.
	 */
	private class Painter extends Thread {
		public void run() {
			int p = 0;
			setBackground(Color.white);
			try {
				sleep(100);
				KeyShowMap = true;
				while (true) {
					if (game1.GameOver == true) {
						printgameOver();
					} else {
						printWallcreated();
						printShibingCreated();
						printShibingDelete();
						printShot();
						showMap(); // Prints walls + shibing
						keySwitch(p); // KeySwitch
						keyLeRi(p); // KeyLeRi
						keyForward(p);// KeyForward
						keyShot(p);// KeyShot

					}
					sleep(25);
				}
			} catch (Exception e) {
				System.out.println("Thread exception");
			}
		}

	}

	public void stop() {
		th = null;
	}
}