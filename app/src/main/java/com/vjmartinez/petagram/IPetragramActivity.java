package com.vjmartinez.petagram;

public interface IPetragramActivity {

    /**
     * Initialize view components of the activity
     */
    void initComponents();

    /**
     * Initialize the activity adapters
     */
    void initAdapters();

    /**
     * Initialize the activity events
     */
    void initEvents();
}
