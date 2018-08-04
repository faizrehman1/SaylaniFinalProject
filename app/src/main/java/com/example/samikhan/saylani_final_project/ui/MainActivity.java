package com.example.samikhan.saylani_final_project.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samikhan.saylani_final_project.AppLogs;
import com.example.samikhan.saylani_final_project.Model.UserModels;
import com.example.samikhan.saylani_final_project.R;
import com.example.samikhan.saylani_final_project.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainThread";
    private EditText email, pass;
    private Button loginbtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference firebase;
    private TextView signupText;
    private android.support.v4.app.FragmentManager fragmentManager;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        email = (EditText) findViewById(R.id.editText_Loginemail);
        pass = (EditText) findViewById(R.id.editText_loginpass);
        loginbtn = (Button) findViewById(R.id.login_btn);
        signupText = (TextView) findViewById(R.id.sign_up);
        fragmentManager = getSupportFragmentManager();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    currentUser.getUid();
                    try {
                        firebase.child("users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                UserModels user = dataSnapshot.getValue(UserModels.class);
//                                AppLogs.logd("User Logged In For My Auth:" + user.getEmail());
                                if (user != null) {
                                    SharedPref.setCurrentUser(MainActivity.this, user);
                                    openNavigationActivity();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                AppLogs.loge("Error Logged In MYAUTH");

                            }
                        });

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }


            }



        };




            signupText.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View v){
                fragmentManager.beginTransaction().add(R.id.container, new Signup_Fragment()).addToBackStack(null).commit();
            }
            }

            );


            loginbtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View v){
                final String emails = email.getText().toString();
                String passo = pass.getText().toString();
                if (emails.length() == 0) {
                    email.setError("This is Required Field");
                } else if (passo.length() == 0 && passo.length() <= 6) {
                    pass.setError("This is Required Field");
                } else {
                    try {
                        final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Sign In", "Connecting...", true, false);
                        mAuth.signInWithEmailAndPassword(emails, passo).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull final Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    AppLogs.logd("signInWithEmail:onComplete:" + task.isSuccessful());
                                    progressDialog.dismiss();
                                    Toast.makeText(MainActivity.this,"HelloWorld",Toast.LENGTH_SHORT).show();
//                                    if (emails.toString().matches("admin@gmail.com")) {
//                                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
//                                        startActivity(intent);
//                                        finish();
//                                    } else {
                                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
//                                    }
                                } else if (!task.isSuccessful()) {
                                    AppLogs.logw("signInWithEmail" + task.getException());
                                    Toast.makeText(MainActivity.this, "" + task.getException(),
                                            Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();

                                }
                            }
                        }).addOnFailureListener(MainActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                AppLogs.d("FailureSignin", e.getMessage());
                            }
                        });


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            }

            );
        }

    private void openNavigationActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart () {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}