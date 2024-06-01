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
public class Supplier {

    private String supplierId;
    private String companyName;
    private String address;
    private String phone;

    public Supplier() {
    }

    public Supplier(String supplierId, String companyName, String address, String phone) {
        this.supplierId = supplierId;
        this.companyName = companyName;
        this.address = address;
        this.phone = phone;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Supplier{" + "supplierId=" + supplierId + ", companyName=" + companyName + ", address=" + address + ", phone=" + phone + '}';
    }

}
