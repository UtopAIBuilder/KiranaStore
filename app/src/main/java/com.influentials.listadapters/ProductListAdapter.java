package com.influentials.listadapters;

import android.content.Context;

import android.view.LayoutInflater;

import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
    private Menu menu;
    private int cartCounter=0;


    private TextView quantity;



    public ProductListAdapter(Menu menu,Context context, String [] ProductListNames, String [] ItemPrice, String[] ItemWeight)
    {
        this.context=context;
        this.ItemPrice=ItemPrice;
        this.ItemWeight=ItemWeight;
        this.ProductListnames=ProductListNames;
        this.menu=menu;
    }

    @Override
    public int getCount() {
        return ProductListnames.length;
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

        TextView productName= (TextView) row.findViewById(R.id.from_name);
        TextView priceList= (TextView) row.findViewById(R.id.price_tag);
        TextView weightList= (TextView) row.findViewById(R.id.weight);
        ImageView plusCounter= (ImageView) row.findViewById(R.id.plus);
        ImageView minusCounter= (ImageView) row.findViewById(R.id.minus);
        quantity= (TextView) row.findViewById(R.id.quantity);



        plusCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfItem;

                ViewGroup parent= (ViewGroup) v.getParent();


                 quantity= (TextView) parent.findViewById(R.id.quantity);


                numberOfItem = Integer.parseInt(quantity.getText().toString());
                if (numberOfItem <10)
                {
                    numberOfItem+=1;
                    cartCounter+=1;
                    ((MainActivity)context).setmMenu(cartCounter);

                    //MenuItem itemCart = menu.findItem(R.id.action_cart);
                    //LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
                    //setBadgeCount(context, icon,Integer.toString(cartCounter));


                }
                quantity.setText(""+numberOfItem);


                //  row.refreshDrawableState();
                // Do things Here

            }
        });
        minusCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfItem;

                ViewGroup parent= (ViewGroup) view.getParent();
                quantity= (TextView) parent.findViewById(R.id.quantity);

                numberOfItem = Integer.parseInt(quantity.getText().toString());
                if (numberOfItem >0)
                {
                    numberOfItem-=1;
                    cartCounter-=1;
                    ((MainActivity)context).setmMenu(cartCounter);





                }
                quantity.setText(""+numberOfItem);



            }
        });


        productName.setText(ProductListnames[position]);
        priceList.setText(ItemPrice[position]);
        weightList.setText(ItemWeight[position]);

        return row;
    }





}
