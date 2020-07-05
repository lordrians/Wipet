package com.example.wipet.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wipet.Api;
import com.example.wipet.GlobalFunc;
import com.example.wipet.R;
import com.example.wipet.activity.CreateDiscussionActivity;
import com.example.wipet.adapter.DiscussionListAdapter;
import com.example.wipet.object.Discussion;
import com.example.wipet.object.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class DiscussionListFragment extends Fragment {
    private View view;
    private FloatingActionButton fab;
    private RecyclerView rvDiscussion;
    private ProgressDialog dialog;
    private ArrayList<Discussion> discussionArrayList = new ArrayList<>();
    private DiscussionListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_discussion_list, container, false);

        init();
        return view;
    }

    private void init() {
        fab = view.findViewById(R.id.fab_discusfrag);
        rvDiscussion = view.findViewById(R.id.rv_item_discvertical);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        setupRV();


        fab.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), CreateDiscussionActivity.class));
        });
    }

    private void setupRV() {
        rvDiscussion.setHasFixedSize(true);
        rvDiscussion.setLayoutManager(new LinearLayoutManager(getContext()));
        loadData();
    }

    private void loadData() {
        dialog.setMessage("Load discussion...");
        dialog.show();

        StringRequest request = new StringRequest(StringRequest.Method.POST, Api.SHOW_LIST_DISCUSSION,response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONArray discussionJsonArr = object.getJSONArray("discussion");
                    for (int i = 0; i < discussionJsonArr.length(); i++){
                        JSONObject discussionObj = discussionJsonArr.getJSONObject(i);
                        Discussion discussion = new Discussion();
                        discussion.setId(discussionObj.getInt("id"));
                        discussion.setTitle(discussionObj.getString("title"));
                        discussion.setCreated_at(discussionObj.getString("created_at"));
                        discussion.setCommentsCount(discussionObj.getInt("commentsCount"));
                        discussion.setLikesCount(discussionObj.getInt("likesCount"));

                        JSONArray photoArr = discussionObj.getJSONArray("photo");
                        if (photoArr.length() > 0){
                            ArrayList<String> photoArrayList = new ArrayList<>();
                            for (int j = 0; j < photoArr.length(); j++){
                                JSONObject photo = photoArr.getJSONObject(j);
                                photoArrayList.add(photo.getString("path_photo"));
                            }
                            discussion.setPhoto(photoArrayList);
                        }

                        JSONObject userObj = discussionObj.getJSONObject("user");
                        JSONObject kelurahanObj = userObj.getJSONObject("kelurahan");
                        JSONObject kecamatanObj = kelurahanObj.getJSONObject("kecamatan");
                        JSONObject kotaObj = kecamatanObj.getJSONObject("kota");
                        JSONObject provinsiObj = kotaObj.getJSONObject("provinsi");
                        User user = new User();
                        user.setName(userObj.getString("name"));
                        user.setKelurahan(kelurahanObj.getString("name"));
                        user.setKecamatan(kecamatanObj.getString("name"));
                        user.setKota(kotaObj.getString("name"));
                        user.setProvinsi(provinsiObj.getString("name"));

                        discussion.setUser(user);
                        discussionArrayList.add(discussion);



                    }
                    adapter = new DiscussionListAdapter(getContext(),discussionArrayList);
                    rvDiscussion.setAdapter(adapter);
                    Toast.makeText(getContext(),String.valueOf(discussionArrayList.size())+"C", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(),e.getMessage()+"A", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        },error -> {
            error.printStackTrace();
            Toast.makeText(getContext(),error.getMessage()+"B", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return GlobalFunc.getHeaders(getContext());
            }
        };
        Volley.newRequestQueue(getContext()).add(request);

    }
}