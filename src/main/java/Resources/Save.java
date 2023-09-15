package Resources;
import Models.BakedGoods;
import Models.Ingredients;
import Models.Recipe;

import java.io.*;


public class Save {
	public void saveDataToTextFiles(LinkedList<BakedGoods> bakedGoodsLinkedList,
	                                 LinkedList<Ingredients> ingredientsLinkedList,
	                                 LinkedList<Recipe> recipeList) {
		saveBakedGoodsToTextFile(bakedGoodsLinkedList);
		saveIngredientsToTextFile(ingredientsLinkedList);
		saveRecipesToTextFile(recipeList);
		System.out.println("Data saved to text files successfully.");
	}

	private void saveBakedGoodsToTextFile(LinkedList<BakedGoods> bakedGoodsLinkedList) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/Data/BakedGoods.txt"))) {
			for (int i = 0; i < bakedGoodsLinkedList.numNodes(); i++) {
				BakedGoods bakedGood = bakedGoodsLinkedList.get(i);
				writer.write("Name: " + bakedGood.getGoodsName() + "\n");
				writer.write("Origin: " + bakedGood.getOriginCountry() + "\n");
				writer.write("Description: " + bakedGood.getGoodsDesc() + "\n");
				writer.write("URL: " + bakedGood.getURL() + "\n");
				writer.write("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveIngredientsToTextFile(LinkedList<Ingredients> ingredientsLinkedList) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/Data/Ingredients.txt"))) {
			for (int i = 0; i < ingredientsLinkedList.numNodes(); i++) {
				Ingredients ingredient = ingredientsLinkedList.get(i);
				writer.write("Name: " + ingredient.getIngName() + "\n");
				writer.write("Description: " + ingredient.getIngDesc() + "\n");
				writer.write("Calories: " + ingredient.getCalories() + "\n");
				writer.write("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveRecipesToTextFile(LinkedList<Recipe> recipeList) {
		// Implement the saving of recipes to a text file based on your data structure
		// This method should iterate over the recipes and write the necessary information to the file
		// Customize the implementation to fit your specific data structure and requirements
		// Example code:
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/Data/Recipes.txt"))) {
			for (int i = 0; i < recipeList.numNodes(); i++) {
				Recipe recipe = recipeList.get(i);
				writer.write("Name: " + recipe.getName() + "\n");
				writer.write("Steps: " + recipe.getSteps() + "\n");

				writer.write("Ingredients: ");
				LinkedList<Ingredients> recipeIngredients = recipe.getRecipeIngredients();
				for (int j = 0; j < recipeIngredients.numNodes(); j++) {
					Ingredients ingredient = recipeIngredients.get(j);
					writer.write(ingredient.getIngName() + ", ");
				}
				writer.write("\n");

				writer.write("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadDataFromTextFiles(LinkedList<BakedGoods> bakedGoodsLinkedList,
	                                   LinkedList<Ingredients> ingredientsLinkedList,
	                                   LinkedList<Recipe> recipeList) {
		loadBakedGoodsFromTextFile(bakedGoodsLinkedList);
		loadIngredientsFromTextFile(ingredientsLinkedList);
		loadRecipesFromTextFile(recipeList, ingredientsLinkedList);
		System.out.println("Data loaded from text files successfully.");
	}

	private void loadBakedGoodsFromTextFile(LinkedList<BakedGoods> bakedGoodsLinkedList) {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/Data/BakedGoods.txt"))) {
			String line;
			BakedGoods bakedGood = null;

			while ((line = reader.readLine()) != null) {
				if (line.startsWith("Name: ")) {
					String name = line.substring(6);
					bakedGood = new BakedGoods(name, "", "", "");
				} else if (line.startsWith("Origin: ")) {
					String origin = line.substring(8);
					if (bakedGood != null) {
						bakedGood.setOriginCountry(origin);
					}
				} else if (line.startsWith("Description: ")) {
					String description = line.substring(13);
					if (bakedGood != null) {
						bakedGood.setGoodsDesc(description);
					}
				} else if (line.startsWith("URL: ")) {
					String url = line.substring(5);
					if (bakedGood != null) {
						bakedGood.setURL(url);
						bakedGoodsLinkedList.add(bakedGood);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadIngredientsFromTextFile(LinkedList<Ingredients> ingredientsLinkedList) {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/Data/Ingredients.txt"))) {
			String line;
			Ingredients ingredient = null;

			while ((line = reader.readLine()) != null) {
				if (line.startsWith("Name: ")) {
					String name = line.substring(6);
					ingredient = new Ingredients(name, "", 0);
				} else if (line.startsWith("Description: ")) {
					String description = line.substring(13);
					if (ingredient != null) {
						ingredient.setIngDesc(description);
					}
				} else if (line.startsWith("Calories: ")) {
					int calories = Integer.parseInt(line.substring(10));
					if (ingredient != null) {
						ingredient.setCalories(calories);
						ingredientsLinkedList.add(ingredient);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadRecipesFromTextFile(LinkedList<Recipe> recipeList, LinkedList<Ingredients> ingredientsLinkedList) {
		// Implement the loading of recipes from the text file into the recipeList based on your data structure
		// This method should read the necessary information from the file and create Recipe objects
		// Customize the implementation to fit your specific data structure and requirements
		// Example code:
		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/Data/Recipes.txt"))) {
			String line;
			Recipe recipe = null;

			while ((line = reader.readLine()) != null) {
				if (line.startsWith("Name: ")) {
					String name = line.substring(6);
					recipe = new Recipe(name, "", new LinkedList<>());
				} else if (line.startsWith("Steps: ")) {
					String steps = line.substring(8);
					if (recipe != null) {
						recipe.setSteps(steps);
					}
				} else if (line.startsWith("Ingredients: ")) {
					String ingredientsLine = line.substring(14);
					if (recipe != null) {
						String[] ingredientNames = ingredientsLine.split(", ");
						for (String ingredientName : ingredientNames) {
							Ingredients ingredient = findIngredientByName(ingredientName.trim(), ingredientsLinkedList);
							if (ingredient != null) {
								recipe.getRecipeIngredients().add(ingredient);
							}
						}
						recipeList.add(recipe);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Ingredients findIngredientByName(String name, LinkedList<Ingredients> ingredientsLinkedList) {
		for (int i = 0; i < ingredientsLinkedList.numNodes(); i++) {
			Ingredients ingredient = ingredientsLinkedList.get(i);
			if (ingredient.getIngName().equals(name)) {
				return ingredient;
			}
		}
		return null;
	}
}
