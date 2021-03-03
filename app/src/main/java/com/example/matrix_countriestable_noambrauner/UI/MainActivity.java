package com.example.matrix_countriestable_noambrauner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matrix_countriestable_noambrauner.DataEngine.UploadDataFromRestCountries;
import com.example.matrix_countriestable_noambrauner.Entity.Country;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button buttonAscendingOrder;
    Button buttonAscendingByAreaOrder;
    Button buttonDescendingOrder;
    Button buttonDescendingByAreaOrder;
    static ArrayList<Country> arrAllCountries;
    ListView listView;
    CountryListAdapter adapter;
    UploadDataFromRestCountries loadData;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAscendingOrder=findViewById(R.id.ascending_order);
        buttonDescendingOrder=findViewById(R.id.descending_order);
        buttonAscendingByAreaOrder=findViewById(R.id.ascending_order_by_area);
        buttonDescendingByAreaOrder=findViewById(R.id.descending_order_by_area);
        listView=findViewById(R.id.mylist);
        //set the click listener
        buttonAscendingOrder.setOnClickListener(this);
        buttonDescendingOrder.setOnClickListener(this);
        buttonAscendingByAreaOrder.setOnClickListener(this);
        buttonDescendingByAreaOrder.setOnClickListener(this);

        //checking the internet connction
        ConnectivityManager cm = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();

        if(!connected)
            Toast.makeText(this, "internet is not connected, please connect!", Toast.LENGTH_LONG).show();
        else
            {
            loadData = new UploadDataFromRestCountries(this);
            loadData.execute();
            arrAllCountries = loadData.getArrayAllCountries();


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Country selectedItem = (Country) parent.getItemAtPosition(position);
                    Log.e("we click on  ", selectedItem.getName());
                    Intent intent = new Intent(getApplication(), BordersActivity.class);
                    Bundle bundle = new Bundle();
                    ArrayList<String> arr = new ArrayList<>();
                    arr.add(selectedItem.getName());
                    String[] borders = selectedItem.getBorders();
                    for (int i = 0; i < borders.length; i++)
                        arr.add(borders[i]);
                    bundle.putStringArrayList("myDetails", arr);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });


        }

    }

    public static Country getCountryByAlpha3Code(String alphaCode)
    {
        for(Country country:arrAllCountries)
        {
            if(country.getAlpha3Code().equals(alphaCode))
                return country;
        }
        return new Country();
    }


    @Override
    public void onClick(View v)
    {
        ArrayList<Country> countriesOrderedByArea=new ArrayList<>();
        for(int i=0;i<arrAllCountries.size();i++)
            countriesOrderedByArea.add(arrAllCountries.get(i));
       switch(v.getId())
       {
           case R.id.ascending_order:

               adapter=new CountryListAdapter(this,R.layout.adapter_list_item,arrAllCountries);
              listView.setAdapter(adapter);
               break;

           case R.id.descending_order:

               ArrayList<Country> arr=new ArrayList<>();
               for(int i=arrAllCountries.size()-1;i>=0;i--)//reverse the arraylist which sorted by name
                    arr.add(arrAllCountries.get(i));
               adapter=new CountryListAdapter(this,R.layout.adapter_list_item,arr);
               listView.setAdapter(adapter);
               break;

           case R.id.ascending_order_by_area:

               Collections.sort(countriesOrderedByArea); //sorted by the area
               adapter=new CountryListAdapter(this,R.layout.adapter_list_item,countriesOrderedByArea);
               listView.setAdapter(adapter);
               break;

           case R.id.descending_order_by_area:

               Collections.sort(countriesOrderedByArea); //sorted by the area
               ArrayList<Country> arr2=new ArrayList<>();
               for(int i=countriesOrderedByArea.size()-1;i>=0;i--)//reverse the arraylist which sorted by the area
                   arr2.add(countriesOrderedByArea.get(i));
               adapter=new CountryListAdapter(this,R.layout.adapter_list_item,arr2);
               listView.setAdapter(adapter);

               break;
           default:
               break;
       }
    }
}