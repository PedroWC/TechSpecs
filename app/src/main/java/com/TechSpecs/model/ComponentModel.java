package com.TechSpecs.model;

import java.io.Serializable;

public class ComponentModel implements Serializable {
    private final int imageResource;
    private final String name;
    private final String description;

    // Constructor
    public ComponentModel(int imageResource, String name, String description) {
        this.imageResource = imageResource;
        this.name = name;
        this.description = description;
    }

    // Getters
    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Setters (se necessário)
    // ...
}
