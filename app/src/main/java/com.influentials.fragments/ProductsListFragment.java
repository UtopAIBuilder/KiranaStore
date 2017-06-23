package com.influentials.fragments;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.influentials.kiranastore.MainActivity;
import com.influentials.kiranastore.R;
import com.influentials.listadapters.ProductListAdapter;

public class ProductsListFragment extends Fragment {
   private ListView listview;
    MainActivity mainActivity;
    private String productCategory;
    private ProductListAdapter adapter;
    private String [] mProductNameList;
    private String[] mItemPrice;
    private  String[] mItemWeight;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        mProductNameList = getResources().getStringArray(R.array.product_list_items);
        mItemPrice = getResources().getStringArray(R.array.item_prices);
        mItemWeight=getResources().getStringArray(R.array.item_weights);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView= (ViewGroup) inflater.inflate(R.layout.productlist,container,false);
        listview= (ListView) rootView.findViewById(R.id.products_listview);
        productCategory=mainActivity.getProducts_category();
        adapter=new ProductListAdapter(mainActivity,mProductNameList,mItemPrice,mItemWeight);

        ListView lv=listview;
        listview.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return rootView;
    }
    private boolean isProductListRetrieved=false;

    public boolean isProductListRetrieved() {
        return isProductListRetrieved;
    }
    public void setProductListRetrieved(boolean isProductListRetrieved){
        this.isProductListRetrieved=isProductListRetrieved;
    }

}
