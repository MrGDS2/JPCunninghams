package build.cunninghams.jp.jpcunninghamsapp.UserDB;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import build.cunninghams.jp.jpcunninghamsapp.R;

public class SignUp extends AppCompatActivity {

    private EditText etPhone,etUserName,etPassword;
    AppCompatButton btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etPhone=(EditText) findViewById(R.id.PhoneNumber);
        etPassword=(EditText) findViewById(R.id.Password);
        etUserName=(EditText) findViewById(R.id.usrName);

        btnSignup=(AppCompatButton) findViewById(R.id.btnConfirm);


//Init Firebase

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference user_table = db.getReference("User");




        /**register information**/
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mdialog= new ProgressDialog(SignUp.this);
                mdialog.setMessage("Creating account...");
                mdialog.show();

                user_table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //check if phone number already exist
                        if(dataSnapshot.child(etPhone.getText().toString()).exists())
                        {
                            mdialog.dismiss();
                            Toast.makeText(SignUp.this, R.string.ExistUsr, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mdialog.dismiss();
                            User usr= new User(etUserName.getText().toString(),etPassword.getText().toString());
                            user_table.child(etPhone.getText().toString()).setValue(usr);//set's name and password under phone number key on firebase 'db'
                            Toast.makeText(SignUp.this, R.string.SignUpsuccess, Toast.LENGTH_SHORT).show();
                            SignUp.this.finish();//can't go back to page
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
