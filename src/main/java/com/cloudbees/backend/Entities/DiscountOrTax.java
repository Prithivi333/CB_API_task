package com.cloudbees.backend.Entities;
import com.cloudbees.backend.Entities.DRT;

public class DiscountOrTax {

    private Long productID;
    private Double value;
    private DRT drt;

    public DiscountOrTax() {
    }

    public DiscountOrTax(Long productID, Double value, DRT drt) {
        this.productID = productID;
        this.value = value;
        this.drt = drt;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public DRT getDrt() {
        return drt;
    }

    public void setDrt(DRT drt) {
        this.drt = drt;
    }

    @Override
    public String toString() {
        return "DiscountOrTax{" +
                "productID=" + productID +
                ", value=" + value +
                ", drt=" + drt +
                '}';
    }
}
