package com.example.mobin.wallboo;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DemoFragment extends Fragment {


    public DemoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_demo, container, false);

        RecyclerView list = view.findViewById(R.id.list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 3);

        gridLayoutManager.generateLayoutParams(new GridLayoutManager.LayoutParams(30, 30));

        list.setLayoutManager(gridLayoutManager);

        ImageAdapter imageAdapter = new ImageAdapter(new ImageAdapter.Listener() {
            @Override
            public void onImageClicked(View view) {
                transition(view);
            }
        });

        list.setAdapter(imageAdapter);

        return view;
    }

    private void transition(View view) {

        if (Build.VERSION.SDK_INT < 21) {

            Toast.makeText(this.getContext(), "21+ only, keep out", Toast.LENGTH_SHORT).show();

        } else {

            Intent intent = new Intent(this.getContext(), DemoFragment.class);

            ActivityOptionsCompat options = ActivityOptionsCompat.

                    makeSceneTransitionAnimation(this.getActivity(), view, getString(R.string.app_name));

//            startActivity(intent, options.toBundle());

        }

    }

}
