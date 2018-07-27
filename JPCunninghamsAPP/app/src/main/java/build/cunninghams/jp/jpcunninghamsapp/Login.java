package build.cunninghams.jp.jpcunninghamsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {


    private Login m;
    private static final String TAG = Login.class.getSimpleName();
  //  private String Phone, Password;
    EditText ePhone, ePassword;
    AppCompatButton btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        m = this;//place marker for page closeout

        ePhone = (EditText) findViewById(R.id.PhoneNumber);
        ePassword = (EditText) findViewById(R.id.Password);

        btnConfirm = (AppCompatButton) findViewById(R.id.btnConfirm);

        //Init Firebase

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
         final DatabaseReference user_table = db.getReference("User");


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mdialog = new ProgressDialog(Login.this);
                mdialog.setMessage("Connecting...");
                mdialog.show();

                user_table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //check usr existance in db



                        if (dataSnapshot.child(ePhone.getText().toString()).exists()) {
                            //get user information
                            mdialog.dismiss();
                            User usr = dataSnapshot.child(ePhone.getText().toString()).getValue(User.class);
                            /*if usr password on db == to usr typed in password */
                           if (usr.getPassword().equals(ePassword.getText().toString())) {
                               Toast.makeText(Login.this, R.string.success, Toast.LENGTH_SHORT).show();
                           } else {
                               Toast.makeText(Login.this, R.string.logError, Toast.LENGTH_SHORT).show();

                            }
                            Toast.makeText(Login.this, usr.getPassword(), Toast.LENGTH_SHORT).show();
                            Log.d("USR",usr.getPassword());


                        }

                        else
                        {
                            //error non existing usr
                            Toast.makeText(Login.this,R.string.nonExistUsr, Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });





    }



}