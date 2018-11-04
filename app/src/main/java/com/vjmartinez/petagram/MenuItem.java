package com.vjmartinez.petagram;

/**
 * Represents a menu item in main menu
 */
public class MenuItem {

    private String description;
    private int iconSource;
    private Class openActivityClass;

    public MenuItem(String description, int iconSource, Class openActivityClass){
        this.description = description;
        this.iconSource = iconSource;
        this.openActivityClass = openActivityClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIconSource() {
        return iconSource;
    }

    public void setIconSource(int iconSource) {
        this.iconSource = iconSource;
    }

    public Class getOpenActivityClass() {
        return openActivityClass;
    }

    public void setOpenActivityClass(Class openActivityClass) {
        this.openActivityClass = openActivityClass;
    }
}
