package com.example.mobin.wallboo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.jaeger.library.StatusBarUtil;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout drawerLayout;

    private Boolean checkFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);

        checkFragment = false;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("clickporse", false);
        editor.commit();


        drawerLayout = findViewById(R.id.drawer_layout);
//        startService(new Intent(getApplicationContext(), LiveWallpaperService.class));

        StatusBarUtil.setColorForDrawerLayout(this, drawerLayout, getResources().getColor(R.color.primary));


        /// tool bar add r sheita action bar er feature kisu support kora
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        Fragment fr = new TabbedViewFragment();
        Fragment frVideo = new VideoListFragment();

        openFragment(fr, true);


        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        Fragment fragment = null;
                        FragmentTransaction fragmentTransaction = null;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_camera:
                                if (checkFragment == false) break;

                                onBackPressed();
                                checkFragment = false;

                                //Fragment tabbedFr = new TabbedViewFragment();
                                //openFragment(tabbedFr, true);

                                break;
                            case R.id.nav_gallery:

                                if (checkFragment)
                                    break;
                                checkFragment = true;

                                Fragment frVideo = new VideoListFragment();
                                openFragment(frVideo, false);

                                break;
                        }
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });


    }


    private void openFragment(final Fragment fragment, boolean noBack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framer, fragment);
        if (noBack) {
            transaction.disallowAddToBackStack();
        } else {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        checkFragment = false;
        super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    /// Title bar inflate kore search create kore
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");


        /// Typing in the search view

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            /// Type shesh e Enter marle
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(getApplicationContext(),query, Toast.LENGTH_LONG).show();
                return false;
            }

            /// Every character likhle
            @Override
            public boolean onQueryTextChange(String newText) {
                //     mAdapter.getFilter().filter(newText);
                Toast.makeText(getApplicationContext(), newText, Toast.LENGTH_LONG).show();
                return true;
            }

        });

        return super.onCreateOptionsMenu(menu);
    }

}
