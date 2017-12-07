package com.example.pepe.mysampleservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Pepe on 5/17/2016.
 */
public class MyReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context myContext, Intent myIntent){
        Toast.makeText(myContext,"Intent Detected.",Toast.LENGTH_LONG).show();
    }
}
