package com.influentials.kiranastore;


import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.widget.AdapterView;
import android.widget.ListView;


import com.influentials.fragments.HomePageCategoryListFragment;
import com.influentials.fragments.HomePageListFragment;
import com.influentials.fragments.ProductsListFragment;
import com.influentials.listadapters.NavDrawerListAdapter;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
   // private ListView mDrawerList;
    private String [] navDrawerTitles;
    private Boolean loginFlag=false;
    private String products_category;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private CharSequence mdrawerTitle;
    private CharSequence mTitle;
    private NavDrawerListAdapter adapter;
    private ActionBarDrawerToggle drawerListener;
    private int [] navDrawerIcons={R.drawable.ic_home,R.drawable.ic_basket,R.drawable.ic_basket};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  mTitle = mdrawerTitle = getTitle();
        //navDrawerTitles=getResources().getStringArray(R.array.nav_drawer_items);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView= (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       // handleIntent(getIntent());



       // mDrawerList= (ListView) findViewById(R.id.drawer_list);

       // mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        mDrawerLayout.setStatusBarBackground(Color.TRANSPARENT);

        // setting the nav drawer list adapter
      //  adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerTitles,navDrawerIcons);
      //  mDrawerList.setAdapter(adapter);
        drawerListener = new ActionBarDrawerToggle(this, mDrawerLayout,toolbar,
                 // nav menu toggle icon
                R.string.drawer_open, // nav drawer open - description for
                // accessibility
                R.string.drawer_close // nav drawer close - description for
                // accessibility
        )
        {
            @Override
            public void onDrawerClosed(View drawerView) {
               super.onDrawerClosed(drawerView);
                //getSupportActionBar().setTitle(R.string.app_name);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        mDrawerLayout.addDrawerListener(drawerListener);
        if (savedInstanceState==null)
        {
            displayView(0);
        }
    }


  /*  private class SlideMenuClickListener implements
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
*/



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerListener.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayView(int position)
    {
       Fragment fragment=null;
        Intent intent;
        switch (position){
            case 0:
                fragment=new HomePageCategoryListFragment();
                break;
            case 1:
                intent= new Intent(this,LoginActivity.class);
                startActivity(intent);
               // fragment=new Loginfragment();
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
           // mDrawerList.setItemChecked(position,true);
          // mDrawerLayout.closeDrawer(GravityCompat.START);
          //  setTitle(navDrawerTitles[position]);
        }
        else
        {
            Log.d("Bz","error in retrieving fragment in main due to:");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle=title;
       // getSupportActionBar().setTitle(mTitle);
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.isChecked())item.setChecked(false);
        else item.setChecked(true);
        if (item.getItemId()==R.id.welcome)
        {
            item.setChecked(false);
        }
        mDrawerLayout.closeDrawers();





            switch (item.getItemId()) {
                case R.id.home:
                    displayView(0);
                   break;
                case R.id.login:
                    displayView(1);
                    break;
                case R.id.my_addresses:
                    if (!loginFlag)
                    {
                        displayView(1);
                    }
                    break;
                case R.id.my_orders:
                    if (!loginFlag)
                    {
                        displayView(1);
                    }
                    break;
                case R.id.my_cart:
                    if (!loginFlag)
                    {
                        displayView(1);
                    }
                    break;

                default:
                    break;
            }





        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
       /* SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
*/

        return true;
    }
  /*  private void handleIntent(Intent intent)
    {
        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query=intent.getStringExtra(SearchManager.QUERY);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }
    */
}
