package com.example.hijabaccessories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ShowFullPostForOrder extends AppCompatActivity {


    ImageView fullProductImage;
    TextView fullProductName, fullProductPrice, fullProductCategory, fullProductSubCategory, fullProductStockAvailability, fullProductDescription;

    int totalPrice;

    String tempAdminNumber, tempAdminID, tempProductCategory, tempProductCode,tempProductDescription, tempProductID,
            tempProductImage, tempProductName, tempProductPrice,tempProductStockAvailability,
            tempProductSubCategory  ;


    String myOrderMemberName, myOrderItemNumbers, myOrderMemberNumber;

    EditText orderNumberOfItems, orderMemberName, orderMemberNumber;
    Button orderConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_post_for_order);

        orderMemberName = (EditText) findViewById(R.id.productOrderMemberName);
        orderNumberOfItems = (EditText) findViewById(R.id.productOrderItemNumbers);
        orderMemberNumber = (EditText) findViewById(R.id.productOrderMemberNumber);
        orderConfirm = (Button) findViewById(R.id.productOrderBtn);



        Intent intent = getIntent();


        tempAdminNumber=intent.getStringExtra("AdminNumber");
        tempAdminID=intent.getStringExtra("AdminID");
        tempProductCategory =intent.getStringExtra("ProductCategory");
        tempProductCode=intent.getStringExtra("ProductCode");
        tempProductDescription =intent.getStringExtra("ProductDescription");
        tempProductID=intent.getStringExtra("ProductID");
        tempProductImage =intent.getStringExtra("ProductImage");
        tempProductName =intent.getStringExtra("ProductName");
        tempProductPrice =intent.getStringExtra("ProductPrice");
        tempProductStockAvailability =intent.getStringExtra("ProductAvailability");
        tempProductSubCategory =intent.getStringExtra("ProductSubCategory");










        fullProductImage = (ImageView) findViewById(R.id.showFullImageView);
        fullProductName = (TextView) findViewById(R.id.showFullProductName);
        fullProductPrice = (TextView) findViewById(R.id.showFullProductPrice);
        fullProductCategory = (TextView) findViewById(R.id.showFullProductCategory);
        fullProductSubCategory = (TextView) findViewById(R.id.showFullProductSubCategory);
        fullProductStockAvailability = (TextView) findViewById(R.id.showFullProductAvailability);
        fullProductDescription = (TextView) findViewById(R.id.showFullProductDescription);


        Picasso.get().load(tempProductImage).into(fullProductImage);
        fullProductName.setText(tempProductName);
        fullProductPrice.setText(tempProductPrice);
        fullProductCategory.setText(tempProductCategory);
        fullProductSubCategory.setText(tempProductSubCategory);
        fullProductStockAvailability.setText(tempProductStockAvailability);
        fullProductDescription.setText(tempProductDescription);





        orderConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendSMSOnWhatsApp();
                insertOrderData();


            }
        });

    }

    private void sendSMSOnWhatsApp() {
        myOrderMemberName = orderMemberName.getText().toString();
        myOrderItemNumbers = orderNumberOfItems.getText().toString();
        myOrderMemberNumber = orderMemberNumber.getText().toString();

        if (TextUtils.isEmpty(myOrderMemberName)){
            orderMemberName.requestFocus();
            orderMemberName.setError("Enter Your Name");
        }
        else if (TextUtils.isEmpty(myOrderItemNumbers)){
            orderNumberOfItems.requestFocus();
            orderNumberOfItems.setError("Number of Items");
        }
        else if (TextUtils.isEmpty(myOrderMemberNumber)){
            orderMemberNumber.requestFocus();
            orderMemberNumber.setError("Enter Name");
        }
        else if (!myOrderMemberNumber.matches("\\+[0-9]{10,13}$")){
            orderMemberNumber.requestFocus();
            orderMemberNumber.setError("Enter Name");
        }
        else {



            totalPrice = Integer.parseInt(tempProductPrice) * Integer.parseInt(myOrderItemNumbers);


            String message = "Product Code: " +tempProductCode +
                    "\nProduct Name: "+tempProductName +
                    "\nProduct Category: " +tempProductCategory +
                    "\nProduct Sub-Category: "+tempProductSubCategory+
                    "\nProduct Price: " + tempProductPrice +
                    "\nCustomer Name: " + myOrderMemberName+
                    "\nItem Quantity: " + myOrderItemNumbers +
                    "\nTotal Amount: "+ totalPrice;

            boolean installed = appInstalledOrNot("com.whatsapp");

            if(installed)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com//send?phone="+tempAdminNumber+"&text="+message));
                startActivity(intent);




            }
            else
            {
                Toast.makeText(ShowFullPostForOrder.this, "Whats-App is not Installed", Toast.LENGTH_SHORT).show();

            }



        }
    }

    private void insertOrderData() {

        String totalOrderPrice = String.valueOf(totalPrice);







        HashMap insertOrder = new HashMap();
        insertOrder.put("OrderAdminNumber",tempAdminNumber);
        insertOrder.put("OrderAdminID",tempAdminID);
        insertOrder.put("OrderProductCategory",tempProductCategory);
        insertOrder.put("OrderProductCode",tempProductCode);
        insertOrder.put("OrderProductDescription",tempProductDescription);
        insertOrder.put("OrderProductID",tempProductID);
        insertOrder.put("OrderProductImage",tempProductImage);
        insertOrder.put("OrderProductName",tempProductName);
        insertOrder.put("OrderProductPrice",tempProductPrice);
        insertOrder.put("OrderProductStockAvailability",tempProductStockAvailability);
        insertOrder.put("OrderProductSubCategory",tempProductSubCategory);
        insertOrder.put("OrderProductMemberName",myOrderMemberName);
        insertOrder.put("OrderProductItemNumber",myOrderItemNumbers);
        insertOrder.put("OrderProductMemberNumber",myOrderMemberNumber);
        insertOrder.put("OrderProductFullBill",totalOrderPrice);

        FirebaseDatabase.getInstance().getReference()
                .child("Order")
                .push()
                .setValue(insertOrder)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ShowFullPostForOrder.this, "Order Please", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            Toast.makeText(ShowFullPostForOrder.this, "FD"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });



    }

    private boolean appInstalledOrNot(String url) {

        PackageManager packageManager = getPackageManager();
        Boolean app_installed;
        try {

            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch(PackageManager.NameNotFoundException e)
        {
            app_installed = false;
        }
        return app_installed;
    }

}