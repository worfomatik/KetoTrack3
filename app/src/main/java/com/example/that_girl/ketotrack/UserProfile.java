package com.example.that_girl.ketotrack;

/**
 * Created by that_girl on 10/30/2015.
 */

public class UserProfile extends KetoData{

    //TODO: add profile picture
    private String fName,lName;

    public UserProfile() {
    }

    public UserProfile(String f, String l, double ft, double p, double c) {
        super("", ft, p, c);
        setfName(f);
        setlName(l);
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String toString() {
        return "Profile" + "\n\n"+
                fName + ' ' +
                lName + "\n\n" + "Macros: \n" +
                "Fat: " + getFat() + " g\n" +
                "Protein: " + getProtein() + " g\n" +
                "Carbs: " + getCarbs() + " g\n";
    }
}
