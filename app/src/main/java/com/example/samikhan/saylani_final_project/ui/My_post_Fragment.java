package com.example.samikhan.saylani_final_project.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.samikhan.saylani_final_project.Adapters.My_req_Adapter;
import com.example.samikhan.saylani_final_project.AppLogs;
import com.example.samikhan.saylani_final_project.Model.Post_Req_Model;
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

public class My_post_Fragment extends android.support.v4.app.Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ArrayList<Post_Req_Model> postModelArrayList;
    private ListView myPostsListView;
    private My_req_Adapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.post_feed_list,null);
        postModelArrayList = new ArrayList<>();
            myPostsListView = (ListView) view.findViewById(R.id.my_posts_listView);
            adapter = new My_req_Adapter(postModelArrayList,getActivity());
            myPostsListView.setAdapter(adapter);

            databaseReference.child("user_req").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            AppLogs.d("Kamran", "45623 " + snapshot.getValue().toString());
                            Post_Req_Model value = snapshot.getValue(Post_Req_Model.class);
                            postModelArrayList.add(value);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        return view;
    }
}
