package com.influentials.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.influentials.kiranastore.R;

/**
 * Created by pesin on 6/10/17.
 */

public class ProductListAdapter extends BaseAdapter {
  private   Context context;
    private String []  ProductListnames;
    private String []  ItemPrice;
    private String []  ItemWeight;

    public ProductListAdapter(Context context,String [] ProductListNames,String [] ItemPrice,String[] ItemWeight)
    {
        this.context=context;
        this.ItemPrice=ItemPrice;
        this.ItemWeight=ItemWeight;
        this.ProductListnames=ProductListNames;
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
        LayoutInflater inflater;

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
        TextView weightlist= (TextView) row.findViewById(R.id.weight);
        productName.setText(ProductListnames[position]);
        priceList.setText(ItemPrice[position]);
        weightlist.setText(ItemWeight[position]);

        return row;
    }
}
