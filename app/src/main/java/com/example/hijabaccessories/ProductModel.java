package com.example.hijabaccessories;

public class ProductModel {

    String AdminNumber;
    String AdminId;
    String ProductCategory;
    String ProductCode;
    String ProductDescription;
    String ProductID;
    String ProductImage;
    String ProductName;
    String ProductPrice;
    String ProductStock;
    String ProductSubCategory;

    public ProductModel() {
    }

    public ProductModel(String adminNumber, String adminId, String productCategory, String productCode, String productDescription, String productID, String productImage, String productName, String productPrice, String productStock, String productSubCategory) {
        AdminNumber = adminNumber;
        AdminId = adminId;
        ProductCategory = productCategory;
        ProductCode = productCode;
        ProductDescription = productDescription;
        ProductID = productID;
        ProductImage = productImage;
        ProductName = productName;
        ProductPrice = productPrice;
        ProductStock = productStock;
        ProductSubCategory = productSubCategory;
    }


    public String getAdminNumber() {
        return AdminNumber;
    }

    public void setAdminNumber(String adminNumber) {
        AdminNumber = adminNumber;
    }

    public String getAdminId() {
        return AdminId;
    }

    public void setAdminId(String adminId) {
        AdminId = adminId;
    }

    public String getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(String productCategory) {
        ProductCategory = productCategory;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductStock() {
        return ProductStock;
    }

    public void setProductStock(String productStock) {
        ProductStock = productStock;
    }

    public String getProductSubCategory() {
        return ProductSubCategory;
    }

    public void setProductSubCategory(String productSubCategory) {
        ProductSubCategory = productSubCategory;
    }
}