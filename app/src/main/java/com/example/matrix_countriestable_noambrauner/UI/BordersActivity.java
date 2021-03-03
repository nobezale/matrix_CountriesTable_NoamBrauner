package com.example.matrix_countriestable_noambrauner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.matrix_countriestable_noambrauner.DataEngine.UploadDataFromRestCountries;
import com.example.matrix_countriestable_noambrauner.Entity.Country;

import java.util.ArrayList;

public class BordersActivity extends AppCompatActivity {
TextView nameCountryTV;
ArrayList<String> arrDetails;
ListView listView;
ArrayList<Country>listCountriesBorder=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borders);
        nameCountryTV=findViewById(R.id.name_tv);
        listView=findViewById(R.id.mylistBorders);
        //getting the borders from mainActivity
        Bundle bundle=getIntent().getExtras();
        arrDetails=new ArrayList<>(bundle.getStringArrayList("myDetails"));
        nameCountryTV.setText(arrDetails.get(0));//get the name of the country we clicked on

        for(int i=1;i<arrDetails.size();i++)
       {
           Country country = MainActivity.getCountryByAlpha3Code(arrDetails.get(i));
           listCountriesBorder.add(country);
       }
        CountryListAdapter adapter=new CountryListAdapter(this,R.layout.adapter_list_item,listCountriesBorder);
        listView.setAdapter(adapter);
    }
}