package com.example.samikhan.saylani_final_project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.samikhan.saylani_final_project.Model.Post_Req_Model;
import com.example.samikhan.saylani_final_project.R;

import java.util.ArrayList;

/**
 * Created by Sami Khan on 2/26/2017.
 */

public class My_req_Adapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Post_Req_Model> postModelArrayList;
    private Context context;

    public My_req_Adapter() {
    }

    public My_req_Adapter(ArrayList<Post_Req_Model> postModelArrayList, Context context) {
        this.postModelArrayList = postModelArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return postModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return postModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.my_postlayout, parent, false);
        }
        Post_Req_Model postModel = postModelArrayList.get(position);
        TextView informatio  = (TextView) convertView.findViewById(R.id.info_instruction_mypost);
        TextView status = (TextView) convertView.findViewById(R.id.status);
        informatio.setText("Required "+postModel.getNo_of_unit()+" of "+postModel.getBlood_Group()+"  at "+postModel.getHospital());
        status.setText("Not fulfilled");
        notifyDataSetChanged();
        return convertView;
    }
}
