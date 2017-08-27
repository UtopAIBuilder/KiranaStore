package com.influentials.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class MyCartDbAdapter{

    MyCartDbHelper helper;

    public MyCartDbAdapter(Context context)
    {
         helper=new MyCartDbHelper(context);
    }

    public long insertData(String productName,String productWeight,String priceTag,int productQuantity,int imageId)
    {
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(MyCartDbHelper.PRODUCT_NAME,productName);
        contentValues.put(MyCartDbHelper.PRODUCT_WEIGHT,productWeight);
        contentValues.put(MyCartDbHelper.PRODUCT_QUANTITY,productQuantity);
        contentValues.put(MyCartDbHelper.PRICE_TAG,priceTag);
        contentValues.put(MyCartDbHelper.PRODUCT_IMAGE_ID,imageId);
        long id=db.insert(MyCartDbHelper.TABLE_NAME,null,contentValues);
        return id;

    }

    public int updateData(String productName,int productQuantity)
    {
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(MyCartDbHelper.PRODUCT_QUANTITY,productQuantity);
        String[] whereArgs={productName};
       int count= db.update(MyCartDbHelper.TABLE_NAME,contentValues,MyCartDbHelper.PRODUCT_NAME+"=?",whereArgs);

        return count;
    }

    public int deleteData(String productName)
    {
        SQLiteDatabase db=helper.getWritableDatabase();
        String [] whereArgs={productName};
        int count=db.delete(MyCartDbHelper.TABLE_NAME,MyCartDbHelper.PRODUCT_NAME+"=?",whereArgs);
        return count;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db=helper.getWritableDatabase();
        String[] columns={MyCartDbHelper.UID,MyCartDbHelper.PRODUCT_NAME,MyCartDbHelper.PRICE_TAG,
                MyCartDbHelper.PRODUCT_QUANTITY,MyCartDbHelper.PRODUCT_WEIGHT};
        Cursor cursor=db.query(MyCartDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        Cursor returningCursor=cursor;
        String tag;
        while (cursor.moveToNext())
        {
           int _id=cursor.getInt(0);
            String productName=cursor.getString(1);
            String priceTag=cursor.getString(2);
            int productQuantity=cursor.getInt(3);
            String productWeight=cursor.getString(4);
            tag=_id+" "+productName+" "+priceTag+" "+productQuantity+" "+productWeight+"\n ";
            Log.d("Bz",tag);
        }


        return cursor;

    }

    public int getData(String productName)
    {
        SQLiteDatabase db=helper.getWritableDatabase();
        String[] columns={MyCartDbHelper.PRODUCT_QUANTITY};
        int productQuantity=0;
        String [] whereArgs={productName};
        try
        {Cursor cursor=db.query(MyCartDbHelper.TABLE_NAME,columns,MyCartDbHelper.PRODUCT_NAME+"=?",whereArgs,null,null,null,null);

            while (cursor.moveToNext())
            {
                productQuantity=cursor.getInt(0);



            }
        }
        catch (Exception e)
        {
            Log.d("Bz","SQLEXCEPtion"+e);
        }



        return productQuantity;
    }




       static class MyCartDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME="MyCartDb";
        private static final String TABLE_NAME="MyCartTable";
        private static final int DATABASE_VERSION=1;
        private static final String PRODUCT_NAME="ProductName";
        private static final String PRODUCT_WEIGHT="ProductWeight";
        private static final String PRODUCT_QUANTITY="ProductQuantity";
        private static final String UID="_id";
        private static final String PRICE_TAG="PriceTag";
        private static final String PRODUCT_IMAGE_ID="ImageId";
        private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME
                +"("+UID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+PRODUCT_NAME+
                " VARCHAR(255),"+PRODUCT_WEIGHT+ " VARCHAR(255),"+PRICE_TAG+ " VARCHAR(255),"+PRODUCT_QUANTITY+ " INTEGER,"
                +PRODUCT_IMAGE_ID+ " INTEGER);";
        private static final String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;

        private  Context context;

        MyCartDbHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
            this.context=context;
        }
        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
                Log.d("Bz","successfully created table");
            }
            catch (SQLException e)
            {
                Log.d("Bz","SQLException: "+e);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }
            catch (SQLException e)
            {
                Log.d("Bz","SQLException: "+e);
            }

        }
    }

}
