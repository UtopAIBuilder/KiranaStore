package com.influentials.kiranastore;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.influentials.kiranastore.R;

/**
 * Created by bazil on 13/8/17.
 */

public class NoInternetConnection extends AppCompatActivity{


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_internet_fragment);

    }

    public void callForFinish(View view)
    {
        int status = NetworkUtil.getConnectivityStatusString(this);

        if (status != NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {

            finish();

        }

    }

    @Override
    public void onBackPressed() {

    }
}
