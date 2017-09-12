package net.cleonet.cleo.photofeed_galileo.domain.di;

import android.content.Context;
import android.location.Geocoder;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.cleonet.cleo.photofeed_galileo.domain.FirebaseAPI;
import net.cleonet.cleo.photofeed_galileo.domain.Util;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Pepe on 9/8/17.
 */

@Module
public class DomainModule {
    String firebaseURL;

    public DomainModule(String firebaseURL) {
        this.firebaseURL = firebaseURL;
    }

    @Provides
    @Singleton
//    FirebaseAPI providesFirebaseAPI(Firebase firebase){
    FirebaseAPI providesFirebaseAPI(){
        //return new FirebaseAPI(firebase);
        return new FirebaseAPI();
    }

//    @Provides
//    @Singleton
//    Firebase providesFirebase(String firebaseURL){
//    DatabaseReference providesFirebase(String firebaseURL){
//        return new FirebaseDatabase();
//    }

    @Provides
    @Singleton
    String providesFirebaseURL(){
        return this.firebaseURL;
    }

    @Provides
    @Singleton
    Util providesUtil(Geocoder geocoder){
        return new Util(geocoder);
    }

    @Provides
    @Singleton
    Geocoder providesGeocoder(Context context){
        return new Geocoder(context);
    }
}
