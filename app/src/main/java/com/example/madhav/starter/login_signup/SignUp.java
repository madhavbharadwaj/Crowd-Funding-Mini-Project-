package com.example.madhav.starter.login_signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.madhav.starter.R;
import com.example.madhav.starter.controller.VolleySingleton;
import com.example.madhav.starter.model.mSignup;
import com.example.madhav.starter.network.mAPI;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private EditText su_email;
    private EditText su_usn;
    private EditText su_username;
    private EditText su_phone;
    private EditText su_glink;
    private Button signUP;
    private TextView input_status_su;
    private ProgressBar pdia_su;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        su_email = findViewById(R.id.input_email_su);
        su_usn = findViewById(R.id.input_usn_su);
        su_username = findViewById(R.id.input_username_su);
        su_phone = findViewById(R.id.input_phone_su);
        su_glink = findViewById(R.id.input_glink_su);
        signUP = findViewById(R.id.btn_signup);
        input_status_su = findViewById(R.id.su_status);
        pdia_su = findViewById(R.id.su_progB);
        pdia_su.setVisibility(View.GONE);

        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_status_su.setText("");
                if( TextUtils.isEmpty(su_email.getText()) || TextUtils.isEmpty(su_usn.getText()) || TextUtils.isEmpty(su_username.getText()) || TextUtils.isEmpty(su_phone.getText()) || TextUtils.isEmpty(su_glink.getText())) {
                    input_status_su.setText("Fields cannot be left blank");
                    input_status_su.setTextColor(Color.RED);
                }
                else
                if(CheckInternet())
                {


                    new LongOperationSignup().execute();
                }
                else
                {input_status_su.setTextColor(Color.RED);
                    input_status_su.setText("No Internet Connection");

                }

            }
        });



        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_signupS);

        setSupportActionBar(toolbar);


        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_arrow_back));
        getSupportActionBar().setTitle("Sign up");




        //back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  onBackPressed();
                Intent intent = new Intent(SignUp.this, RegLogActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(SignUp.this, RegLogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    private void register() {
        final mSignup msign = new mSignup(su_email.getText().toString(),su_usn.getText().toString(),su_phone.getText().toString(),su_glink.getText().toString(),su_username.getText().toString());

        StringRequest postRequest = new StringRequest(Request.Method.POST, mAPI.SIGNUP_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        su_email.setText("");
                        su_usn.setText("");
                        su_username.setText("");
                        su_phone.setText("");
                        su_glink.setText("");
                        //ins.requestFocus();
                        /*Toast.makeText(getApplicationContext(), "Account has been created successfully",
                                Toast.LENGTH_SHORT).show();
                        input_status_su.setTextColor(Color.GRAY);
                        input_status_su.setText("Check your Mail and Set your Password");*/
                        Toast.makeText(getApplicationContext(), "Check your Mail and Set your Password",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUp.this, LoginScreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                        //getDialog().dismiss();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                        input_status_su.setText("Email ID exists");
                        input_status_su.setTextColor(Color.RED);
                        pdia_su.setVisibility(View.GONE);
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", msign.getmSEmail());
                params.put("username", msign.getmSUsername());
                params.put("usn", msign.getmSUSN());
                params.put("mobile", msign.getmSMobile());
                params.put("github", msign.getmSGithub());
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(postRequest);
    }

    private class LongOperationSignup extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            register();
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }


            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            pdia_su.setVisibility(View.GONE);
        }

        @Override
        protected void onPreExecute() {

            pdia_su.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
    public boolean CheckInternet()
    {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

        return connected;
    }
}