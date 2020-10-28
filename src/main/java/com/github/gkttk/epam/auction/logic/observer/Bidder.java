package com.github.gkttk.epam.auction.logic.observer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gkttk.epam.auction.logic.BidderSemaphoreHolder;
import com.github.gkttk.epam.auction.logic.observable.Auction;
import com.github.gkttk.epam.auction.model.Lot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Participant of auction
 */
public class Bidder implements Runnable, Observer {

    private List<Lot> requiredLotsList = new ArrayList<>();

    private final static Logger LOGGER = LogManager.getLogger(Bidder.class);
    private String name;
    //lot which this bidder wants to buy. Map with required lot and maximum price for this lot
    private Map<String, Double> requiredLots;
    private Semaphore semaphore;
    private Auction auction;


    @JsonCreator
    public Bidder(@JsonProperty("name") String name,
                  @JsonProperty("required_lots") Map<String, Double> requiredLots) {
        this.auction = Auction.getInstance();
        this.semaphore = BidderSemaphoreHolder.getSEMAPHORE();
        this.name = name;
        this.requiredLots = requiredLots;
        //register
        auction.addObserver(this);
    }

    /**
     * This method selects from auction items only needed items and adds them to requiredLotsList.
     */
    private void initRequiredLotsList(List<Lot> auctionLots) {
        Set<String> requiredLotNames = requiredLots.keySet();
        for (Lot lot : auctionLots) {
            String lotName = lot.getName();
            boolean isCurrentLotNeeded = requiredLotNames
                    .stream()
                    .anyMatch(requiredLotName -> requiredLotName.equals(lotName));
            if (isCurrentLotNeeded) {
                this.requiredLotsList.add(lot);
            }
        }
    }


    private void waitForStartAuction() {
        while (!auction.isBiddingProcess()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException exception) {
                LOGGER.error("Waiting for the auction  starting was interrupted\n", exception);
            }
        }
    }

    public void run() {
        //get all auction lots
        List<Lot> auctionLots = this.auction.getLotsInfo();
        //init requiredLotsList from auction lots
        initRequiredLotsList(auctionLots);
        //waiting for start of bidding process
        waitForStartAuction();
        //bidding process for all required lots
        while (auction.isBiddingProcess()) {
            for (Lot currentLot : requiredLotsList) {
                String name = currentLot.getName();
                double fairPrice = requiredLots.get(name);
                if (currentLot.getCustomer() != this && currentLot.getCurrentPrice() <= fairPrice) {
                    try {
                        //get access from semaphore
                        this.semaphore.acquire();
                        doBet(currentLot);
                    } catch (InterruptedException exception) {
                        LOGGER.warn("Bidder thread was interrupted:{}\nException:{}", this.name, exception);
                    } finally {
                        semaphore.release();
                    }
                }
            }
        }
    }


    private void doBet(Lot currentLot) {
        double newPriceForLot = getNewPriceForLot(currentLot);

        currentLot.setCustomer(this);
        currentLot.setCurrentPrice(newPriceForLot);
        auction.updateLot(currentLot);

    }

    /**
     * This method decides which cost will be set for lot.
     */
    private double getNewPriceForLot(Lot currentLot) {
        String currentLotName = currentLot.getName();
        double currentPrice = currentLot.getCurrentPrice();
        Double fairPrice = this.requiredLots.get(currentLotName);
        double raisedPrice = currentPrice * Auction.getPriceCoefficient();

        return raisedPrice <= fairPrice ? raisedPrice : fairPrice;
    }

    @Override
    public void notifyObserver(Lot updatedLot) {
        String updatedLotName = updatedLot.getName();

        for (Lot currentLot : requiredLotsList) {
            String currentLotName = currentLot.getName();
            if (currentLotName.equals(updatedLotName)) {
                Bidder customer = updatedLot.getCustomer();
                double currentPrice = updatedLot.getCurrentPrice();

                currentLot.setCustomer(customer);
                currentLot.setCurrentPrice(currentPrice);
                break;
            }
        }
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
        return name + ", RequiredLots : " + requiredLots;
    }


}
