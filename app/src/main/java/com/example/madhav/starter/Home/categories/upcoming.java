package com.example.madhav.starter.Home.categories;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.madhav.starter.R;
import com.example.madhav.starter.controller.VolleySingleton;
import com.example.madhav.starter.model.adapter_upcoming;
import com.example.madhav.starter.model.upcomingItem;
import com.example.madhav.starter.network.mAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class upcoming extends Fragment {


    public upcoming() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<upcomingItem> upcomingItems;
  //  private TextView pen_count;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upcoming, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

       // pen_count = v.findViewById(R.id.pen_count);

        upcomingItems = new ArrayList<>();
        /*for(int i =0;i<=10;i++)
        {
            upcomingItem ui = new upcomingItem(
                    "heading" + (i+1),
                    "dummy text"

            );
            upcomingItems.add(ui);
        }

        adapter = new adapter_upcoming(upcomingItems,getActivity());

        recyclerView.setAdapter(adapter);*/


        loadRecyclerViewData();

        return v;

    }
   /* private void loadRecyclerViewData()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading data...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                mAPI.USER_URL ,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("student");
                            for(int i=0;i<array.length();i++){

                                JSONObject student = array.getJSONObject(i);
                                JSONObject jo = student.getJSONObject("Student_details");

                                    upcomingItem ui = new upcomingItem(
                                            //array1.getString("email"),
                                            jo.getString("email"),
                                            jo.getString("username")
                                    );
                                    upcomingItems.add(ui);
                            }
                            adapter = new adapter_upcoming(upcomingItems,getActivity());

                            recyclerView.setAdapter(adapter);



                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        );
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }*/

    private void loadRecyclerViewData()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading data ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                mAPI.PENDING_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("COMPLETE_DETAILS");
                            for(int i=0;i<array.length();i++){

                                JSONObject student = array.getJSONObject(i);
                               // JSONObject jo = student.getJSONObject("Student_details");

                                upcomingItem ui = new upcomingItem(
                                        //array1.getString("email"),
                                        student.getString("title"),
                                        student.getString("description"),
                                        student.getString("email"),
                                        student.getString("domain"),
                                        student.getString("category"),
                                        student.getString("upload_time"),
                                        student.getString("git_proj_link")

                                );
                                upcomingItems.add(ui);
                            }
                            adapter = new adapter_upcoming(upcomingItems,getActivity());
                          //  pen_count.setText("Pending Projects : "+array.length());
                            recyclerView.setAdapter(adapter);



                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        );
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }



}
