package com.example.that_girl.ketotrack;

/**
 * Created by that_girl on 11/10/2015.
 */
import java.io.Serializable;

abstract class KetoData implements Serializable{

    private String name;
    private double fat,protein,carbs;

    public KetoData(){
        name="";
        fat=0.0;
        protein=0.0;
        carbs=0.0;
    }
    public KetoData(String l, double ft, double p, double c){
        name=l;
        fat=ft;
        protein=p;
        carbs=c;
    }

    public double getFat() {
        return fat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }
    @Override
    public String toString() {
        return name + "\n\n" + "Macros: \n" +
                "Fat: " + fat + " g\n" +
                "Protein: " + protein + " g\n" +
                "Carbs: " + carbs + " g\n";
    }
}
