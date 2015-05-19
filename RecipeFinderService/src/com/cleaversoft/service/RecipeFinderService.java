package com.cleaversoft.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.cleaversoft.domain.FridgeItem;
import com.cleaversoft.domain.FridgeItemUnit;
import com.cleaversoft.domain.Ingredient;
import com.cleaversoft.domain.Recipe;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;


public class RecipeFinderService {
	

    private ArrayList<FridgeItem> fridgeItems = new ArrayList<FridgeItem>();
	private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
	
	public void loadRecipe(String recipesJsonString) {
		
		//read file with recipe json data
	    
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode recipeJsonNode = null;
	    
	    try {
			recipeJsonNode = mapper.readTree(recipesJsonString);
			
			Iterator<JsonNode> ite = recipeJsonNode.elements();
			 
			while (ite.hasNext()) {
				JsonNode temp = ite.next();
				ObjectMapper jsonObjectMapper = new ObjectMapper();
				try {
					Recipe recipe = jsonObjectMapper.treeToValue(temp, Recipe.class);
					recipes.add(recipe);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	   
		
	}
	
	public void loadFridgeItems(String inputCsvPath) {

		try {
			
        	InputStream is = getClass().getResourceAsStream(inputCsvPath);
        	Reader reader = new InputStreamReader(is);
        	CSVReader csvReader = new CSVReader(reader);
            String [] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
            	
	        	//todo: handle parsing errors here
	        	String item = nextLine[0];
	        	int amount = Integer.parseInt(nextLine[1]);
	        	FridgeItemUnit unitEnum = FridgeItemUnit.valueOf(nextLine[2].toLowerCase());
	        	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        	Date usedBy = dateFormat.parse(nextLine[3]);
	        	
	        	fridgeItems.add(new FridgeItem(item, amount, unitEnum, usedBy));

            }
	        
            
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		
	}
	
	public String findRecipe() {
				
		if(recipes!=null && fridgeItems!=null) {
			
			ArrayList <Recipe> potencialRecipes = new ArrayList<Recipe>();
			Recipe bestRecipe = null;
			potencialRecipes = (ArrayList<Recipe>) findRecipesByAvailableFridgeItems();
 
			if (potencialRecipes.size()==0) {
				return "Order  Takeout";
			} else {
				bestRecipe= findRecipeByShortestExpiryDate(potencialRecipes);
				
				if(bestRecipe!=null && bestRecipe.getName()!=null) return bestRecipe.getName();
				else return "We are sorry, something whent wrong";
			}
			
			
		} else {
			return "No ingredients or recipes provided";
		}
		
	}
	

	private List<Recipe> findRecipesByAvailableFridgeItems(){
		ArrayList <Recipe> potencialRecipes = new ArrayList<Recipe>();
		
		if(recipes!=null && fridgeItems!=null) {		
			for (Recipe recipe : recipes) {
				boolean ingredientsReady = true;
			    for(Ingredient ingredient: recipe.getIngredients()) {
			    	//test if this specific ingredient for the recipe can be cover by any items in fridge, assuming none of the items are expired already
			    	//also test which ingredient expire first and choose recipe to utilise those ingredients
			    	//iterate through fridge items
			    	boolean isInFridge = false;
			    	for(FridgeItem fridgeItem: fridgeItems) {
			    		if(fridgeItem.getItem().equals(ingredient.getItem()) && fridgeItem.getUnit()==ingredient.getUnit() && fridgeItem.getAmount()>=ingredient.getAmount() && fridgeItem.getUsedBy().after(new Date())) {
			    			isInFridge = true;
			    		}
			    	}
			    	
			    	if(!isInFridge) {
			    		ingredientsReady=false;
			    		break;
			    	}
			    }
			    if(ingredientsReady) potencialRecipes.add(recipe);
			}
		}
		
		return potencialRecipes;
	}
	
	private Recipe findRecipeByShortestExpiryDate(ArrayList <Recipe> potencialRecipes){
		//test which ingredient expire first and choose recipe to utilise this ingredient first
		//this part perhaps could be incorporated into previous loop, but I did not want to cloud logic too much
		Recipe bestRecipe= null;
		Date shortestExpiryDate = null;
		for (Recipe recipe : potencialRecipes) {
			for(Ingredient ingredient: recipe.getIngredients()) {
				
				FridgeItem fridgeItem = findFridgeItemByName(ingredient.getItem()) ;
				if(fridgeItem!=null && fridgeItem.getUsedBy()!=null ) {
					if(shortestExpiryDate==null) {
						shortestExpiryDate = fridgeItem.getUsedBy();
						bestRecipe = recipe;
					} else {
						if(fridgeItem.getUsedBy().before(shortestExpiryDate)) {
							shortestExpiryDate = fridgeItem.getUsedBy();
							bestRecipe = recipe;								
						}
					}
				} 
						
				
			}
			
		}
		
		return bestRecipe;
	}
	
	private FridgeItem findFridgeItemByName (String name) {
		for (FridgeItem fridgeItem: fridgeItems) {
			if (fridgeItem.getItem().equals(name)) {
				return fridgeItem;
			}
		}
		return null;
	}
}
