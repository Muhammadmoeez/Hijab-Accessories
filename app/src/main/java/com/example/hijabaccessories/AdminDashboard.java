package com.example.hijabaccessories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminDashboard extends AppCompatActivity {

    private Toolbar toolbar;


    AdminAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView myRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        toolbar = (Toolbar) findViewById(R.id.adminDashboardToolBar);
        setSupportActionBar(toolbar);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Product")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        myRecyclerView = (RecyclerView) findViewById(R.id.myAllProductRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<ProductModel> options = new FirebaseRecyclerOptions.Builder<ProductModel>()
                .setQuery(databaseReference, ProductModel.class).build();




        adapter = new AdminAdapter(options);
        myRecyclerView.setAdapter(adapter);









    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id== R.id.addProduct)
        {
            Intent intent = new Intent(this,AddItem.class);
            startActivity(intent);
        }
        else if (id == R.id.addNewCategory){
            Intent intent = new Intent(this,AddCategory.class);
            startActivity(intent);
        }
        else if (id == R.id.adminShowMyAllOrders){

            Intent intent = new Intent(this,OrderDashboard.class);
            startActivity(intent);

        }
        else if (id == R.id.publicView){


            Intent intent = new Intent(this,PublicDashboard.class);
            startActivity(intent);
        }
        else if (id == R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            Intent intent1 = new Intent(this,PublicDashboard.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent1);
        }

        return super.onOptionsItemSelected(item);
    }

}