package com.github.gkttk.epam.auction.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gkttk.epam.auction.logic.observer.Bidder;

import java.util.Objects;

/**
 * This class represents an auction lot
 */
public class Lot {

    private int id;
    private String name;
    //this price is changing if some bidder wants to raise bet
    private double currentPrice;
    private Bidder customer;


    @JsonCreator
    public Lot(@JsonProperty("id") int id,
               @JsonProperty("name") String name,
               @JsonProperty("currentPrice") double currentPrice) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
    }


    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Bidder getCustomer() {
        return customer;
    }

    public void setCustomer(Bidder customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lot lot = (Lot) o;
        return id == lot.id &&
                Objects.equals(name, lot.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


    @Override
    public String toString() {
        return String.format("Lot: id = %d, name = %s, currentPrice = %.2f", id, name, currentPrice);
    }
}
