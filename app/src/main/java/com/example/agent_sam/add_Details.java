package com.example.agent_sam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class add_Details extends AppCompatActivity {

    TextView firstName,lastName,email;
    Button saveBtn;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__details);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        saveBtn = findViewById(R.id.saveBtn);

        firebaseAuth= FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = firebaseAuth.getCurrentUser().getUid();
        final DocumentReference docref = fStore.collection("users").document(userID);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !email.getText().toString().isEmpty()){
                    String first=firstName.getText().toString();
                    String last=lastName.getText().toString();
                    String userEmail= email.getText().toString();

                    Map<String,Object> user = new HashMap<>();
                    user.put("firstName",first);
                    user.put("lastName",last);
                    user.put("email",email);

                    docref.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(add_Details.this,"data is not inserted",Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }else {
                    Toast.makeText(add_Details.this,"All Field are Required",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }
}
