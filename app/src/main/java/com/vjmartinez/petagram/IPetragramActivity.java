package com.vjmartinez.petagram;

public interface IPetragramActivity {

    /**
     * Initialize view components of the activity
     */
    public void initComponents();

    /**
     * Initialize the activity adapters
     */
    public void initAdapters();

    /**
     * Initialize the activity events
     */
    public void initEvents();
}
