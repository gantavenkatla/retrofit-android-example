package com.example.retrofitjavaexample.service;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.retrofitjavaexample.model.DataItem;

import java.io.IOException;

import retrofit2.Call;


public class CommonService extends IntentService {

    public static final String MY_SERVICE_MESSAGE="myServiceMessage";
    public static final String MY_SERVICE_PAYLOAD="myServicePayload";

    public static final String TAG="CommonService";

    public CommonService() {
        super("CommonService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


//
        MyWebSevice myWebSevice = MyWebSevice.retrofit.create(MyWebSevice.class);

        Call<DataItem[]> call = myWebSevice.dataItems();

        DataItem[] dataItems;
        try {
            dataItems = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Intent messageIntent = new Intent((MY_SERVICE_MESSAGE));
        messageIntent.putExtra(MY_SERVICE_PAYLOAD, dataItems);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());

        localBroadcastManager.sendBroadcast(messageIntent);

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
