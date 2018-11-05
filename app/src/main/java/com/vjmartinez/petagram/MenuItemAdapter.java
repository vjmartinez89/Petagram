package com.vjmartinez.petagram;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder>{

    private List<MenuItem> menuItems;
    private PetagramActivity activity;

    /**
     * Default constructor
     * @param menuItems
     */
    public MenuItemAdapter(List<MenuItem> menuItems, PetagramActivity activity){
        this.menuItems = menuItems;
        this.activity = activity;
    }

    /* Content class of RecyclerView */
    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemIcon;
        private TextView itemDescription;

        public MenuItemViewHolder(View itemView) {
            super(itemView);
            //Initialize the components
            itemIcon = (ImageView)itemView.findViewById(R.id.ic_menu_item);
            itemDescription = (TextView)itemView.findViewById(R.id.tvi_menu_item);
        }
    }

    //Fill the list contact object in position to view properties
    @Override
    public void onBindViewHolder(@NonNull MenuItemAdapter.MenuItemViewHolder menuItemViewHolder,
                                 int position) {
        final MenuItem menuItem = menuItems.get(position);
        menuItemViewHolder.itemIcon.setImageResource(menuItem.getIconSource());
        menuItemViewHolder.itemDescription.setText(menuItem.getDescription());

        //Onclick event on icon
        menuItemViewHolder.itemIcon.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if(menuItem.getOpenActivityClass() != null) {
                    Intent i = new Intent(activity, menuItem.getOpenActivityClass());
                    ObjectMapper objectMapper = new ObjectMapper();
                    activity.startActivity(i);
                    activity.finish(); //Finish Main Activity
                }else{
                    MessageUtil.showAlertDialog(activity, activity.getResources().getString(R.string.error),
                            "Destination activity is not found!");
                }
            }
        });
    }

    @NonNull
    @Override
    public MenuItemAdapter.MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate card view layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent,
                false);
        return new MenuItemAdapter.MenuItemViewHolder(v); //Pass layout to view holder
    }

    /**
     * Return item list count
     * @return
     */
    @Override
    public int getItemCount() { //Total list item
        return menuItems.size();
    }
}
