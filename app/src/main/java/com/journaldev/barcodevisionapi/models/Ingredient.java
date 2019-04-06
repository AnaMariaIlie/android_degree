package com.journaldev.barcodevisionapi.models;


import java.util.ArrayList;
import java.util.List;

public class Ingredient {

    private String id;

    private String name;

    private List<Interaction> interactions;

    public Ingredient(String name) {
        this.name = name;
        this.interactions = new ArrayList<>();
    }

    public Ingredient() {
        this.interactions = new ArrayList<>();
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

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }
}
