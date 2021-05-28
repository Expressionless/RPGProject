package main.game;

import main.game.item.ItemType;

public class WriteItems {

	public static void main(String[] args) {

		
		boolean[] STACKABLE = {true, false, false, false};
		boolean[] NOT_STACKABLE = {false, false, false, false};
		
		ItemType.beginWriting();
		ItemType.addItem(0, "grass", STACKABLE);
		ItemType.addItem(1, "wood", STACKABLE);
		ItemType.addItem(2, "sword", NOT_STACKABLE);
		ItemType.addItem(3, "bow", NOT_STACKABLE);
		ItemType.stopWriting();
		/*
		ItemType.beginReading();
		ItemType item = ItemType.parseItemType(0);
		item = ItemType.parseItemType(1);
		item = ItemType.parseItemType(2);
		item = ItemType.parseItemType(3);
		ItemType.stopReading();*/
	}
	
}
