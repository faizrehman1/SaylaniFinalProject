package com.example.samikhan.saylani_final_project.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samikhan.saylani_final_project.Model.Post_Req_Model;
import com.example.samikhan.saylani_final_project.Model.Volunteer_model;
import com.example.samikhan.saylani_final_project.R;
import com.example.samikhan.saylani_final_project.SharedPref;
import com.example.samikhan.saylani_final_project.ui.Comment_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sami Khan on 2/26/2017.
 */

public class CardAdapter extends ArrayAdapter<Post_Req_Model> {

    private static final String TAG = "CardArrayAdapter";
    private ArrayList<Post_Req_Model> cardList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private Context context;
    int i=0;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference firebase;
    static class CardViewHolder {
        TextView line1;
        TextView line2;
        TextView line3;
        TextView line4;
        TextView line5;
        TextView line6;
        TextView line7;
        TextView line8;
        Button volunter,comments;
    }


    public CardAdapter(Context context, int textViewResourceId) {
        super(context,textViewResourceId);
        this.context = context;
    }

    @Override
    public void add(Post_Req_Model object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();    }

    @Override
    public Post_Req_Model getItem(int index) {
        return this.cardList.get(index);
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View row = convertView;
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        final CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_card, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.line1 = (TextView) row.findViewById(R.id.line1);
            viewHolder.line2 = (TextView) row.findViewById(R.id.line2);
            viewHolder.line3 = (TextView)row.findViewById(R.id.line3);
            viewHolder.line4 = (TextView)row.findViewById(R.id.line4);
            viewHolder.line5 = (TextView)row.findViewById(R.id.line5);
            viewHolder.line6 = (TextView)row.findViewById(R.id.line6);
            viewHolder.line7 = (TextView)row.findViewById(R.id.line7);
            viewHolder.line8 = (TextView)row.findViewById(R.id.line8);
            viewHolder.volunter = (Button)row.findViewById(R.id.volun);
            viewHolder.comments = (Button)row.findViewById(R.id.comment);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        final Post_Req_Model card = getItem(position);
        viewHolder.line1.setText(mAuth.getCurrentUser().getEmail().toString());
        viewHolder.line2.setText(card.getNo_of_unit()+" units of "+card.getBlood_Group()+" required");
        viewHolder.line3.setText("At "+card.getHospital()+" for my "+card.getRelation());
        viewHolder.line4.setText("Urgency: "+card.getUrgency().toString());
        viewHolder.line5.setText("Contact at: "+card.getContact_no());
        viewHolder.line6.setText("Addtional Instruction: "+"\n"+card.getInstruction());
        viewHolder.line7.setText("Vlounteer Uptill now: "+card.getVolunteer());
        if(card.getVolunteer()==0) {

        }else{
            viewHolder.line8.setText("Current Requirment: " +String.valueOf(card.getVolunteer() - card.getNo_of_unit()));
        }

        viewHolder.volunter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               i=0;
                firebase.child("users_feed").child(cardList.get(position).getPost_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null){
                            Post_Req_Model post_req_model  = dataSnapshot.getValue(Post_Req_Model.class);
                        int pos = post_req_model.getVolunteer()+1;
                            firebase.child("users_feed").child(cardList.get(position).getPost_id()).child("volunteer").setValue(pos);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Toast.makeText(context,"Hello",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Volunteer");
                builder.setMessage("You want to Donate or Note ?");
                builder.setPositiveButton("Donated", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebase.child("volunteer").child("donation").child(cardList.get(position).getPost_id()).setValue(new Volunteer_model(mAuth.getCurrentUser().getEmail(), SharedPref.getCurrentUser(context).getBlood_group(),true,""));
                    }
                });
                builder.setNegativeButton("Not Donated", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebase.child("volunteer").child("not_donation").child(cardList.get(position).getPost_id()).setValue(new Volunteer_model(mAuth.getCurrentUser().getEmail(), SharedPref.getCurrentUser(context).getBlood_group(),true,""));
                    }
                });
                builder.create().show();
            notifyDataSetChanged();
            }
        });


        viewHolder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Comment_Activity.class);
                intent.putExtra("post_id",cardList.get(position).getPost_id());
                context.startActivity(intent);
            }
        });


        return row;
    }
}