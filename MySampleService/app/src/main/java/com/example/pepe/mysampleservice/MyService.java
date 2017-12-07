package com.example.pepe.mysampleservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by Pepe on 5/17/2016.
 */
public class MyService extends Service {

    @Override
    public IBinder onBind(Intent inputIntent){
        return null;
    }

    @Override
    public int onStartCommand(Intent inputIntent, int flags, int startId) {
        Toast.makeText(this,"Servicio Iniciado",Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this,"Servicio Destruido",Toast.LENGTH_LONG).show();
    }
}
