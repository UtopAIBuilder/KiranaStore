package com.influentials.kiranastore;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.influentials.database.MyCartDbAdapter;
import com.influentials.listadapters.ProductListAdapter;

public class MyCartActivity extends AppCompatActivity {

    ListView listview;
    private String[] ProductListnames;
    private String[] ItemPrice;
    private String[] ItemWeight;
    int cartCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        getSupportActionBar().setTitle("My Cart");
        listview = (ListView) findViewById(R.id.my_cart_list);
        SharedPreferences sharedPreferences = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        cartCounter = sharedPreferences.getInt("CartCounter", cartCounter);
        if (cartCounter > 0) {
            ProductListnames = new String[cartCounter];
            ItemPrice = new String[cartCounter];
            ItemWeight = new String[cartCounter];
        }
        MyCartDbAdapter dbAdapter = new MyCartDbAdapter(this);
        Cursor cursor = dbAdapter.getAllData();
        cursor.moveToFirst();
        int i = 0;
        Log.d("Bz", "waiting for cursor");
        do {
            Log.d("Bz", "while of cursor");
            ProductListnames[i] = cursor.getString(1);
            Log.d("Bz", "ProductListnames[" + i + "]: " + ProductListnames[i]);
            ItemPrice[i] = cursor.getString(2);
            Log.d("Bz", "ItemPrice" + i + "]: " + ItemPrice[i]);
            ItemWeight[i] = cursor.getString(4);
            Log.d("Bz", "ItemWeight" + i + "]: " + ItemWeight[i]);
            i++;

        } while (cursor.moveToNext());
        Log.d("Bz", "crossed cursor");
        ProductListAdapter adapter = new ProductListAdapter(this, 1, ProductListnames, ItemPrice, ItemWeight, i);
        listview.setAdapter(adapter);

    }

}
