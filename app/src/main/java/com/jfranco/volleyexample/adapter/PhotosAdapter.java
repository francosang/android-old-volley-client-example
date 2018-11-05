package com.jfranco.volleyexample.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jfranco.volleyexample.R;
import com.jfranco.volleyexample.dto.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sistemas on 29/10/2018.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {

    private List<Photo> photos;
    private OnPhotoSelectedListener onPhotoSelectedListener;

    public PhotosAdapter() {
        this.photos = new ArrayList<>();
    }

    public PhotosAdapter(List<Photo> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View elementoTitular = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);

        return new PhotoViewHolder(elementoTitular);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder view, int pos) {
        view.bind(photos.get(pos));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public void setOnPhotoSelectedListener(OnPhotoSelectedListener onPhotoSelectedListener) {
        this.onPhotoSelectedListener = onPhotoSelectedListener;
    }

    public interface OnPhotoSelectedListener {
        void onClick(Photo photo);
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        private TextView titulo;
        private TextView album;
        private ImageView thumb;

        public PhotoViewHolder(View item) {
            super(item);

            titulo = item.findViewById(R.id.item_photo_tv_titulo);
            album = item.findViewById(R.id.item_photo_tv_album);
            thumb = item.findViewById(R.id.item_photo_iv_thumb);
        }

        public void bind(final Photo photo) {
            titulo.setText(photo.getTitle());
            album.setText("Album #" + photo.getAlbumId());

            Picasso.get().load(photo.getThumbnailUrl()).into(thumb);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onPhotoSelectedListener != null) {
                        onPhotoSelectedListener.onClick(photo);
                    }
                }
            });
        }
    }
}
