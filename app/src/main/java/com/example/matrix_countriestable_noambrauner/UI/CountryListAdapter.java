package com.example.matrix_countriestable_noambrauner.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matrix_countriestable_noambrauner.Entity.Country;

import java.util.List;

public class CountryListAdapter extends ArrayAdapter<Country> {
    Context mContext;
    int mResource;
    public CountryListAdapter(@NonNull Context context, int resource, @NonNull List<Country> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        String name=getItem(position).getName();
        String nativeName=getItem(position).getNativeName();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView nameT=(TextView)convertView.findViewById(R.id.name_textview);
        TextView nativeNameT=(TextView)convertView.findViewById(R.id.nativeName_textview);

        nameT.setText(name);
        nativeNameT.setText(nativeName);
        return convertView;

    }
}
