package Models;

import Resources.LinkedList;

public class Recipe {
    public LinkedList<Ingredients> recipeIngredients = new LinkedList<>();
    public String steps;
    private String Name;

    public Recipe(String name, String Steps, LinkedList<Ingredients> ingredients) {
        this.Name = name;
        this.steps = Steps;
        this.recipeIngredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public LinkedList<Ingredients> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(LinkedList<Ingredients> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeIngredients=" + recipeIngredients +
                ", Name='" + Name + "'\'" +
                '}';
    }
}
