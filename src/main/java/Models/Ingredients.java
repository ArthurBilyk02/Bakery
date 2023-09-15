package Models;

public class Ingredients {
    private String ingName;
    private String ingDesc;
    private int calories;

    public Ingredients(String ingName, String ingDesc, int calories) {
        this.ingName = ingName;
        this.ingDesc = ingDesc;
        this.calories = calories;
    }


    public String getIngName() {
        return ingName;
    }

    public void setIngName(String ingName) {
        this.ingName = ingName;
    }

    public String getIngDesc() {
        return ingDesc;
    }

    public void setIngDesc(String ingDesc) {
        this.ingDesc = ingDesc;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }


    @Override
    public String toString() {
        return ingName + ", " + ingDesc + ' ' +
                ", " + calories;
    }
}

