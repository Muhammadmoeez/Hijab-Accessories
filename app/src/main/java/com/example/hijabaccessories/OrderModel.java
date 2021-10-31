package com.example.hijabaccessories;

public class OrderModel {


    //String OrderAdminNumber;
    String OrderAdminID;
    String OrderProductCategory;
    String OrderProductCode;
    String OrderProductDescription;
    String OrderProductID;
    String OrderProductImage;
    String OrderProductName;
    String OrderProductPrice;
    String OrderProductStockAvailability;
    String OrderProductSubCategory;
    String OrderProductMemberName;
    String OrderProductItemNumber;
    String OrderProductMemberNumber;
    String OrderProductFullBill;

    public OrderModel() {
    }

    public OrderModel(String orderAdminID, String orderProductCategory, String orderProductCode, String orderProductDescription, String orderProductID, String orderProductImage, String orderProductName, String orderProductPrice, String orderProductStockAvailability, String orderProductSubCategory, String orderProductMemberName, String orderProductItemNumber, String orderProductMemberNumber, String  orderProductFullBill) {
        OrderAdminID = orderAdminID;
        OrderProductCategory = orderProductCategory;
        OrderProductCode = orderProductCode;
        OrderProductDescription = orderProductDescription;
        OrderProductID = orderProductID;
        OrderProductImage = orderProductImage;
        OrderProductName = orderProductName;
        OrderProductPrice = orderProductPrice;
        OrderProductStockAvailability = orderProductStockAvailability;
        OrderProductSubCategory = orderProductSubCategory;
        OrderProductMemberName = orderProductMemberName;
        OrderProductItemNumber = orderProductItemNumber;
        OrderProductMemberNumber = orderProductMemberNumber;
        OrderProductFullBill = orderProductFullBill;
    }

    public String getOrderAdminID() {
        return OrderAdminID;
    }

    public void setOrderAdminID(String orderAdminID) {
        OrderAdminID = orderAdminID;
    }

    public String getOrderProductCategory() {
        return OrderProductCategory;
    }

    public void setOrderProductCategory(String orderProductCategory) {
        OrderProductCategory = orderProductCategory;
    }

    public String getOrderProductCode() {
        return OrderProductCode;
    }

    public void setOrderProductCode(String orderProductCode) {
        OrderProductCode = orderProductCode;
    }

    public String getOrderProductDescription() {
        return OrderProductDescription;
    }

    public void setOrderProductDescription(String orderProductDescription) {
        OrderProductDescription = orderProductDescription;
    }

    public String getOrderProductID() {
        return OrderProductID;
    }

    public void setOrderProductID(String orderProductID) {
        OrderProductID = orderProductID;
    }

    public String getOrderProductImage() {
        return OrderProductImage;
    }

    public void setOrderProductImage(String orderProductImage) {
        OrderProductImage = orderProductImage;
    }

    public String getOrderProductName() {
        return OrderProductName;
    }

    public void setOrderProductName(String orderProductName) {
        OrderProductName = orderProductName;
    }

    public String getOrderProductPrice() {
        return OrderProductPrice;
    }

    public void setOrderProductPrice(String orderProductPrice) {
        OrderProductPrice = orderProductPrice;
    }

    public String getOrderProductStockAvailability() {
        return OrderProductStockAvailability;
    }

    public void setOrderProductStockAvailability(String orderProductStockAvailability) {
        OrderProductStockAvailability = orderProductStockAvailability;
    }

    public String getOrderProductSubCategory() {
        return OrderProductSubCategory;
    }

    public void setOrderProductSubCategory(String orderProductSubCategory) {
        OrderProductSubCategory = orderProductSubCategory;
    }

    public String getOrderProductMemberName() {
        return OrderProductMemberName;
    }

    public void setOrderProductMemberName(String orderProductMemberName) {
        OrderProductMemberName = orderProductMemberName;
    }

    public String getOrderProductItemNumber() {
        return OrderProductItemNumber;
    }

    public void setOrderProductItemNumber(String orderProductItemNumber) {
        OrderProductItemNumber = orderProductItemNumber;
    }

    public String getOrderProductMemberNumber() {
        return OrderProductMemberNumber;
    }

    public void setOrderProductMemberNumber(String orderProductMemberNumber) {
        OrderProductMemberNumber = orderProductMemberNumber;
    }

    public String getOrderProductFullBill() {
        return OrderProductFullBill;
    }

    public void setOrderProductFullBill(String orderProductFullBill) {
        OrderProductFullBill = orderProductFullBill;
    }
}
