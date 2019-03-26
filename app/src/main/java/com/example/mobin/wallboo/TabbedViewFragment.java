package com.example.mobin.wallboo;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabbedViewFragment extends Fragment {


    public TabbedViewFragment() {
        // Required empty public constructor
    }

    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tabbed_view, container, false);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this.getContext()));

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                this.getChildFragmentManager(), FragmentPagerItems.with(this.getActivity())
                .add(R.string.titleA, DemoFragment.class)
                .add(R.string.titleB, DemoFragment.class)
                .add(R.string.titleC, DemoFragment.class)
                .add(R.string.titleD, DemoFragment.class)
                .create());

        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = view.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

        return view;
    }

}
