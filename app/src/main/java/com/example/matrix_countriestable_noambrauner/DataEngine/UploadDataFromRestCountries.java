package com.example.matrix_countriestable_noambrauner.DataEngine;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.matrix_countriestable_noambrauner.Entity.Country;
import com.example.matrix_countriestable_noambrauner.UI.CountryListAdapter;
import com.example.matrix_countriestable_noambrauner.UI.MainActivity;
import com.example.matrix_countriestable_noambrauner.UI.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class UploadDataFromRestCountries extends AsyncTask<Void, Void, Void>
{
    Context mContext;
    public UploadDataFromRestCountries(Context context)
    {
        mContext=context;
    }
     private ArrayList<Country> allCountries=new ArrayList<Country>();
     String allData="";
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.e("check the data ", allData);
        ListView listView=((Activity)mContext).findViewById(R.id.mylist);
        CountryListAdapter  adapter = new CountryListAdapter(mContext, R.layout.adapter_list_item, allCountries);
        listView.setAdapter(adapter);



    }


    private void getData() throws IOException, JSONException {
        //get all countries in json array format
        JSONArray arr=readJsonArrayFromUrl("https://restcountries.eu/rest/v2/all?fields=area;name;nativeName;borders;alpha3Code");


           for(int i=0;i<arr.length();i++)
           {
               try {
               JSONObject country=arr.getJSONObject(i);
               String name=country.getString("name");
               String nativeName=country.getString("nativeName");
                   String areaString="0.0";
                   if(country.has("area"))//if area is exist
                   {
                       Object areaObj=country.get("area");
                       areaString=areaObj.toString();
                   }

               String alpha3Code=country.getString("alpha3Code");
               JSONArray borders=country.getJSONArray("borders");
               String[] bordersArray=new String[borders.length()];
               for(int j=0;j<borders.length();j++)
               {
                   bordersArray[j]= (String)borders.get(j);
               }
               Country country1=new Country(name,nativeName,bordersArray,alpha3Code,areaString);
               allCountries.add(country1);



               } catch (JSONException e) {

                   e.printStackTrace();
               }
           }
            JSONObject country=arr.getJSONObject(0);
            String response = country.getString("nativeName");
            Object area=country.get("area");

            allData=area.toString();



    }
    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


    public JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray json = new JSONArray(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public ArrayList<Country> getArrayAllCountries()
    {
        return allCountries;
    }



}
