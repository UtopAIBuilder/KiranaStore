package com.influentials.kiranastore;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.NavigationView;

import android.widget.RelativeLayout;
import android.widget.TextView;


import com.influentials.fragments.HomePageCategoryListFragment;
import com.influentials.fragments.ProductsListFragment;

import java.lang.reflect.Field;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    int cartCounter;
    private boolean loginFlag=false;   // to check if user is logged in or not
    private String products_category;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    private BroadcastReceiver receiver;
    private IntentFilter filter;
    private boolean isConnected=false;
    private Bundle bundleSavedInstance;
    private int status;


    private ActionBarDrawerToggle drawerListener;
    private int [] navDrawerIcons={R.drawable.ic_home,R.drawable.ic_basket,R.drawable.ic_basket};
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Bz","in Oncreate");
        bundleSavedInstance=savedInstanceState;

        status = NetworkUtil.getConnectivityStatusString(this);

        if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {

                 Intent intent =new Intent(this,NoInternetConnection.class);
                startActivity(intent);

        } else {

              isConnected=true;
            garbageOfOnCreate(savedInstanceState);
            // new ResumeForceExitPause(context).execute();
        }




    }





    private void garbageOfOnCreate(Bundle savedInstanceState)
    {
        sharedPreferences = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
        boolean  firstTime=sharedPreferences.getBoolean("first", true);  // to remember if it's their first visit
        if(firstTime) {                                                    // after installation

            Intent intent = new Intent(MainActivity.this, LocationSearchActivity.class);
            startActivity(intent);
            finish();
        }

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Field titleField ;
        try {
            titleField =Toolbar.class.getDeclaredField("mTitleTextView");
            titleField.setAccessible(true);
            TextView barTitleView = (TextView) titleField.get(toolbar);
            barTitleView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.d("Bz","actionBarClicked succsessfully");
                    Intent intent=new Intent(MainActivity.this,LocationSearchActivity.class);
                    startActivity(intent);

                }
            });
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView= (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mDrawerLayout.setStatusBarBackground(Color.TRANSPARENT);

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


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

            garbageOfOnCreate(bundleSavedInstance);

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_cart)
        {
           Log.d("Bz","inside action cart click");
        }


        return super.onOptionsItemSelected(item);
    }

    public void displayView(int position)   // display different fragments in main activity
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
            if (position==0) {
                transaction.replace(R.id.main_content, fragment, null);
                transaction.commitAllowingStateLoss();
            }
            else
            {
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_content, fragment, null);
                transaction.commitAllowingStateLoss();
            }
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
      getSupportActionBar().setTitle(title);
        Log.d("Bz","set title has been called");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (drawerListener!=null)
        {drawerListener.syncState();}

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (drawerListener!=null)
        {drawerListener.onConfigurationChanged(newConfig);}
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
        mDrawerLayout.closeDrawers();
        if (item.getItemId()==R.id.welcome)
        {
            item.setChecked(false);
            mDrawerLayout.openDrawer(GravityCompat.START);
        }



        status = NetworkUtil.getConnectivityStatusString(this);

        if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {

            Intent intent =new Intent(this,NoInternetConnection.class);
            startActivity(intent);

        }
        else
            {
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
            }





        return true;
    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        SharedPreferences mSharedPreferences = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
        boolean  firstTime=mSharedPreferences.getBoolean("first", true);
        if (!firstTime)
        {
            String mTitle=mSharedPreferences.getString("ActionBarTitle",null);
            setTitle(mTitle);
        }

       MenuItem menuItem = menu.findItem(R.id.action_cart);
       MenuItemCompat.setActionView(menuItem, R.layout.badge_layout);

        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(menuItem);
        TextView tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        tv.setVisibility(View.INVISIBLE);
        Log.d("Bz","onCreateOptions has been called");
        return true;
    }



public void setmMenu(int cartCounter)
{
    Log.d("Bz","setmMenu has been called");
    this.cartCounter=cartCounter;
    invalidateOptionsMenu();


}

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        Log.d("Bz","onPrepareOptions has been called");

        MenuItem menuItem = menu.findItem(R.id.action_cart);
        MenuItemCompat.setActionView(menuItem, R.layout.badge_layout);

        final View menu_cartlist = MenuItemCompat.getActionView(menuItem);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(menuItem);
        TextView tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        if (cartCounter>0) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(Integer.toString(cartCounter));
        }
        else  tv.setVisibility(View.INVISIBLE);
        menu_cartlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onOptionsItemSelected(menu.findItem(R.id.action_cart));
            }
        });


        return super.onPrepareOptionsMenu(menu);
    }



}

