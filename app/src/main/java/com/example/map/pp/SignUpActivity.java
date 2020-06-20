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

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private DatabaseReference mPostReference;
    EditText UserName, Id, PassWord, BirthDay;
    String name,id,password,birth;
    long birthday=0;
    Button SignUp;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mPostReference= FirebaseDatabase.getInstance().getReference("id_list");
        Intent intent=getIntent();
        UserName=findViewById(R.id.EdName);
        Id=findViewById(R.id.EdId);
        PassWord=findViewById(R.id.EdPassword);
        BirthDay=findViewById(R.id.EdBirth);
        SignUp=findViewById(R.id.SignUpB);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=UserName.getText().toString();
                id=Id.getText().toString();
                password=PassWord.getText().toString();
                birth=BirthDay.getText().toString();
                birthday=Long.parseLong(birth);
                if((name.length()*password.length()*id.length()*birth.length())==0){
                    Toast.makeText(SignUpActivity.this,"please fill all blanks", Toast.LENGTH_SHORT).show();
                }else {
                    mPostReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(count==0) {
                                if (dataSnapshot.child(id).exists()) {
                                    Toast.makeText(SignUpActivity.this, "Please use another Id", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Sign up successfully", Toast.LENGTH_LONG).show();
                                    birthday = Long.parseLong(birth);
                                    postFirebaseDatabase(true);

                                    Intent intent1 = new Intent(SignUpActivity.this, MainActivity.class);

                                    intent1.putExtra("id", id);
                                    startActivity(intent1);
                                    count++;

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
    public void postFirebaseDatabase(boolean add){
        Map<String, Object> childUpdates=new HashMap<>();
        Map<String, Object> postValues=null;
        if(add){
            FirebasePost post=new FirebasePost(name,id, password,birthday);
            postValues=post.toMap();

        }
        childUpdates.put(id,postValues);
        mPostReference.updateChildren(childUpdates);
        clearET();
    }
    public void clearET(){
        UserName.setText("");
        Id.setText("");
        PassWord.setText("");
        BirthDay.setText("");
        birthday=0;
    }
}
