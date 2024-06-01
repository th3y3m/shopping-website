/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Entity;

/**
 *
 * @author admin
 */
public class Product {

    private String productId;
    private String productName;
    private String supplierId;
    private String CategoryId;
    private int quantityPerUnit;
    private double unitPrice;
    private String productImage;

    public Product() {
    }

    public Product(String productId, String productName, String supplierId, String CategoryId, int quantityPerUnit, double unitPrice, String productImage) {
        this.productId = productId;
        this.productName = productName;
        this.supplierId = supplierId;
        this.CategoryId = CategoryId;
        this.quantityPerUnit = quantityPerUnit;
        this.unitPrice = unitPrice;
        this.productImage = productImage;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String CategoryId) {
        this.CategoryId = CategoryId;
    }

    public int getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public void setQuantityPerUnit(int quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", productName=" + productName + ", supplierId=" + supplierId + ", CategoryId=" + CategoryId + ", quantityPerUnit=" + quantityPerUnit + ", unitPrice=" + unitPrice + ", productImage=" + productImage + '}';
    }

}
