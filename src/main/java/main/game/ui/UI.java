package main.game.ui;

import java.util.HashMap;

public class UI {

	public final HashMap<String, UIComponent> components;
	
	public UI() {
		this.components = new HashMap<>();
	}
	
	public void addComponent(String name, UIComponent component) {
		this.components.put(name, component);
	}
	
	public UIComponent getComponent(String name) {
		return this.components.get(name);
	}
}
