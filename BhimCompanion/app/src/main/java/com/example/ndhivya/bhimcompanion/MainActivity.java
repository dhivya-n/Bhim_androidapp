package com.example.ndhivya.bhimcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView username = (TextView) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    JSONObject body = new JSONObject();
                    body.put("username", username.getText().toString());
                    body.put("password", password.getText().toString());
                    //    Toast.makeText(LoginActivity.this, body.toString(), Toast.LENGTH_SHORT).show();
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://techwhiz.karma34.hasura-app.io/weblogin",body, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            if(response.optBoolean("status")==false)
                            {
                                Toast.makeText(MainActivity.this,"Authentication failed!!Invalid Details", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Bundle b=new Bundle();
                                try {
                                    // Toast.makeText(LoginActivity.this, response.getString("jwt"), Toast.LENGTH_SHORT).show();
                                    b.putString("jwt", response.getString("jwt"));
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }

                                Intent in=new Intent(getApplicationContext(),MainActivity.class);
                                in.putExtras(b);
                                startActivity(in);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley Error!",error.toString());
                        }
                    });
                    queue.add(jsonObjectRequest);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
