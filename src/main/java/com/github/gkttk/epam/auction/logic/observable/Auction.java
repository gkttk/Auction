package com.github.gkttk.epam.auction.logic.observable;

import com.github.gkttk.epam.auction.data.LotsDataParser;
import com.github.gkttk.epam.auction.data.exception.DataParserException;
import com.github.gkttk.epam.auction.logic.exception.AuctionException;
import com.github.gkttk.epam.auction.logic.observer.Bidder;
import com.github.gkttk.epam.auction.logic.observer.Observer;
import com.github.gkttk.epam.auction.model.Lot;
import com.github.gkttk.epam.auction.model.wrapper.Lots;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Auction class with static price coefficient(5%) and trading session time(5 seconds)
 * List of lot injects in constructor.
 */
public class Auction implements Observable {

    private final List<Observer> bidders;
    private final List<Lot> auctionLots;

    private final static String LOTS_FILE_LOCATION = "files/lot";

    //trading session time(5 seconds)
    private final static long TRADING_SESSION_TIME = 5;
    //coefficient of lot price increasing(5%)
    private final static double PRICE_COEFFICIENT = 1.05;
    //indicator which showing that process of trading is going on at the moment
    private boolean isBiddingProcess;


    private Auction() {
        try {
            LotsDataParser lotsDataParser = new LotsDataParser();
            Lots lotsWrapper = lotsDataParser.parse(LOTS_FILE_LOCATION);
            this.auctionLots = lotsWrapper.getLots();
            this.bidders = new ArrayList<>();
        } catch (DataParserException exception) {
            throw new IllegalStateException("Can't initialize the private Auction constructor, parse problem", exception);
        }
    }

    private static class AuctionHolder {
        private static final Auction INSTANCE = new Auction();
    }

    public static Auction getInstance() {
        return AuctionHolder.INSTANCE;
    }


    @Override
    public void addObserver(Observer bidder) {
        this.bidders.add(bidder);
    }


    public void startBidding() throws AuctionException {
        try {
            System.out.println("Start the bidding...");
            this.isBiddingProcess = true;
            TimeUnit.SECONDS.sleep(TRADING_SESSION_TIME);
            this.isBiddingProcess = false;
            System.out.println("Finish the bidding");
        } catch (InterruptedException exception) {
            throw new AuctionException("The process of bidding was interrupted", exception);
        }
    }


    /**
     * Method gives to bidders copy of auction lots
     */

    public List<Lot> getLotsInfo() {
        return new ArrayList<>(this.auctionLots);
    }


    public void updateLot(Lot updatedLot) {
        String updatedLotName = updatedLot.getName();
        for (Lot lot : auctionLots) {
            String lotName = lot.getName();
            if (lotName.equals(updatedLotName)) {
                Bidder newCustomer = updatedLot.getCustomer();
                double newPrice = updatedLot.getCurrentPrice();

                lot.setCustomer(newCustomer);
                lot.setCurrentPrice(newPrice);
                System.out.println(String.format("Purchaser was changed to %s", newCustomer));
                System.out.println(String.format("Current price was changed to %.2f", newPrice));
                break;
            }
        }
        notifyObservers(updatedLot);
    }


    private void notifyObservers(Lot lot) {
        bidders.forEach(observer -> observer.notifyObserver(lot));
    }


    public static double getPriceCoefficient() {
        return PRICE_COEFFICIENT;
    }

    public boolean isBiddingProcess() {
        return isBiddingProcess;
    }

    public List<Lot> getAuctionLots() {
        return auctionLots;
    }

}



