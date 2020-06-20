package com.example.map.pp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mPostReference;
    String Id="";
    EditText id, Password;
    Button SignUp, Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPostReference= FirebaseDatabase.getInstance().getReference("id_list");
        id=findViewById(R.id.textId);
        Password=findViewById(R.id.textPassword);
        SignUp=findViewById(R.id.button2);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
               startActivity(intent);
            }
        });
        Intent intent1=getIntent();
        Id=intent1.getStringExtra("id");
        id.setText(Id);
        Login=findViewById(R.id.button1);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString().length()==0){
                    Toast.makeText(MainActivity.this,"Wrong Username", Toast.LENGTH_SHORT).show();
                } else {
                    mPostReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(id.getText().toString()).exists()){
                                FirebasePost fp=dataSnapshot.child(id.getText().toString()).getValue(FirebasePost.class);
                                if(fp.password.equals(Password.getText().toString())){
                                    Toast.makeText(MainActivity.this,"login successfully",Toast.LENGTH_LONG).show();
                                    Intent intent2=new Intent(MainActivity.this,MovieActivity.class);
                                    intent2.putExtra("UserName",id.getText().toString());
                                    startActivity(intent2);
                                }else{
                                    Toast.makeText(MainActivity.this,"Wrong Password",Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }
}