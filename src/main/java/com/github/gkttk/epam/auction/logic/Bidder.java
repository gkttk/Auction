package com.github.gkttk.epam.auction.logic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gkttk.epam.auction.model.Lot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Participant of auction
 */
public class Bidder implements Runnable {
    private final static Logger LOGGER = LogManager.getLogger(Bidder.class);
    private String name;
    //lot which this bidder wants to buy. Map with required lot and maximum price for this lot
    private Map<String, Double> requiredLots;
    private Semaphore semaphore;
    private Auction currentAuction;

    //for json
    public Bidder() {
    }

    public Bidder(String name, Map<String, Double> requiredLots) {
        this.name = name;
        this.requiredLots = requiredLots;
    }

    public void run() {
        //get current lot
        Lot currentLot = currentAuction.getCurrentLot();
        //get this bidder required lot
        Set<String> requiredLots = this.requiredLots.keySet();
        //is current lot required?
        String currentLotName = currentLot.getName();
        boolean isCurrentLotNeeded = requiredLots.stream().anyMatch(currentLotName::equals);
        //if current lot is required
        if (isCurrentLotNeeded) {
            //start while before ending of bidding
            while (currentAuction.isBiddingProcess()) {
                //check current purchaser of lot
                Bidder currentLotCustomer = currentLot.getCustomer();
                //if current purchaser of lot not this bidder...
                if (!this.equals(currentLotCustomer)) {
                    //get access from semaphore
                    try {
                        semaphore.acquire();
                        //if current lot is still not needed by anyone.
                        if (!currentLot.isNeeded()) {
                            //set isNeeded field of currentLot and set new customer
                            currentLot.setNeeded(true);
                            currentLot.setCustomer(this);
                            System.out.println("Purchaser was changed to " + this.name);
                        } else {      //if current lot is already needed by someone
                            //get current price
                            double currentPrice = currentLot.getCurrentPrice();
                            //get fair price of this bidder for current lot
                            Double fairPrice = this.requiredLots.get(currentLotName);
                            //if this bidder can still raise the bet than do it and set new purchaser
                            if (fairPrice >= currentPrice) {
                                raiseBet(currentLot);
                                System.out.println("Purchaser was changed to " + this.name);
                                currentLot.setCustomer(this);
                            }
                        }
                    } catch (InterruptedException exception) {
                        LOGGER.warn("Bidder thread was interrupted:{}\nException:{}", this.name, exception);
                    } finally {
                        //give away permit
                        semaphore.release();
                    }
                }
            }
        }
    }


    private void raiseBet(Lot currentLot) {
        String currentLotName = currentLot.getName();
        double currentPrice = currentLot.getCurrentPrice();
        Double fairPrice = this.requiredLots.get(currentLotName);
        double raisedPrice = currentPrice * Auction.getPriceCoefficient();
        if (raisedPrice <= fairPrice) {
            currentLot.setCurrentPrice(raisedPrice);
            System.out.println(String.format("Current price was changed to %.2f", raisedPrice));
        } else {
            currentLot.setCurrentPrice(fairPrice);
            System.out.println(String.format("Current price was changed to %.2f", raisedPrice));
        }
    }

    public void setCurrentAuction(Auction currentAuction) {
        this.currentAuction = currentAuction;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("required_lots")
    public void setRequiredLots(Map<String, Double> requiredLots) {
        this.requiredLots = requiredLots;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bidder bidder = (Bidder) o;
        return Objects.equals(name, bidder.name) &&
                Objects.equals(requiredLots, bidder.requiredLots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, requiredLots);
    }

    @Override
    public String toString() {
        return "Bidder: " + "name = " + name + " requiredLots : " + requiredLots;
    }
}
