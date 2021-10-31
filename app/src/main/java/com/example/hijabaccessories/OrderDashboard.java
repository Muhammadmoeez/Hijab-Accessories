package com.example.hijabaccessories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class OrderDashboard extends AppCompatActivity {

    ImageView regArrowBackOrder;
    String currentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    RecyclerView.LayoutManager layoutManager;
    Query orderReference;
    private RecyclerView myRecyclerViewOrder;

    FirebaseRecyclerAdapter<OrderModel, MYAllOrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dashboard);

        regArrowBackOrder = (ImageView) findViewById(R.id.arrowBackOrderDashboard);
        regArrowBackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        orderReference = FirebaseDatabase.getInstance().getReference().child("Order")
        .orderByChild("OrderAdminID")
        .equalTo(currentID);


        myRecyclerViewOrder = (RecyclerView) findViewById(R.id.myAllOrdersRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        myRecyclerViewOrder.setLayoutManager(layoutManager);
        
        
        showOrders();

    }

    private void showOrders() {

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<OrderModel>()
                .setQuery(orderReference, OrderModel.class).build();

        adapter = new FirebaseRecyclerAdapter<OrderModel, MYAllOrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull OrderDashboard.MYAllOrderViewHolder myAllOrderViewHolder, int position, @NonNull @NotNull OrderModel orderModel) {


                Picasso.get().load(orderModel.getOrderProductImage()).into(myAllOrderViewHolder.imageViewOrderImageShowPost);

                myAllOrderViewHolder.textViewOrderMemberName.setText(orderModel.getOrderProductMemberName());
                myAllOrderViewHolder.textViewOrderProductName.setText(orderModel.getOrderProductName());
                myAllOrderViewHolder.textViewOrderMemberNumber.setText(orderModel.getOrderProductMemberNumber());
                myAllOrderViewHolder.textViewOrderProductSKU.setText(orderModel.getOrderProductCode());
                myAllOrderViewHolder.textViewOrderItemNumbers.setText(orderModel.getOrderProductItemNumber());
                myAllOrderViewHolder.textViewOrderFullBill.setText(orderModel.getOrderProductFullBill());


                myAllOrderViewHolder.imageViewOrderDeleteShowPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(myAllOrderViewHolder.textViewOrderMemberName.getContext());

                        builder.setTitle("Delete Product");
                        builder.setMessage("Delete.....?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {



                                FirebaseDatabase.getInstance().getReference().child("Order")
                                        .child(getRef(position).getKey()).removeValue();



                                dialogInterface.dismiss();
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        });


                        builder.show();
                    }
                });

            }

            @NonNull
            @NotNull
            @Override
            public MYAllOrderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_post, parent, false);
                return new MYAllOrderViewHolder(view);
            }
        };

        adapter.startListening();
        adapter.notifyDataSetChanged();
        myRecyclerViewOrder.setAdapter(adapter);
    }

    public static class MYAllOrderViewHolder extends RecyclerView.ViewHolder {

        TextView textViewOrderMemberName,textViewOrderProductName,textViewOrderMemberNumber, textViewOrderProductSKU, textViewOrderFullBill, textViewOrderItemNumbers;
        ImageView imageViewOrderImageShowPost, imageViewOrderDeleteShowPost;
        public MYAllOrderViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);




            textViewOrderMemberName = (TextView) itemView.findViewById(R.id.orderMemberName);
            textViewOrderProductName = (TextView) itemView.findViewById(R.id.orderProductName);
            textViewOrderMemberNumber = (TextView) itemView.findViewById(R.id.orderMemberNumber);
            textViewOrderProductSKU = (TextView) itemView.findViewById(R.id.orderProductSKU);
            textViewOrderFullBill = (TextView) itemView.findViewById(R.id.orderFullBill);
            textViewOrderItemNumbers= (TextView) itemView.findViewById(R.id.orderItemNumbers);

            imageViewOrderImageShowPost = (ImageView) itemView.findViewById(R.id.orderImageShowPost);
            imageViewOrderDeleteShowPost = (ImageView) itemView.findViewById(R.id.orderDeleteShowPost);





        }
    }
}