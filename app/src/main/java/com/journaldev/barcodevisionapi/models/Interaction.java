package com.journaldev.barcodevisionapi.models;

public class Interaction {

    private String id;

    private String firstIngredientName;

    private String secondIngredientName;

    private String toxicityLevel;

    private String description;

    public Interaction(String firstIngredientName, String secondIngredientName, String toxicityLevel, String description) {
        this.firstIngredientName = firstIngredientName;
        this.secondIngredientName = secondIngredientName;
        this.toxicityLevel = toxicityLevel;
        this.description = description;
    }

    public Interaction(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstIngredientName() {
        return firstIngredientName;
    }

    public void setFirstIngredientName(String firstIngredientName) {
        this.firstIngredientName = firstIngredientName;
    }

    public String getSecondIngredientName() {
        return secondIngredientName;
    }

    public void setSecondIngredientName(String secondIngredientName) {
        this.secondIngredientName = secondIngredientName;
    }

    public String getToxicityLevel() {
        return toxicityLevel;
    }

    public void setToxicityLevel(String toxicityLevel) {
        this.toxicityLevel = toxicityLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
