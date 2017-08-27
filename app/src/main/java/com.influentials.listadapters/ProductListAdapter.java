package com.influentials.listadapters;

import android.content.Context;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.influentials.database.MyCartDbAdapter;
import com.influentials.kiranastore.MainActivity;
import com.influentials.kiranastore.R;

/**
 * Created by pesin on 6/10/17.
 */

public class ProductListAdapter extends BaseAdapter {
  private   Context context;
    private String []  ProductListnames;
    private String []  ItemPrice;
    private String []  ItemWeight;

    private int cartCounter=0;


    private TextView quantityTextView;
    private TextView productNameTextView;
    private TextView priceListTextView;
    private TextView weightListTextview;
    private int sizeOfListView;
    private int classId;



    public ProductListAdapter(Context context
            ,int classId, String [] ProductListNames, String [] ItemPrice, String[] ItemWeight,int sizeOfListView)
    {
        this.context=context;
        this.ItemPrice=ItemPrice;
        this.ItemWeight=ItemWeight;
        this.ProductListnames=ProductListNames;
        this.sizeOfListView=sizeOfListView;
        this.classId=classId;

    }

    @Override
    public int getCount() {
        return sizeOfListView;
    }

    @Override
    public Object getItem(int position) {
        return ProductListnames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        final LayoutInflater inflater;

        if (convertView==null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row= inflater.inflate(R.layout.list_row,parent,false);
            convertView=row;

        }
        else
        {
            row=convertView;
        }

        productNameTextView= (TextView) row.findViewById(R.id.from_name);
        priceListTextView= (TextView) row.findViewById(R.id.price_tag);
        weightListTextview= (TextView) row.findViewById(R.id.weight);
        ImageView plusCounter= (ImageView) row.findViewById(R.id.plus);
        ImageView minusCounter= (ImageView) row.findViewById(R.id.minus);
        quantityTextView= (TextView) row.findViewById(R.id.quantity);
        productNameTextView.setText(ProductListnames[position]);
        priceListTextView.setText(ItemPrice[position]);
        weightListTextview.setText(ItemWeight[position]);
        MyCartDbAdapter dbAdapter=new MyCartDbAdapter(context);
        quantityTextView.setText(""+dbAdapter.getData(ProductListnames[position]));





        plusCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfItem;

                ViewGroup parent= (ViewGroup) v.getParent();
                ViewGroup superParent= (ViewGroup) parent.getParent();

                 quantityTextView= (TextView) parent.findViewById(R.id.quantity);
                productNameTextView=(TextView) superParent.findViewById(R.id.from_name);
                priceListTextView= (TextView) parent.findViewById(R.id.price_tag);
                weightListTextview= (TextView) parent.findViewById(R.id.weight);

                String priceList=priceListTextView.getText().toString();
                String weightList=weightListTextview.getText().toString();
                numberOfItem = Integer.parseInt(quantityTextView.getText().toString());
                String productName=productNameTextView.getText().toString();
                if (numberOfItem <10)
                {
                    numberOfItem+=1;
                    SharedPreferences sharedPreferences =context.getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    cartCounter=sharedPreferences.getInt("CartCounter",cartCounter);
                    cartCounter+=1;
                    Log.d("Bz","cartcounter"+cartCounter);
                    editor.putInt("CartCounter", cartCounter);
                    editor.apply();

                    if (classId==0)
                    { ((MainActivity)context).setmMenu(cartCounter);}



                    MyCartDbAdapter myCartDbAdapter=new MyCartDbAdapter(context);
                    int updatedData=0;
                    if (numberOfItem>1)
                    {
                        updatedData=myCartDbAdapter.updateData(productName,numberOfItem);
                    }

                    Log.d("Bz","succesfullyUpdated: "+updatedData);


                    if (updatedData==0)
                    {
                        long insertData= myCartDbAdapter.insertData(productName,weightList,priceList,numberOfItem,R.drawable.ic_basket);
                        Log.d("Bz","succesfullyInserted: "+insertData);

                    }
                  //  Log.d("Bz",myCartDbAdapter.getAllData());

                    //MenuItem itemCart = menu.findItem(R.id.action_cart);
                    //LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
                    //setBadgeCount(context, icon,Integer.toString(cartCounter));


                }
                quantityTextView.setText(""+numberOfItem);


                //  row.refreshDrawableState();
                // Do things Here

            }
        });
        minusCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfItem;

                ViewGroup parent= (ViewGroup) view.getParent();
                ViewGroup superParent= (ViewGroup) parent.getParent();
                quantityTextView= (TextView) parent.findViewById(R.id.quantity);
                productNameTextView=(TextView) superParent.findViewById(R.id.from_name);
                priceListTextView= (TextView) parent.findViewById(R.id.price_tag);
                weightListTextview= (TextView) parent.findViewById(R.id.weight);


                String productName=productNameTextView.getText().toString();

                numberOfItem = Integer.parseInt(quantityTextView.getText().toString());
                if (numberOfItem >0)
                {
                    numberOfItem-=1;
                    SharedPreferences sharedPreferences =context.getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    cartCounter=sharedPreferences.getInt("CartCounter",cartCounter);
                    cartCounter-=1;
                    Log.d("Bz","cartcounter"+cartCounter);
                    editor.putInt("CartCounter", cartCounter);
                    editor.apply();
                    if (classId==0)
                    { ((MainActivity)context).setmMenu(cartCounter);}
                    MyCartDbAdapter myCartDbAdapter=new MyCartDbAdapter(context);
                    if (numberOfItem==0)
                    {
                        int deletedData=0;
                        deletedData=myCartDbAdapter.deleteData(productName);
                        Log.d("Bz","deleted rows: "+deletedData);
                       //
                    }
                    else
                    {
                        int updatedData=0;

                        updatedData=myCartDbAdapter.updateData(productName,numberOfItem);
                        Log.d("Bz","updated: "+updatedData);
                      //  Log.d("Bz",myCartDbAdapter.getAllData());


                    }







                }


                quantityTextView.setText(""+numberOfItem);



            }
        });




        return row;
    }





}
