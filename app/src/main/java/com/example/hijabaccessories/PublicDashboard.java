package com.example.hijabaccessories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class PublicDashboard extends AppCompatActivity {


    ImageView regArrowBackPublicDashboard;



    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


//
//    RecyclerView recyclerView, recyclerViewAccessories;
//    RecyclerView.LayoutManager layoutManager;
//    FirebaseDatabase firebaseDatabase;
//    Query databaseReferenceHijab;

//    FirebaseRecyclerAdapter<ProductModel, MyAllPublicViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_dashboard);

        regArrowBackPublicDashboard = (ImageView) findViewById(R.id.arrowBackPublicDashboard);
        regArrowBackPublicDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.publicDashboardToolBar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);


        ViewPagerAdapter  adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentHijab(),"Hijab");
        adapter.AddFragment(new FragmentAccessories(),"Accessories");




        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.public_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

         if (id == R.id.adminLogIn){

            Intent intent = new Intent(this,SignIn.class);
            startActivity(intent);

        }



        return super.onOptionsItemSelected(item);
    }



}