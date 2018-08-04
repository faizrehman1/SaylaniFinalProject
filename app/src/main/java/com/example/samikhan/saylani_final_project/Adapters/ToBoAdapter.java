package com.example.samikhan.saylani_final_project.Adapters;

/**
 * Created by MT-2016 on 2/26/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.samikhan.saylani_final_project.Model.Commit_for;
import com.example.samikhan.saylani_final_project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by babar on 1/29/2017.
 */

//public class ToBoAdapter {
//}

public class ToBoAdapter extends BaseAdapter {
    private List<Commit_for> dataList;
    private Context context;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public ToBoAdapter(List<Commit_for> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.single_commit,null);

        TextView foename = (TextView) view.findViewById(R.id.tre_name);
        TextView foenamep = (TextView) view.findViewById(R.id.tre_com);



        final  Commit_for data = dataList.get(position);

        String nam = data.getComit_nmae();
        String namp = data.getCommit_text();



        //to still the condition after changes
        final Commit_for todoChekd = (Commit_for) getItem(position);



        foename.setText(nam);
        foenamep.setText(namp);


        return view;
    }
}