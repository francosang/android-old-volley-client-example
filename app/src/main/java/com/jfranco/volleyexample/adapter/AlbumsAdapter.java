package com.jfranco.volleyexample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jfranco.volleyexample.R;
import com.jfranco.volleyexample.dto.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sistemas on 29/10/2018.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder> {

    private List<Album> albums;
    private OnAlbumSelectedListener onAlbumSelectedListener;
    private OnAlbumDetailListener onDetailListener;

    public AlbumsAdapter() {
        this.albums = new ArrayList<>();
    }

    public AlbumsAdapter(List<Album> albums) {
        this.albums = albums;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View elementoTitular = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_album, parent, false);

        return new AlbumViewHolder(elementoTitular);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder view, int pos) {
        view.bind(albums.get(pos));
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public void setOnAlbumSelectedListener(OnAlbumSelectedListener onAlbumSelectedListener) {
        this.onAlbumSelectedListener = onAlbumSelectedListener;
    }

    public void setOnDetailListener(OnAlbumDetailListener onDetailListener) {
        this.onDetailListener = onDetailListener;
    }

    public interface OnAlbumSelectedListener {
        void onClick(Album album);
    }

    public interface OnAlbumDetailListener {
        void onDetail(Album album);
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {

        private TextView titulo;
        private Button detalle;

        public AlbumViewHolder(View item) {
            super(item);

            titulo = item.findViewById(R.id.item_titular_tv_titulo);
            detalle = item.findViewById(R.id.item_titular_bt_detalle);
        }

        public void bind(final Album album) {
            titulo.setText(album.getTitle());
            detalle.setText("Usuario " + album.getUserId());
            detalle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDetailListener != null) {
                        onDetailListener.onDetail(album);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onAlbumSelectedListener != null) {
                        onAlbumSelectedListener.onClick(album);
                    }
                }
            });
        }
    }
}
