package com.example.mobin.wallboo;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import im.ene.toro.widget.Container;
import im.ene.toro.widget.PressablePlayerSelector;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToroVideoListFragment extends Fragment {


    public ToroVideoListFragment() {
        // Required empty public constructor
    }

    Container container1;
    LinearLayoutManager layoutManager;
    BasicListAdapter adapter;

    PressablePlayerSelector selector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toro_video_list, container, false);

        container1 = view.findViewById(R.id.player_container);

        layoutManager = new LinearLayoutManager(view.getContext());
        container1.setLayoutManager(layoutManager);
        selector = new PressablePlayerSelector(container1);
        container1.setPlayerSelector(selector);

        adapter = new BasicListAdapter(selector, view.getContext());
        container1.setAdapter(adapter);

        return view;
    }


    @Override
    public void onDestroyView() {
        layoutManager = null;
        adapter = null;
        selector = null;
        super.onDestroyView();
    }
}
