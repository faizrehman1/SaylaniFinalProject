package com.example.samikhan.saylani_final_project.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.samikhan.saylani_final_project.Adapters.CardAdapter;
import com.example.samikhan.saylani_final_project.Model.Post_Req_Model;
import com.example.samikhan.saylani_final_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Sami Khan on 2/26/2017.
 */

public class Feed_fragment extends android.support.v4.app.Fragment {

    private CardAdapter cardArrayAdapter;
    private ListView listView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference firebase;
    private ArrayList<Post_Req_Model> arrayList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.my_req_view,null);
        listView = (ListView)view.findViewById(R.id.req_list);
        listView.addHeaderView(new View(getActivity()));
        listView.addFooterView(new View(getActivity()));
        mAuth = FirebaseAuth.getInstance();
        arrayList = new ArrayList<>();
        firebase = FirebaseDatabase.getInstance().getReference();
        cardArrayAdapter = new CardAdapter(getActivity(),R.layout.list_item_card);
        listView.setAdapter(cardArrayAdapter);


//        firebase.child("users_feed").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                cardArrayAdapter.clear();
//                if (dataSnapshot != null) {
//                    for (DataSnapshot data : dataSnapshot.getChildren()) {
//                        Post_Req_Model dataa = data.getValue(Post_Req_Model.class);
//                        cardArrayAdapter.add(dataa);
//                        cardArrayAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        firebase.child("users_feed").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                cardArrayAdapter.clear();
                if (dataSnapshot != null) {
           //         for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Post_Req_Model dataa = dataSnapshot.getValue(Post_Req_Model.class);
                        cardArrayAdapter.add(dataa);
                    arrayList.add(dataa);
                        cardArrayAdapter.notifyDataSetChanged();
            //        }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                i--;
                if(getActivity().getSupportFragmentManager().findFragmentById(R.id.container_frag) != null) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction().
                            remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.container_frag)).commit();
                    Post_Req_Model postModel = arrayList.get(i);
                    Post_Detail fragment = new Post_Detail();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("faiz", postModel);
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.container_frag, fragment)
                            .addToBackStack(null)
                            .commit();
              //     getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_frag, new My_post_Fragment()).addToBackStack(null).commit();

                }else {
                    Post_Req_Model postModel = arrayList.get(i);
                    Post_Detail fragment = new Post_Detail();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("faiz", postModel);
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.container_frag, fragment)
                            .addToBackStack(null)
                            .commit();
                  getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_frag, new My_post_Fragment()).addToBackStack(null).commit();
                }
            }
        });


        return view;

    }
}
