package com.TechSpecs.utils;


public class ModelListItem {
    private int iconResourceId;
    private String componentName;

    public ModelListItem(int iconResourceId, String componentName) {
        this.iconResourceId = iconResourceId;
        this.componentName = componentName;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public String getComponentName() {
        return componentName;
    }
}
