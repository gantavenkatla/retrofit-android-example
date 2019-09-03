package com.example.retrofitjavaexample;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import com.example.retrofitjavaexample.model.DataItem;
import com.example.retrofitjavaexample.service.CommonService;
import com.example.retrofitjavaexample.service.MyWebSevice;
import com.example.retrofitjavaexample.utils.NetWorkHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG="MainActivity";

    TextView output;

    public static final String JSON_ENDPOINT="http://560057.youcanlearnit.net/services/json/itemsfeed.php";

    List<DataItem> dataItems = new ArrayList<DataItem>();

    private  boolean networkOk;

    private BroadcastReceiver mbroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            DataItem[] dataItems =(DataItem[]) intent.getParcelableArrayExtra(CommonService.MY_SERVICE_PAYLOAD);

            for (DataItem dataItem :dataItems) {

                output.append("\n"+ dataItem.getItemName() + " : " + dataItem.getPrice() + " : "+ dataItem.getCategory());
            }


    }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        networkOk= NetWorkHelper.hasNetworkAccess(getApplicationContext());

        output = (TextView) findViewById(R.id.output);
        output.append((" \n Network Ok : " +  networkOk));

    }

    public void viewCustomValue(View view) {

        Intent intent = new Intent(this, CommonService.class);
        intent.setData(Uri.parse(JSON_ENDPOINT));
        startService(intent);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mbroadCastReceiver,new IntentFilter(CommonService.MY_SERVICE_MESSAGE));
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    public void clearScreen(View view)
    {
        output.setText("");
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    private void requestData(){
//        Intent intent = new Intent(this,CommonService.class);
//        startService(intent);
//    }
}
