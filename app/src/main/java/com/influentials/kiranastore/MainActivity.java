package com.influentials.kiranastore;


import android.content.res.Configuration;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.influentials.fragments.HomePageListFragment;
import com.influentials.fragments.ProductsListFragment;
import com.influentials.listadapters.NavDrawerListAdapter;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;


    private String [] navDrawerTitles;
    private String products_category;
    private CharSequence mdrawerTitle;
    private CharSequence mTitle;
    private NavDrawerListAdapter adapter;
    private ActionBarDrawerToggle drawerListener;
    private int [] navDrawerIcons={R.drawable.ic_home,R.drawable.ic_basket,R.drawable.ic_basket};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mdrawerTitle = getTitle();
        navDrawerTitles=getResources().getStringArray(R.array.nav_drawer_items);

        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList= (ListView) findViewById(R.id.drawer_list);

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerTitles,navDrawerIcons);
        mDrawerList.setAdapter(adapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Kirana Store");
        getSupportActionBar().setIcon(R.drawable.ic_home);
        drawerListener = new ActionBarDrawerToggle(this, mDrawerLayout,
                 // nav menu toggle icon
                R.string.drawer_open, // nav drawer open - description for
                // accessibility
                R.string.drawer_close // nav drawer close - description for
                // accessibility
        )
        {
            @Override
            public void onDrawerClosed(View drawerView) {
               //getSupportActionBar().setTitle(R.string.app_name);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mdrawerTitle);

            }
        };
        mDrawerLayout.addDrawerListener(drawerListener);
        if (savedInstanceState==null)
        {
            displayView(0);
        }
    }


    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {


            mDrawerList.setItemChecked(position,true);
            setTitle(navDrawerTitles[position]);
            // display view for selected nav drawer item
           displayView(position);
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerListener.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void displayView(int position)
    {
       Fragment fragment=null;
        switch (position){
            case 0:
           fragment=new HomePageListFragment();
                break;
            case 1:
             fragment=new ProductsListFragment();
                break;
            case 2:
               fragment=new ProductsListFragment();
                break;
            default:
                    break;
        }
        if (fragment!=null)
        {
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.main_content,fragment,null);
            transaction.commitAllowingStateLoss();
            mDrawerList.setItemChecked(position,true);
            mDrawerLayout.closeDrawer(mDrawerList);
            setTitle(navDrawerTitles[position]);
        }
        else
        {
            Log.d("Bz","error in retrieving fragment in main due to:");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle=title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }
     public String getProducts_category()
    {
       return products_category;
    }
     public void setProduct_category(String products_category)
    {
        this.products_category=products_category;
    }
}
