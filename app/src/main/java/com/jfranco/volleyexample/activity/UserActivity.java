package com.jfranco.volleyexample.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jfranco.volleyexample.R;
import com.jfranco.volleyexample.dto.User;
import com.jfranco.volleyexample.util.GsonUtils;
import com.jfranco.volleyexample.util.URLs;

public class UserActivity extends AppCompatActivity {

    public static final String USER_EXTRA_KEY = "user_id_extra";
    private static final String REQUEST_TAG_USER = "user-request";

    private Long userId;
    private RequestQueue requestQueue;
    private TextView userName;
    private TextView city;
    private TextView street;
    private TextView name;
    private TextView email;
    private TextView companyName;
    private TextView catchPhase;
    private TextView companyBs;
    private TextView zip;
    private TextView suite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        requestQueue = Volley.newRequestQueue(this);

        city = findViewById(R.id.activity_user_tv_address_city);
        street = findViewById(R.id.activity_user_tv_address_street);
        suite = findViewById(R.id.activity_user_tv_address_suite);
        zip = findViewById(R.id.activity_user_tv_address_zip);
        companyBs = findViewById(R.id.activity_user_tv_company_bs);
        catchPhase = findViewById(R.id.activity_user_tv_company_catch_phrase);
        companyName = findViewById(R.id.activity_user_tv_company_name);
        email = findViewById(R.id.activity_user_tv_email);
        name = findViewById(R.id.activity_user_tv_name);
        userName = findViewById(R.id.activity_user_tv_user);

        this.userId = getIntent().getLongExtra(USER_EXTRA_KEY, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String url = URLs.API_USER_URL.replace("{userId}", String.valueOf(userId));

        StringRequest request = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESP", response);

                        updateUser(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        request.setTag(REQUEST_TAG_USER);

        requestQueue.add(request);
    }

    private void updateUser(String response) {
        User user = GsonUtils.parse(response, User.class);

        setTitle(user.getName());

        userName.setText(user.getUsername());
        city.setText(user.getAddress().getCity());
        street.setText(user.getAddress().getStreet());
        name.setText(user.getName());
        email.setText(user.getEmail());
        companyName.setText(user.getCompany().getName());
        catchPhase.setText(user.getCompany().getCatchPhrase());
        companyBs.setText(user.getCompany().getBs());
        zip.setText(user.getAddress().getZipcode());
        suite.setText(user.getAddress().getSuite());
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (requestQueue != null) {
            requestQueue.cancelAll(REQUEST_TAG_USER);
        }
    }

}
