package com.influentials.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class MyCartDbAdapter{

    MyCartDbHelper helper;

    MyCartDbAdapter(Context context)
    {
         helper=new MyCartDbHelper(context);
    }

    public long insertData(String productName,String productWeight,int productQuantity,int imageId)
    {
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(MyCartDbHelper.PRODUCT_NAME,productName);
        contentValues.put(MyCartDbHelper.PRODUCT_WEIGHT,productWeight);
        contentValues.put(MyCartDbHelper.PRODUCT_QUANTITY,productQuantity);
        contentValues.put(MyCartDbHelper.PRODUCT_IMAGE_ID,imageId);
        long id=db.insert(MyCartDbHelper.TABLE_NAME,null,contentValues);
        return id;

    }



       static class MyCartDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME="MyCartDb";
        private static final String TABLE_NAME="MyCartTable";
        private static final int DATABASE_VERSION=1;
        private static final String PRODUCT_NAME="Product Name";
        private static final String PRODUCT_WEIGHT="Product Weight";
        private static final String PRODUCT_QUANTITY="Product Quantity";
        private static final String UID="_id";
        private static final String PRODUCT_IMAGE_ID="Image Id";
        private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME
                +"("+UID+ "INTEGER PRIMARY KEY AUTOINCREMENT,"+PRODUCT_NAME+
                "VARCHAR(255),"+PRODUCT_WEIGHT+ "VARCHAR(255),"+PRODUCT_QUANTITY+ "INTEGER,"
                +PRODUCT_IMAGE_ID+ "INTEGER);";
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
