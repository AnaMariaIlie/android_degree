package com.journaldev.barcodevisionapi.models;


import java.util.ArrayList;
import java.util.List;

public class Drug {

    private String id;

    private String name;

    private List<Ingredient> ingredients;

    public Drug(String name) {
        this.name = name;
        this.ingredients = new ArrayList<>();
    }

    public Drug() {
        this.ingredients = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Drug{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
