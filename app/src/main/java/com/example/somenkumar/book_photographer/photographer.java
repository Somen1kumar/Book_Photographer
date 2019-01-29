package com.example.somenkumar.book_photographer;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class photographer extends AppCompatActivity {
    Button sign;
    EditText firstnam,lastnam,emaili,passwor,phonen,addres;
    public String sfirstname,semail,slastname,sphoneno,saddress,sinformation,spass;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    String TAG="hello";
    HashMap<String,String> map1=new HashMap<>();
    HashMap<String,String[]>map2=new HashMap<>();
    HashMap<String,List<String>> map3=new HashMap<>();
    String strfirstname,strlastname,stremailname,strpassname,straddressname,strphonename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer);
        sign=(Button)findViewById(R.id.signup);
        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final DatabaseReference myref=database.getReference("message");
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstnam=(EditText)findViewById(R.id.firstname);
                lastnam=(EditText)findViewById(R.id.lastname);
                emaili=(EditText)findViewById(R.id.emailid);
                passwor=(EditText)findViewById(R.id.password);
                addres=(EditText)findViewById(R.id.Address);
                phonen=(EditText)findViewById(R.id.Phonenumber);
                strfirstname=firstnam.getText().toString();
               strlastname=lastnam.getText().toString();
                stremailname=emaili.getText().toString();
                strpassname=passwor.getText().toString();
                straddressname=addres.getText().toString();
                strphonename=phonen.getText().toString();
               // sfirstname=strfirstname;sphoneno=strphonename;semail=stremailname;spass=strpassname;sinformation=strinformation;slastname=strlastname;saddress=straddressname;
                String[] ss = new String[10];
                if(strfirstname!=null && strlastname!=null && stremailname!=null &&strpassname!=null &&straddressname!=null){
                    ss[0]=strfirstname;
                    ss[1]=strlastname;
                    ss[2]=stremailname;
                    ss[3]=strpassname;
                    ss[4]=straddressname;
                    ss[5]=strphonename;
                }
                else
                    Toast.makeText(getApplicationContext(),"Fill details correctly",Toast.LENGTH_SHORT).show();

                map3.put("details",Arrays.asList(ss));
               /* try {
                    myref.push().setValue(map3);
                    Intent sss=new Intent(photographer.this,MainActivity.class);
                    startActivity(sss);
                    Toast.makeText(getApplicationContext(),"Sign Up Successfully",Toast.LENGTH_LONG).show();
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Sign Up Failed",Toast.LENGTH_LONG).show();
                }*/


               mAuth.createUserWithEmailAndPassword(stremailname,strpassname).
                       addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        try {
                            myref.push().setValue(map3);
                            Intent sss=new Intent(photographer.this,MainActivity.class);
                            startActivity(sss);
                            Toast.makeText(getApplicationContext(),"Sign Up Successfully",Toast.LENGTH_LONG).show();
                        }catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"Sign Up Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }

                   }
               });
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null)
        {

        }
    }

}
