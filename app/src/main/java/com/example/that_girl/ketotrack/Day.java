package com.example.that_girl.ketotrack;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by that_girl on 12/2/2015.
 */
public class Day implements Serializable{

    final Date date;
    Date endDate;
    LinkedList<FoodItem> foods;
    double carbs,protein,fat;
    boolean notCurrentDay;

    public Day() {
        this.date = new Date();
        this.foods = new LinkedList<FoodItem>();
        this.carbs=0;
        this.fat=0;
        this.protein=0;
        this.notCurrentDay=false;
    }

    public Date getDate() {
        return date;
    }

    public void addFood(FoodItem theFood){
        foods.add(theFood);
        this.carbs+=theFood.getCarbs();
        this.protein+=theFood.getProtein();
        this.fat+=theFood.getFat();
    }
    public void setEndDate(Date d){
        endDate = d;
    }
    public int getSize(){
        return foods.size();
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public FoodItem getFood(int index){
        return foods.get(index);
    }

    public LinkedList<FoodItem> getFoodList(){
        return foods;
    }

    public void deleteFood(int index){
        this.carbs-=foods.get(index).getCarbs();
        this.protein-=foods.get(index).getProtein();
        this.fat-=foods.get(index).getFat();
        foods.remove(index);
    }

    public void setNotCurrentDay(){
        notCurrentDay=true;
    }

    @Override
    public String toString() {
        if(notCurrentDay) {
            return "Totals for: " +
                    date + "\n\n" +
                    "Carbs: " + carbs +
                    "\nProtein: " + protein +
                    "\nFat " + fat +
                    "\n\nData for day stopped on " + endDate;
        }
        else{
            return "Totals for: " +
                    date + "\n\n" +
                    "Carbs: " + carbs +
                    "\nProtein: " + protein +
                    "\nFat " + fat;
        }
    }
}
