package com.example.hijabaccessories;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class FragmentHijab extends Fragment {


    View viewHijab;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase firebaseDatabase;
    Query databaseReferenceHijab;

    private RecyclerView myRecyclerViewHijab;
    FirebaseRecyclerAdapter<ProductModel, MyAllPublicViewHolder> adapter;


    public FragmentHijab() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceHijab = firebaseDatabase.getReference("AllProduct")
                .orderByChild("ProductCategory")
                .equalTo("Hijab");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewHijab = inflater.inflate(R.layout.fragment_hijab, container,false);
        myRecyclerViewHijab = (RecyclerView) viewHijab.findViewById(R.id.myAllHijabProductRecyclerView);
        layoutManager = new GridLayoutManager(getActivity(), 2);

        myRecyclerViewHijab.setLayoutManager(layoutManager);

        showList();

        return viewHijab;
    }

    private void showList() {

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<ProductModel>()
                .setQuery(databaseReferenceHijab, ProductModel.class).build();

        adapter = new FirebaseRecyclerAdapter<ProductModel, MyAllPublicViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FragmentHijab.MyAllPublicViewHolder myAllPublicViewHolder, int i, @NonNull ProductModel productModel) {

                Picasso.get().load(productModel.getProductImage()).into(myAllPublicViewHolder.viewPublicProductImage);
                myAllPublicViewHolder.viewPublicProductName.setText(productModel.getProductName());
                myAllPublicViewHolder.viewPublicProductPrice.setText(productModel.getProductPrice());


                myAllPublicViewHolder.SubCategoryInterfaceClick(new CategoryOnClickShowFullPost() {
                    @Override
                    public void onClick(View view, boolean isLongPressed) {

                        Intent intent = new Intent(getContext(),ShowFullPostForOrder.class);

                        intent.putExtra("AdminNumber",productModel.getAdminNumber());
                        intent.putExtra("AdminID", productModel.getAdminId());
                        intent.putExtra("ProductCategory", productModel.getProductCategory());
                        intent.putExtra("ProductCode", productModel.getProductCode());
                        intent.putExtra("ProductDescription",productModel.getProductDescription());
                        intent.putExtra("ProductID",productModel.getProductID());
                        intent.putExtra("ProductImage", productModel.getProductImage());
                        intent.putExtra("ProductName",productModel.getProductName());
                        intent.putExtra("ProductPrice",productModel.getProductPrice());
                        intent.putExtra("ProductAvailability",productModel.getProductStock());
                        intent.putExtra("ProductSubCategory",productModel.getProductSubCategory());
                        startActivity(intent);

                    }
                });
            }

            @NonNull

            @Override
            public MyAllPublicViewHolder onCreateViewHolder(@NonNull  ViewGroup viewGroup, int viewType) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.public_post, viewGroup, false);
                return new MyAllPublicViewHolder(view);
            }
        };


        adapter.startListening();
        adapter.notifyDataSetChanged();
        myRecyclerViewHijab.setAdapter(adapter);



    }


    public static class MyAllPublicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView viewPublicProductImage;
        TextView viewPublicProductName;
        TextView viewPublicProductPrice;


        public CategoryOnClickShowFullPost categoryOnClickShowFullPostInterface;

        public MyAllPublicViewHolder(@NonNull View itemView) {
            super(itemView);

            viewPublicProductImage = (ImageView) itemView.findViewById(R.id.publicImageShowPost);
            viewPublicProductName = (TextView) itemView.findViewById(R.id.publicNameShowPost);
            viewPublicProductPrice = (TextView) itemView.findViewById(R.id.publicPriceShowPost);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            categoryOnClickShowFullPostInterface.onClick(view, false);

        }


        public void SubCategoryInterfaceClick(CategoryOnClickShowFullPost categoryOnClickShowFullPost)
        {
            this.categoryOnClickShowFullPostInterface = categoryOnClickShowFullPost;
        }
    }


}


