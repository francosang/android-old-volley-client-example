package com.jfranco.volleyexample.activity;

import android.content.Intent;
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
import com.jfranco.volleyexample.adapter.AlbumsAdapter;
import com.jfranco.volleyexample.dto.Album;
import com.jfranco.volleyexample.util.GsonUtils;
import com.jfranco.volleyexample.util.URLs;

import java.util.List;

public class AlbumsActivity extends AppCompatActivity {

    private static final String REQUEST_TAG_ALBUMS = "albums";

    private RequestQueue requestQueue;
    private AlbumsAdapter albumsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        albumsAdapter = new AlbumsAdapter();
        albumsAdapter.setOnAlbumSelectedListener(new AlbumsAdapter.OnAlbumSelectedListener() {
            @Override
            public void onClick(Album album) {
                selectAlbum(album);
            }
        });

        albumsAdapter.setOnDetailListener(new AlbumsAdapter.OnAlbumDetailListener() {
            @Override
            public void onDetail(Album album) {
                selectUser(album);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.activity_albums_recview_albums);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(albumsAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        makeRequest(URLs.API_ALBUMS_URL);
    }

    private void selectUser(Album album) {
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra(UserActivity.USER_EXTRA_KEY, album.getUserId());

        startActivity(intent);
    }

    private void selectAlbum(Album album) {
        Intent intent = new Intent(this, PhotosActivity.class);
        intent.putExtra(PhotosActivity.ALBUM_EXTRA_KEY, album);

        startActivity(intent);
    }

    private void makeRequest(String url) {
        StringRequest request = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESP", response);

                        parseAndUpdateAlbumsAdapter(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        request.setTag(REQUEST_TAG_ALBUMS);

        requestQueue.add(request);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (requestQueue != null) {
            requestQueue.cancelAll(REQUEST_TAG_ALBUMS);
        }
    }

    private void parseAndUpdateAlbumsAdapter(String response) {
        List<Album> albums = GsonUtils.parseList(response, Album[].class);
        updateAlbumsAdapter(albums);
    }

    private void updateAlbumsAdapter(List<Album> albumns) {
        albumsAdapter.setAlbums(albumns);
        albumsAdapter.notifyDataSetChanged();
    }
}
