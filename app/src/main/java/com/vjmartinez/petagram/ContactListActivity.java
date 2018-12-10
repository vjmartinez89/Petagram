package com.vjmartinez.petagram;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.vjmartinez.petagram.adapter.PageAdapter;
import com.vjmartinez.petagram.fragments.ProfileFragment;
import com.vjmartinez.petagram.fragments.RecyclerViewFragment;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends PetagramActivity {

    //Global variables
  //  private Toolbar actionBar = null;
    private Toolbar toolBar = null;
    private TabLayout tabLayout = null;
    private ViewPager viewPager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        init();
    }

    @Override
    public void initComponents() {
        super.initComponents();
     //   actionBar =  findViewById(R.id.mainAcionBar);
        toolBar =  findViewById(R.id.toolBar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        if(toolBar != null){
            setSupportActionBar(toolBar);
        }
        //Set support for previous action bar button
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setUpViewPager();
    }

    @Override
    public void initAdapters() {
        super.initAdapters();
    }

    @Override
    public void initEvents() {
        super.initEvents();
        //Intercepts the click event on arrow back button in action bar
        if(toolBar!=null) {
            toolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBack();
                }
            });
        }
    }

    /**
     * Go to Main Activity when user touch back button
     * @param keyCode, The key code
     * @param event, Yhe event information
     * @return boolean
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            goBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Go to previous activity
     */
    private void goBack() {
        go(MainActivity.class, null, true);
    }

    /**
     * Configure
     */
    private void setUpViewPager(){
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),getFragmentsList()));
        tabLayout.setupWithViewPager(viewPager);
        if( tabLayout.getTabAt(0) != null ) {
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_action_name);
        }
        if( tabLayout.getTabAt(1)!= null ) {
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_phone_24dp);
        }
    }

    /**
     * Create and return the list of fragments to show
     * @return The list of fragments
     */
    private List<Fragment> getFragmentsList(){
        List<Fragment> fragments = new ArrayList<>();
        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
        recyclerViewFragment.setPetagramActivity(this);
        fragments.add(recyclerViewFragment);
        fragments.add(new ProfileFragment());
        return fragments;
    }

}
