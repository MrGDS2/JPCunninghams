package build.cunninghams.jp.jpcunninghamsapp.Startup;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import build.cunninghams.jp.jpcunninghamsapp.UserDB.Login;
import build.cunninghams.jp.jpcunninghamsapp.R;
import build.cunninghams.jp.jpcunninghamsapp.UserDB.SignUp;

public class MainActivity extends AppCompatActivity {


     private DrawerLayout  mDrawerLayout;
     private ActionBarDrawerToggle mtoggle;
     private AppCompatButton mSignup;
     private AppCompatButton mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       /** mDrawerLayout= (DrawerLayout) findViewById(R.id.drawerlayout);

        mtoggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.Open,R.string.Close);

        mDrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

       getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back nav
**/
        //Sign up
        mSignup= (AppCompatButton) findViewById(R.id.btnSignUp);

        mSignup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
    Intent i= new Intent(MainActivity.this, SignUp.class);
    startActivity(i);
    }
});
        //Login
        mLogin= (AppCompatButton) findViewById(R.id.btnLogin);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(MainActivity.this, "Login Clicked", Toast.LENGTH_LONG).show();
                Intent i= new Intent(MainActivity.this,Login.class);
                startActivity(i);

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mtoggle.onOptionsItemSelected(item))
        {
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }
}
