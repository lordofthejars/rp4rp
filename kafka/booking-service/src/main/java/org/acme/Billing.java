package org.acme;

public class Billing {

    public Long customerId;
    public Double price;

    public Billing(Long customerId, Double price) {
        this.customerId = customerId;
        this.price = price;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Double getPrice() {
        return price;
    }

}