package com.example.samikhan.saylani_final_project.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.samikhan.saylani_final_project.AppLogs;
import com.example.samikhan.saylani_final_project.R;
import com.example.samikhan.saylani_final_project.Model.UserModels;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Sami Khan on 2/24/2017.
 */

public class Signup_Fragment extends android.support.v4.app.Fragment {

    private EditText email,userID,password,confirmpass,fname,lname;
    private Button signup;
    private FirebaseAuth mAuth;
    private FirebaseUser firebase_user;
    private DatabaseReference firebase;
    private Spinner blood_group;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.signup_view,null);

        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        email = (EditText) view.findViewById(R.id.editText_email);
        password = (EditText) view.findViewById(R.id.editText_password);
        confirmpass = (EditText) view.findViewById(R.id.editText_cpassword);
        fname = (EditText) view.findViewById(R.id.editText_fname);
        lname = (EditText) view.findViewById(R.id.editText_lname);
        signup = (Button)view.findViewById(R.id.signup_btn);
        blood_group = (Spinner)view.findViewById(R.id.spin_blood_group);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pass = password.getText().toString();
                final String confrim_passwordd = confirmpass.getText().toString();
                //Checking the length of pasword while registering new USER;
                if (pass.length() <= 6) {
                    main(pass);
                }else if(!pass.matches(confrim_passwordd)){
                    Toast.makeText(getActivity(),"Something Wrong in Password",Toast.LENGTH_SHORT).show();
                    confirmpass.setError("Pass not match");
                }else if(( fname.getText().toString().equals("")
                        || lname.getText().toString().equals("")
                        || pass.equals("")
                        || confrim_passwordd.equals("")) ){
                    Toast.makeText(getActivity(),"Fields Should not be left Empty",Toast.LENGTH_SHORT).show();

                }
                else if(email.getText().length()==0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() ){
                    email.setError("Enter Valid Email Address");
                }
                else if(fname.getText().length()== 0 || !fname.getText().toString().matches("[a-zA-Z ]+")){
                    fname.setError("Invalid Name");
                }
                else if(lname.getText().length() == 0 || !lname.getText().toString().matches("[a-zA-Z ]+")){
                    lname.setError("Invalid Name");
                }
                //Checking the length of pasword while registering new USER;
                else if (pass.length() <= 6) {
                    main(pass);
                } else {
                    try {
                        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Sign Up", "Connecting...", true, false);

                        mAuth.createUserWithEmailAndPassword((email.getText().toString()), (password.getText().toString())).addOnCompleteListener(getActivity(),
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        String uid = mAuth.getCurrentUser().getUid();
                                        firebase.child("users").child(uid).setValue(new UserModels(email.getText().toString(),pass,confrim_passwordd,uid,fname.getText().toString(),lname.getText().toString(),blood_group.getSelectedItem().toString()));
//                                                if (task.isSuccessful()) {
                                        //  firebase.child("User").child(uid).setValue();
                                        progressDialog.dismiss();
                                        if(getActivity().getSupportFragmentManager().findFragmentById(R.id.container) != null) {
                                            getActivity().getSupportFragmentManager()
                                                    .beginTransaction().
                                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.container)).commit();
                                        }
                                        Toast.makeText(getActivity(), "Successfull", Toast.LENGTH_SHORT).show();
                                        AppLogs.logd("createUserWithEmail:onComplete: " + task.isSuccessful());

//                                                } else
                                        if (!task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), " " + task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                AppLogs.d("FailureSignup",e.getMessage());

                            }
                        });

                    } catch (Exception ex) {

                        ex.printStackTrace();
                    }
                }
            }
        });




        return view;
    }
    private void main(String pass) {

        Toast.makeText(getActivity(), pass + "\nYou Password is no longer Stronger \nPlease signup Again with \natleast 7 Character of Pasword.\nThanks ", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
