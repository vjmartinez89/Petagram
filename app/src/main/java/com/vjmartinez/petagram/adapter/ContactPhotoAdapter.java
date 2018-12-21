package com.vjmartinez.petagram.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vjmartinez.petagram.PetagramActivity;
import com.vjmartinez.petagram.R;
import com.vjmartinez.petagram.dto.ContactPhoto;

import java.util.List;

public class ContactPhotoAdapter extends RecyclerView.Adapter<ContactPhotoAdapter
        .ContactPhotoViewHolder> {


    private List<ContactPhoto> photos;
    private PetagramActivity activity;

    /**
     * Default constructor
     * @param photos The photo list
     */
    public ContactPhotoAdapter(List<ContactPhoto> photos, PetagramActivity activity){
        this.photos = photos;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ContactPhotoAdapter.ContactPhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                         int viewType) {
        //Inflate card view layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_contact_photo,
                parent, false);
        return new ContactPhotoAdapter.ContactPhotoViewHolder(v); //Pass layout to view holder
    }

    @Override
    public void onBindViewHolder(@NonNull ContactPhotoAdapter.ContactPhotoViewHolder
                                             contactPhotoViewHolder, int position) {
        final ContactPhoto contactPhoto = photos.get(position);
        contactPhotoViewHolder.ivCvPhotoImg.setImageResource(contactPhoto.getResourceId());
        contactPhotoViewHolder.tviCvPhotoLikes.setText(String.valueOf(contactPhoto.getLikes()));
    }

    @Override
    public int getItemCount() {
        return photos != null ? photos.size() : 0;
    }


    // Content class of RecyclerView
    static class ContactPhotoViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivCvPhotoImg;
        private TextView tviCvPhotoLikes;
        ContactPhotoViewHolder(View itemView){
            super(itemView);
            ivCvPhotoImg = itemView.findViewById(R.id.iv_cv_photo_img);
            tviCvPhotoLikes = itemView.findViewById(R.id.tvi_cv_photo_likes);
        }

    }
}
