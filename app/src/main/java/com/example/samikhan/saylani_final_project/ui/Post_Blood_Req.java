package com.example.samikhan.saylani_final_project.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.samikhan.saylani_final_project.Model.Post_Req_Model;
import com.example.samikhan.saylani_final_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Sami Khan on 2/26/2017.
 */

public class Post_Blood_Req extends android.support.v4.app.Fragment {

    private Spinner bloodGroup,Urgency,country,state,city,hospital,your_relation_w_patrient;
    private EditText no_of_unit,contact_no,addition,instruction;
    private Button post_req;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference firebase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.post_blood_require,null);
        super.onCreateView(inflater, container, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        bloodGroup = (Spinner)view.findViewById(R.id.BloodGroup);
        Urgency = (Spinner)view.findViewById(R.id.Urgency);
        country = (Spinner)view.findViewById(R.id.Country);
        state = (Spinner)view.findViewById(R.id.State);
        city = (Spinner)view.findViewById(R.id.City);
        hospital = (Spinner)view.findViewById(R.id.Hospital);
        your_relation_w_patrient = (Spinner)view.findViewById(R.id.Relation);
        no_of_unit = (EditText)view.findViewById(R.id.Numberofunits);
        contact_no = (EditText)view.findViewById(R.id.contact);
        instruction = (EditText)view.findViewById(R.id.Instruction);
        post_req  = (Button)view.findViewById(R.id.subbmit);



        post_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Post Request", "Sending...", true, false);

                if(no_of_unit.getText().toString().matches("") || contact_no.getText().toString().matches("") || instruction.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "Fill Required Unit", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {

                    DatabaseReference ref  = firebase.child("user_req").child(mAuth.getCurrentUser().getUid()).push();

                    firebase.child("user_req").child(mAuth.getCurrentUser().getUid()).child(ref.getKey()).setValue(new Post_Req_Model(bloodGroup.getSelectedItem().toString(),Integer.valueOf(no_of_unit.getText().toString())
                    ,Urgency.getSelectedItem().toString(),country.getSelectedItem().toString(),state.getSelectedItem().toString(),
                           city.getSelectedItem().toString(),hospital.getSelectedItem().toString(),your_relation_w_patrient.getSelectedItem().toString(),contact_no.getText().toString(),instruction.getText().toString()
                    ,0,mAuth.getCurrentUser().getEmail(),ref.getKey()
                    ));
                    firebase.child("users_feed").child(ref.getKey()).setValue(new Post_Req_Model(bloodGroup.getSelectedItem().toString(),Integer.valueOf(no_of_unit.getText().toString())
                            ,Urgency.getSelectedItem().toString(),country.getSelectedItem().toString(),state.getSelectedItem().toString(),
                            city.getSelectedItem().toString(),hospital.getSelectedItem().toString(),your_relation_w_patrient.getSelectedItem().toString(),contact_no.getText().toString(),instruction.getText().toString()
                            ,0,mAuth.getCurrentUser().getEmail(),ref.getKey()
                    ));
                    Toast.makeText(getActivity(),"Successfuly Post !!!",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                }
            }
        });




        return view;
    }
}
