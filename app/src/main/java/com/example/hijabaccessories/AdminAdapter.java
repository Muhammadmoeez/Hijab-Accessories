package com.example.hijabaccessories;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminAdapter extends FirebaseRecyclerAdapter<ProductModel, AdminAdapter.MyAllProductViewHolder> {



    public AdminAdapter(@NonNull FirebaseRecyclerOptions<ProductModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdminAdapter.MyAllProductViewHolder myAllProductViewHolder, int position, @NonNull ProductModel productModel) {



        Picasso.get().load(productModel.getProductImage()).into(myAllProductViewHolder.imageViewProductImage);
        myAllProductViewHolder.textViewProductCategory.setText(productModel.getProductCategory());
        myAllProductViewHolder.textViewProductName.setText(productModel.getProductName());
        myAllProductViewHolder.textViewProductSubCategory.setText(productModel.getProductSubCategory());
        myAllProductViewHolder.textViewProductPrice.setText(productModel.getProductPrice());

        myAllProductViewHolder.imageViewViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(myAllProductViewHolder.textViewProductName.getContext())
                        .setContentHolder(new ViewHolder(R.layout.admin_view_layout))
                        .setExpanded(true, 1800)
                        .create();

                View myViewForView = dialogPlus.getHolderView();
                ImageView productImageViewForView = (ImageView) myViewForView.findViewById(R.id.adminViewImageView);
                TextView productNameView = (TextView) myViewForView.findViewById(R.id.adminViewProductName);
                TextView productCategoryView = (TextView) myViewForView.findViewById(R.id.adminViewProductCategory);
                TextView productSubCategoryView = (TextView) myViewForView.findViewById(R.id.adminViewProductSubCategory);
                TextView productStockAvailability = (TextView) myViewForView.findViewById(R.id.adminViewProductAvailability);
                TextView productPriceView = (TextView) myViewForView.findViewById(R.id.adminViewProductPrice);
                TextView productDescriptionView = (TextView) myViewForView.findViewById(R.id.adminViewProductDescription);


                Picasso.get().load(productModel.getProductImage()).into(productImageViewForView);
                productNameView.setText(productModel.getProductName());
                productCategoryView.setText(productModel.getProductCategory());
                productSubCategoryView.setText(productModel.getProductSubCategory());
                productStockAvailability.setText(productModel.getProductStock());
                productPriceView.setText(productModel.getProductPrice());
                productDescriptionView.setText(productModel.getProductDescription());

                dialogPlus.show();
            }
        });


        myAllProductViewHolder.imageViewUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(myAllProductViewHolder.textViewProductName.getContext())
                        .setContentHolder(new ViewHolder(R.layout.admin_update_layout))
                        .setExpanded(true, 1900)
                        .create();


                View myViewForView = dialogPlus.getHolderView();

                ImageView productImageViewForUpdate = (ImageView) myViewForView.findViewById(R.id.adminUpdateImageView);
                TextView productNameUpdate = (TextView) myViewForView.findViewById(R.id.adminUpdateProductName);
                TextView productCategoryUpdate = (TextView) myViewForView.findViewById(R.id.adminUpdateProductCategory);
                TextView productSubCategoryUpdate = (TextView) myViewForView.findViewById(R.id.adminUpdateProductSubCategory);

               // EditText productStockAvailabilityUpdate = (EditText) myViewForView.findViewById(R.id.adminUpdateProductStockOROutOFStock);

//                RadioGroup radioGroup = (RadioGroup) myViewForView.findViewById(R.id.radioGroup);
//                RadioButton radioButtonInStock = (RadioButton) myViewForView.findViewById(R.id.radioButtonUpdateInStock);
//                RadioButton radioButtonOutOfStock = (RadioButton) myViewForView.findViewById(R.id.radioButtonUpdateOutOfStock);

//                int radioID = radioGroup.getCheckedRadioButtonId();
//                RadioButton radioButton = myViewForView.findViewById(radioID);

                EditText productPriceUpdate = (EditText) myViewForView.findViewById(R.id.adminUpdateProductPrice);
                EditText productDescriptionUpdate = (EditText) myViewForView.findViewById(R.id.adminUpdateProductDescription);


                Picasso.get().load(productModel.getProductImage()).into(productImageViewForUpdate);
                productNameUpdate.setText(productModel.getProductName());
                productCategoryUpdate.setText(productModel.getProductCategory());
                productSubCategoryUpdate.setText(productModel.getProductSubCategory());
                //productStockAvailabilityUpdate.setText(productModel.getProductStock());
                productPriceUpdate.setText(productModel.getProductPrice());
                productDescriptionUpdate.setText(productModel.getProductDescription());


                Button productUpdateButton = (Button) myViewForView.findViewById(R.id.updateProductBtn);
                productUpdateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        RadioGroup radioGroupUpdateData;
                        int radioIDUpdateData;
                        RadioButton radioButtonUpdateData;

                        radioGroupUpdateData = myViewForView.findViewById(R.id.radioGroupUpdate);
                        radioIDUpdateData = radioGroupUpdateData.getCheckedRadioButtonId();
                        radioButtonUpdateData = myViewForView.findViewById(radioIDUpdateData);

                        //final String updateProductAvailability = productStockAvailabilityUpdate.getText().toString();


                        final String updateProductPrice = productPriceUpdate.getText().toString();
                        final String updateProductAvailability = radioButtonUpdateData.getText().toString();
                        final String updateProductDescription = productDescriptionUpdate.getText().toString();



                        if (TextUtils.isEmpty(updateProductPrice)){
                            productPriceUpdate.requestFocus();
                            productPriceUpdate.setError("Set Price");

                        }
//                        else if (TextUtils.isEmpty(updateProductAvailability)){
//
//                            productStockAvailabilityUpdate.requestFocus();
//                            productStockAvailabilityUpdate.setError("Enter Stock Availability");
//                        }

                        //https://www.youtube.com/watch?v=fwSJ1OkK304
                        //https://www.youtube.com/watch?v=uGi40flFZE4



                        else if (TextUtils.isEmpty(updateProductDescription)){


                            productDescriptionUpdate.requestFocus();
                            productDescriptionUpdate.setError("Enter Description");
                        }
                        else {

                            HashMap updateProductData = new HashMap();

                            updateProductData.put("ProductPrice",updateProductPrice);
                            updateProductData.put("ProductStock",updateProductAvailability);
                            updateProductData.put("ProductDescription",updateProductDescription);




                            FirebaseDatabase.getInstance().getReference().child("Product")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child(getRef(position).getKey())
                                    .updateChildren(updateProductData)
                                    .addOnSuccessListener(new OnSuccessListener() {
                                        @Override
                                        public void onSuccess(Object o) {

                                            FirebaseDatabase.getInstance().getReference().child("AllProduct")
                                                    .child(getRef(position).getKey())
                                                    .updateChildren(updateProductData);
                                            dialogPlus.dismiss();


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialogPlus.dismiss();


                                }
                            });

                        }


                    }
                });





                dialogPlus.show();

            }
        });


        myAllProductViewHolder.imageViewDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(myAllProductViewHolder.textViewProductName.getContext());

                builder.setTitle("Delete Product");
                builder.setMessage("Delete.....?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        FirebaseDatabase.getInstance().getReference().child("Product")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(getRef(position).getKey()).removeValue();

                        FirebaseDatabase.getInstance().getReference().child("AllProduct")
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
    @Override
    public MyAllProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_post,parent, false);
        return new MyAllProductViewHolder(view);
    }

    public static class MyAllProductViewHolder extends RecyclerView.ViewHolder{

    TextView textViewProductName, textViewProductCategory, textViewProductSubCategory, textViewProductPrice;
    ImageView imageViewProductImage, imageViewViewProduct, imageViewUpdateProduct, imageViewDeleteProduct;

    public MyAllProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageViewProductImage = (ImageView) itemView.findViewById(R.id.adminImageShowPost);

        textViewProductCategory = (TextView) itemView.findViewById(R.id.adminCategoryShowPost);
        textViewProductName = (TextView) itemView.findViewById(R.id.adminNameShowPost);
        textViewProductSubCategory = (TextView) itemView.findViewById(R.id.adminSubCategoryShowPost);
        textViewProductPrice = (TextView) itemView.findViewById(R.id.adminPriceShowPost);

        imageViewViewProduct = (ImageView) itemView.findViewById(R.id.adminViewShowPost);
        imageViewUpdateProduct = (ImageView) itemView.findViewById(R.id.adminEditShowPost);
        imageViewDeleteProduct = (ImageView) itemView.findViewById(R.id.adminDeleteShowPost);


    }

}


}
