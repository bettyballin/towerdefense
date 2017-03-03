package ui;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import eea.engine.entity.StateBasedEntityManager;
import model.options.Options;

/**
 * @author Bettina Ballin, Laura Imler
 */
public class Towerdefense extends StateBasedGame {

	// Jeder State wird durch einen Integer-Wert gekennzeichnet
	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;
	public static final int OPTIONSTATE = 2;
	public static final int[] spiderEnemyEinfach = { 10, 8}; // life, speed
	public static final int[] spiderEnemyNormal = {15, 12  }; // life, speed
	public static final int[] spiderEnemySchwer = { 20, 15 }; // life, speed
	public static final int[] wespEnemy = { 15, 15 }; // life, speed
	public static final int[] bulletTower = { 50, 5, 600, 0, 250 }; // costs, strength, speed, slowdown, range
	public static final int[] iceTower = { 80, 3, 500, 5, 250 }; // costs, strength, speed, slowdown, range
	public static final int[] homeTower = { 0, 6, 600, 2, 400 }; // costs, strength, speed, slowdown, range
	public static final int startBudget = 100;
	public static final int moneyPerEnemy = 5;
	public static final int life = 20;

	public Towerdefense() {
		super("Towerdefense");
	}

	public static void main(String[] args) throws SlickException {
		// Setze den library Pfad abhaengig vom Betriebssystem
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/windows");
		} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/macosx");
		} else {
			System.setProperty("org.lwjgl.librarypath",
					System.getProperty("user.dir") + "/native/" + System.getProperty("os.name").toLowerCase());
		}
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		System.err.println(System.getProperty("os.name") + ": " + System.getProperty("org.lwjgl.librarypath"));
		// Setze dieses StateBasedGame in einen App Container (oder Fenster)
		AppGameContainer app = new AppGameContainer(new Towerdefense());

		// Lege die Einstellungen des Fensters fest und starte das Fenster
		app.setShowFPS(false);
		app.setDisplayMode(800, 600, false);

		Options.getInstance().toggleSound();

		app.start();
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {

		// Fuege dem StateBasedGame die State's hinzu
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GameplayState(GAMEPLAYSTATE));
		this.addState(new OptionsState(OPTIONSTATE));

		// Fuege dem StateBasedEntityManager die State's hinzu
		StateBasedEntityManager.getInstance().addState(MAINMENUSTATE);
		StateBasedEntityManager.getInstance().addState(GAMEPLAYSTATE);
		StateBasedEntityManager.getInstance().addState(OPTIONSTATE);
	}
}