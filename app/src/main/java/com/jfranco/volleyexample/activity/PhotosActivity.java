package com.jfranco.volleyexample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jfranco.volleyexample.R;
import com.jfranco.volleyexample.adapter.PhotosAdapter;
import com.jfranco.volleyexample.dto.Album;
import com.jfranco.volleyexample.dto.Photo;
import com.jfranco.volleyexample.util.GsonUtils;
import com.jfranco.volleyexample.util.URLs;

import java.util.List;

public class PhotosActivity extends AppCompatActivity {

    public static final String ALBUM_EXTRA_KEY = "album_extra";

    private static final String REQUEST_TAG_PHOTOS = "photos";

    private RequestQueue requestQueue;
    private PhotosAdapter photosAdapter;
    private Album album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        photosAdapter = new PhotosAdapter();
        photosAdapter.setOnPhotoSelectedListener(new PhotosAdapter.OnPhotoSelectedListener() {
            @Override
            public void onClick(Photo photo) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.activity_albums_recview_albums);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(photosAdapter);

        album = (Album) getIntent().getSerializableExtra(ALBUM_EXTRA_KEY);
        setTitle("Fotos de '" + album.getTitle() + "'");
    }

    @Override
    protected void onResume() {
        super.onResume();

        makePhotosRequest(album.getId());
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (requestQueue != null) {
            requestQueue.cancelAll(REQUEST_TAG_PHOTOS);
        }
    }

    private void makePhotosRequest(Long albumId) {

        String url = URLs.API_PHOTO_ALBUMS_URL.replace("{albumId}", String.valueOf(albumId));

        StringRequest request = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESP", response);

                        parseAndUpdateAdapter(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        request.setTag(REQUEST_TAG_PHOTOS);

        requestQueue.add(request);
    }

    private void parseAndUpdateAdapter(String response) {
        List<Photo> photos = GsonUtils.parseList(response, Photo[].class);
        updateAdapter(photos);
    }

    private void updateAdapter(List<Photo> photos) {
        photosAdapter.setPhotos(photos);
        photosAdapter.notifyDataSetChanged();
    }

}
