package build.cunninghams.jp.jpcunninghamsapp.UserDB;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import build.cunninghams.jp.jpcunninghamsapp.CurrentUsr.Currentusr;
import build.cunninghams.jp.jpcunninghamsapp.Model.User;
import build.cunninghams.jp.jpcunninghamsapp.R;
import build.cunninghams.jp.jpcunninghamsapp.Startup.Home_Nav;


public class Login extends AppCompatActivity {


    private Login m;
    private static final String TAG = Login.class.getSimpleName();
   private String Phone, Password;
   private CheckBox cbMemory;
   private boolean savedMemory;
    EditText etPhone, etPassword;
    AppCompatButton btnConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        m = this;//place marker for page closeout

        etPhone = (EditText) findViewById(R.id.PhoneNumber);
        etPassword = (EditText) findViewById(R.id.Password);

        btnConfirm = (AppCompatButton) findViewById(R.id.btnConfirm);
        cbMemory= (CheckBox) findViewById(R.id.rememberme);

        //Init Firebase

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
         final DatabaseReference user_table = db.getReference("User");


         cbMemory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                 savedMemory=b;
             }
         });

        /*   *****************Loads the data from sharepref**** */
        SharedPreferences sharedPreferences=getPreferences(Context.MODE_PRIVATE);
         savedMemory=sharedPreferences.getBoolean("save",false);
         cbMemory.setChecked(savedMemory);

         String phone=sharedPreferences.getString("phone","");
         String password=sharedPreferences.getString("password","");
         /**sets stored values to et*/
         etPhone.setText(phone);
         etPassword.setText(password);
 /*

                }*/


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



                        if (dataSnapshot.child(etPhone.getText().toString()).exists()) {
                            //get user information
                            mdialog.dismiss();
                            User usr = dataSnapshot.child(etPhone.getText().toString()).getValue(User.class);
                            /*if usr password on db == to usr typed in password */
                           if (usr.getPassword().equals(etPassword.getText().toString())) {
                               //Toast.makeText(Login.this, R.string.logsuccess, Toast.LENGTH_SHORT).show();
                               Intent i = new Intent(Login.this, Home_Nav.class);

                               Currentusr.currentUser=usr;//saves current user
                               startActivity(i);//after login go to home page
                               Login.this.finish();

                               Phone = etPhone.getText().toString();
                               Password=etPassword.getText().toString();

                               if(savedMemory) {

                                   SharedPreferences savepref = getPreferences(Context.MODE_PRIVATE);
                                   SharedPreferences.Editor editor = savepref.edit();
                                   editor.putString("phone", Phone);
                                   editor.putString("password", Password);
                                   editor.putBoolean("save", true);                          //stores username and password on device when checked
                                   editor.apply();
                               }
                           } else {
                               Toast.makeText(Login.this, R.string.logError, Toast.LENGTH_SHORT).show();
                                mdialog.dismiss();
                            }
                            Toast.makeText(Login.this, usr.getPassword(), Toast.LENGTH_SHORT).show();
                            Log.d("USR",usr.getPassword());


                        }

                        else
                        {
                            //error non existing usr
                            Toast.makeText(Login.this,R.string.nonExistUsr, Toast.LENGTH_SHORT).show();
                            mdialog.dismiss();
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