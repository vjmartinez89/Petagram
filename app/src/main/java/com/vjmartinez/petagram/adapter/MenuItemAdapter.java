package com.vjmartinez.petagram.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vjmartinez.petagram.dto.MenuItem;
import com.vjmartinez.petagram.utils.MessageUtil;
import com.vjmartinez.petagram.PetagramActivity;
import com.vjmartinez.petagram.R;

import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder>{

    private List<MenuItem> menuItems;
    private PetagramActivity activity;

    /**
     * Default constructor
     * @param menuItems the list of menu items
     */
    public MenuItemAdapter(List<MenuItem> menuItems, PetagramActivity activity){
        this.menuItems = menuItems;
        this.activity = activity;
    }

    /* Content class of RecyclerView */
    static class MenuItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemIcon;
        private TextView itemDescription;

        MenuItemViewHolder(View itemView) {
            super(itemView);
            //Initialize the components
            itemIcon = itemView.findViewById(R.id.ic_menu_item);
            itemDescription = itemView.findViewById(R.id.tvi_menu_item);
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
                    activity.go(menuItem.getOpenActivityClass(), null, true);
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
     * @return The total of items on menu
     */
    @Override
    public int getItemCount() { //Total list item
        return menuItems.size();
    }
}
