package com.cleaversoft.test.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cleaversoft.service.RecipeFinderService;

public class TestRecipeFinder {

	RecipeFinderService recipeFinderService = new RecipeFinderService();
	
	@Test
	public void findTheBestRecipe() {
		
		String recipesJsonString = "[  {  \"name\": \"grilled cheese on toast\", \"ingredients\": [  { \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"}, { \"item\":\"cheese\", \"amount\":\"2\", \"unit\":\"slices\"}   ]  }  ,  {  \"name\": \"salad sandwich\", \"ingredients\": [ { \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},   { \"item\":\"mixed salad\", \"amount\":\"100\", \"unit\":\"grams\"}   ]  }  ,  {  \"name\": \"eat fast sandwich\", \"ingredients\": [ { \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},   { \"item\":\"peanut butter\", \"amount\":\"50\", \"unit\":\"grams\"}   ]  }   ] ";
		String frigeCsvPath = "/com/cleaversoft/test/junit/resources/fridge.csv";
		
		//init recipeFinderService
		recipeFinderService.loadRecipe(recipesJsonString);
		recipeFinderService.loadFridgeItems(frigeCsvPath);	
		
		assertEquals(recipeFinderService.findRecipe(), "eat fast sandwich");

	}
	
	@Test
	public void allFrigeItemsAreExpired() {
		
		String recipesJsonString = "[  {  \"name\": \"grilled cheese on toast\", \"ingredients\": [  { \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"}, { \"item\":\"cheese\", \"amount\":\"2\", \"unit\":\"slices\"}   ]  }  ,  {  \"name\": \"salad sandwich\", \"ingredients\": [ { \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},   { \"item\":\"mixed salad\", \"amount\":\"100\", \"unit\":\"grams\"}   ]  }  ,  {  \"name\": \"eat fast sandwich\", \"ingredients\": [ { \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},   { \"item\":\"peanut butter\", \"amount\":\"50\", \"unit\":\"grams\"}   ]  }   ] ";
		String frigeCsvPath = "/com/cleaversoft/test/junit/resources/fridge_all_items_expired.csv";
		
		//init recipeFinderService
		recipeFinderService.loadRecipe(recipesJsonString);
		recipeFinderService.loadFridgeItems(frigeCsvPath);	
		
		assertEquals(recipeFinderService.findRecipe(), "Order  Takeout");

	}
	

}
