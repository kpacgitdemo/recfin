package com.cleaversoft.domain;

import java.util.ArrayList;

public class Recipe {
	String name;
	ArrayList <Ingredient> ingredients;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	

}
