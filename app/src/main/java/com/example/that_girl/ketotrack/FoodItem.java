package com.example.that_girl.ketotrack;

import java.util.Comparator;

/**
 * Created by that_girl on 11/10/2015.
 */
public class FoodItem extends KetoData implements Comparable<FoodItem>{
    String description;

    public FoodItem() {
    }

    public FoodItem(String l, double ft, double p, double c) {
        super(l, ft, p, c);
        description="";
    }
    public FoodItem(String l, double ft, double p, double c,String d){
        super(l, ft, p, c);
        description=d;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString(){
        return getName() + "\n" + "Protein: " + getProtein()
                + "g  " + " Fat: " + getFat()+ "g  " + " Carbs: " + getCarbs() + "g  "
                + "\n" + getDescription();
    }
    public int compareTo(FoodItem f){
        int result = this.getName().compareTo(f.getName());
        return result;
    }
}
