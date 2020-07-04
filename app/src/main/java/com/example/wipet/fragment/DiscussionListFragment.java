package com.example.wipet.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wipet.R;
import com.example.wipet.activity.CreateDiscussionActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DiscussionListFragment extends Fragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_discussion_list, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab_discusfrag);
        fab.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), CreateDiscussionActivity.class));
            Toast.makeText(getContext(),"kuy",Toast.LENGTH_SHORT).show();
        });
        return view;
    }
}