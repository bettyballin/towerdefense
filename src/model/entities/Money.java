package model.entities;

import eea.engine.entity.Entity;
import ui.Towerdefense;

public class Money extends Entity{

	private int amount;

	/**
	 * Constructs a money entity with the given stats from Towerdefense
	 */
	public Money(String entityID) {
		super(entityID);
		this.amount = Towerdefense.startBudget;
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	public int getAmount(){
		return this.amount;
	}
	public void changeAmount(int amount){
		this.amount += amount;
	}

}
