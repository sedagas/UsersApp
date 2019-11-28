package com.example.usersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserDetails extends AppCompatActivity {


    private final String USER_URL = "https://reqres.in/api/users/";
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvId;
    private TextView tvCurrentIndex;
    private ImageView ivAvatar;
    private ImageView ivBack;
    private ImageView ivNext;
    private int id;
    private int userCount;


    private RequestQueue queue;
    private RequestOptions option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        this.getSupportActionBar().setTitle("User Details");

        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        id  = getIntent().getExtras().getInt("user_id");
        userCount  = getIntent().getExtras().getInt("user_count");

        tvName = findViewById(R.id.tvNameSurname);
        tvId = findViewById(R.id.tvId);
        tvEmail = findViewById(R.id.tvEmail);
        ivAvatar = findViewById(R.id.ivAvatar);
        ivBack = findViewById(R.id.ivBack);
        ivNext = findViewById(R.id.ivNext);
        tvCurrentIndex = findViewById(R.id.tvCurrentIndex);

        getData(id);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(id <= 1){
                    id = 1;
                }else {
                    id = id - 1;
                }
                getData(id);
            }
        });


        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(id >= userCount){
                    id= userCount;
                }else{
                    id = id + 1;
                }
                getData(id);
            }
        });

    }

    public void getData(final int id){
        queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest objReq = new JsonObjectRequest(Request.Method.GET, USER_URL +"" + id, null,
                new Response.Listener<JSONObject>() {
                @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response.getJSONObject("data");
                            int id = obj.getInt("id");
                            String name = obj.getString("first_name");
                            String surname = obj.getString("last_name");
                            String email = obj.getString("email");
                            String avatar = obj.getString("avatar");

                            update(id,name,surname,email,avatar);

                        } catch (JSONException e) {
                             e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                }
            });

            queue.add(objReq);

    }

    public void update(int id, String name, String surname, String email, String avatar){

        tvName.setText(name + " " + surname);
        tvId.setText(id + "");
        tvEmail.setText(email);
        tvCurrentIndex.setText(id + "");

        Glide.with(getApplicationContext()).load(avatar).apply(option).into(ivAvatar);

    }
}
