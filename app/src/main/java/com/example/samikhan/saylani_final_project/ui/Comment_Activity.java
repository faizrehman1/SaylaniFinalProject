package com.example.samikhan.saylani_final_project.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.samikhan.saylani_final_project.Adapters.CardAdapter;
import com.example.samikhan.saylani_final_project.Adapters.ToBoAdapter;
import com.example.samikhan.saylani_final_project.Model.Commit_for;
import com.example.samikhan.saylani_final_project.Model.Post_Req_Model;
import com.example.samikhan.saylani_final_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Comment_Activity extends AppCompatActivity {

    private ListView main_post,coomment_list;
    private CardAdapter cardAdapter;
    private CardAdapter cardArrayAdapter;
    private ListView listView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference firebase;
    private ToBoAdapter toBoAdapter;
    private ArrayList<Commit_for> commit_forArrayList;
    private EditText editText;
    private Button Sendbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_);
        final String value = getIntent().getStringExtra("post_id");
        commit_forArrayList = new ArrayList<>();
        main_post = (ListView)findViewById(R.id.main_post_commit);
        coomment_list = (ListView)findViewById(R.id.commmit_ist);
        editText = (EditText)findViewById(R.id.edt_comment);
        Sendbtn = (Button)findViewById(R.id.send_cmt);
        toBoAdapter = new ToBoAdapter(commit_forArrayList,Comment_Activity.this);
        main_post.addHeaderView(new View(Comment_Activity.this));
        main_post.addFooterView(new View(Comment_Activity.this));
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        cardArrayAdapter = new CardAdapter(Comment_Activity.this,R.layout.list_item_card);
        main_post.setAdapter(cardArrayAdapter);
        coomment_list.setAdapter(toBoAdapter);


        firebase.child("users_feed").child(value).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cardArrayAdapter.clear();
                if (dataSnapshot != null) {
               //     for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Post_Req_Model dataa = dataSnapshot.getValue(Post_Req_Model.class);
                        cardArrayAdapter.add(dataa);
                        cardArrayAdapter.notifyDataSetChanged();
                 //   }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().matches("")){
                    Toast.makeText(Comment_Activity.this,"Fill field",Toast.LENGTH_SHORT).show();
                }else{
                    firebase.child("comments").child(value).push().setValue(new Commit_for(mAuth.getCurrentUser().getEmail(),editText.getText().toString()));
                    commit_forArrayList.add(new Commit_for(mAuth.getCurrentUser().getEmail(),editText.getText().toString()));
                    toBoAdapter.notifyDataSetChanged();
                    editText.setText("");
                }
            }
        });

        firebase.child("comments").child(value).addValueEventListener(new ValueEventListener() {
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


    }
}
