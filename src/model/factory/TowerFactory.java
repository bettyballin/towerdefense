package model.factory;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.NOTEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import eea.engine.interfaces.IEntityFactory;
import model.actions.DeleteTowerAction;
import model.actions.MakeUpdateSelectionUnvisibleAction;
import model.actions.MakeUpdateSelectionVisibleAction;
import model.actions.ShootAction;
import model.actions.UpdateTowerAction;
import model.entities.Tower;
import model.events.MouseOnLeftHalfEvent;
import model.events.MouseOnRightHalfEvent;
import model.events.EnemyDetectionEvent;
import model.events.TimeEvent;
import ui.Towerdefense;

public class TowerFactory implements IEntityFactory {

	private final String towerType;
	private final int cost;
	private final int strength;
	private final int speed;
	private final int slowdown;
	private final int range;
	private final Vector2f position;

	/**
	 * Constructs a tower factory
	 * 
	 * @param towerType
	 *            given type of tower, f.e. bulletTower or iceTower
	 * @param position
	 *            position of tower
	 */
	public TowerFactory(String towerType, Vector2f position) {
		this.position = position;
		this.towerType = towerType;
		int[] stats = { 0, 0, 0, 0, 0 };
		if (towerType == "bulletTower") {
			stats = Towerdefense.bulletTower;
		} else if (towerType == "iceTower") {
			stats = Towerdefense.iceTower;
		} else
			stats = Towerdefense.homeTower;

		this.cost = stats[0];
		this.strength = stats[1];
		this.speed = stats[2];
		this.slowdown = stats[3];
		this.range = stats[4];
	}

	@Override
	public Entity createEntity() {
		Tower tower = new Tower(towerType);
		tower.setSpeed(speed);
		tower.setCosts(cost);
		tower.setStrength(strength);
		tower.setSlowdown(slowdown);
		tower.setPosition(position);
		tower.setRange(range);

		try {
			tower.addComponent(new ImageRenderComponent(new Image("assets/" + towerType + ".png")));
		} catch (SlickException e) {
			System.err.println("assets/" + towerType + ".png not found in TowerFactory.java");
		}

		Float speed = tower.getSpeed();

		// shoot at the speed of tower if enemy is detected
		Event shoot = new ANDEvent(new EnemyDetectionEvent("enemydetected", tower),
				new TimeEvent(speed.intValue(), true));
		shoot.addAction(new ShootAction());
		tower.addComponent(shoot);

		// distinguish hometower from other towers, because buttons don't exist for
		// hometower
		if (towerType != "homeTower") {
			
			// make buttons for update and delete visible when mouse over tower
			Event onTower = new MouseEnteredEvent();
			onTower.addAction(new MakeUpdateSelectionVisibleAction(towerType));
			tower.addComponent(onTower);

			// make buttons for update and delete unvisible when mouse is not over tower
			Event offTower = new NOTEvent(onTower);
			offTower.addAction(new MakeUpdateSelectionUnvisibleAction(towerType));
			tower.addComponent(offTower);

			// if ButtonCLickLeftEvent is true, update tower
			Event buttonclickleft = new ANDEvent(new MouseOnLeftHalfEvent(), new MouseClickedEvent());
			buttonclickleft.addAction(new UpdateTowerAction());

			// if MouseOnRightHalfEvent is true, delete tower
			Event buttonclickright = new ANDEvent(new MouseOnRightHalfEvent(), new MouseClickedEvent());
			buttonclickright.addAction(new DeleteTowerAction());

			tower.addComponent(buttonclickright);
			tower.addComponent(buttonclickleft);
		}
		return tower;
	}

}
