package build.cunninghams.jp.jpcunninghamsapp.Startup;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import build.cunninghams.jp.jpcunninghamsapp.CurrentUsr.Currentusr;
import build.cunninghams.jp.jpcunninghamsapp.Interface.ItemClickListener;
import build.cunninghams.jp.jpcunninghamsapp.Model.Category;
import build.cunninghams.jp.jpcunninghamsapp.R;
import build.cunninghams.jp.jpcunninghamsapp.ViewHolder.MenuViewholder;

public class Home_Nav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
     FirebaseDatabase db;
     DatabaseReference category_table;
     TextView tvUsername;


     RecyclerView recyclerView;
     RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Food Menu");
        setSupportActionBar(toolbar);


             //Init Firebase
         db = FirebaseDatabase.getInstance();
       category_table = db.getReference("Category");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundColor(getResources().getColor(R.color.btnColor));
       // fab.setImageDrawable(R.color.overlayAction);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        //set user name in menu
        View hv =navigationView.getHeaderView(0);
        tvUsername= (TextView) hv.findViewById(R.id.textboxUserName);
        tvUsername.setText(Currentusr.currentUser.getName());

  //loads menu

  recyclerView=(RecyclerView) findViewById(R.id.recycler_menu);
  recyclerView.setHasFixedSize(true);
  layoutManager =new LinearLayoutManager(Home_Nav.this);
  recyclerView.setLayoutManager(layoutManager);

  loadMenu();

    }

    private void loadMenu() {

        //Bind firebase UI

        FirebaseRecyclerAdapter<Category,MenuViewholder>  adapter= new FirebaseRecyclerAdapter<Category, MenuViewholder>
                (Category.class,R.layout.menu_item,MenuViewholder.class,category_table)
        {
            @Override
            protected void populateViewHolder(MenuViewholder viewHolder, Category item, int position) {

            viewHolder.tvMenuName.setText(item.getName());
                Picasso.with(getBaseContext()).load(item.getImage()).into(viewHolder.ivMenuImage);

                final Category clickItem=item;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClicked) {
                        Toast.makeText(Home_Nav.this,""+clickItem.getName(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };

        //set Recycler adapter

        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home__nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.account) {
            // Handle the camera action
        } else if (id == R.id.buff_prices) {

        } else if (id == R.id.drinksmenu) {

        }  else if (id == R.id.hours) {

        } else if (id == R.id.location) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
