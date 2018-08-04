package com.example.samikhan.saylani_final_project.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.samikhan.saylani_final_project.Adapters.ToBoAdapter;
import com.example.samikhan.saylani_final_project.Adapters.ToBoAdapter_2;
import com.example.samikhan.saylani_final_project.AppLogs;
import com.example.samikhan.saylani_final_project.Model.Commit_for;
import com.example.samikhan.saylani_final_project.Model.Post_Req_Model;
import com.example.samikhan.saylani_final_project.Model.Volunteer_model;
import com.example.samikhan.saylani_final_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Sami Khan on 2/26/2017.
 */

public class Post_Detail extends android.support.v4.app.Fragment {

    private TextView textView;
    private ListView listView;
    private ListView listView_voluteer;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference firebase;
    private ArrayList<Commit_for> commit_forArrayList;
    private Post_Req_Model model;
    private ToBoAdapter toBoAdapter;
    private ToBoAdapter_2 getToBoAdapter;
    private ArrayList<Volunteer_model> volunteer_modelArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.detail_view,null);
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        commit_forArrayList = new ArrayList<>();
        volunteer_modelArrayList = new ArrayList<>();
        toBoAdapter = new ToBoAdapter(commit_forArrayList,getActivity());
        getToBoAdapter = new ToBoAdapter_2(volunteer_modelArrayList,getActivity());
        textView = (TextView)view.findViewById(R.id.post_details) ;
        listView = (ListView)view.findViewById(R.id.comments_ListView);
        listView_voluteer = (ListView)view.findViewById(R.id.volunteers_ListView);
        Bundle bundle = getArguments();
        listView.setAdapter(toBoAdapter);
        listView_voluteer.setAdapter(getToBoAdapter);
        if (bundle.getSerializable("faiz") != null) {
            AppLogs.d("Faiz", "Comment " + bundle.get("faiz"));
             model = (Post_Req_Model) bundle.getSerializable("faiz");
            AppLogs.d("Faiz","model "+model.toString());
            textView.setText(model.getUser_name()+
                    "\nUnits Required: "+model.getNo_of_unit()+
                    "\nDonation Received "+0+
                    "\nStill required:"+0+
                    "\nBlood Group: "+model.getBlood_Group()+
                    "\nLocation: "+model.getCity()+","+model.getState()+","+model.getCountry()+
                    "\nHospital: "+model.getHospital()+
                    "\nUrgency: "+model.getUrgency()+
                    "\nRelation With Patient: "+model.getRelation()+
                    "\nContact no: "+model.getContact_no()+
                    "\nAdditional Instruction: "+model.getInstruction());

        }

        firebase.child("comments").child(model.getPost_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commit_forArrayList.clear();
                if(dataSnapshot!=null){
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        Commit_for commit_for = dataSnapshot1.getValue(Commit_for.class);
                        commit_forArrayList.add(commit_for);
                        toBoAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firebase.child("volunteer").child("donation").child(model.getPost_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                volunteer_modelArrayList.clear();
                if(dataSnapshot!=null){
               //     for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        Volunteer_model commit_for = dataSnapshot.getValue(Volunteer_model.class);
                        volunteer_modelArrayList.add(commit_for);
                        getToBoAdapter.notifyDataSetChanged();
              //      }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


       return view;
    }
}
