package com.github.gkttk.epam.auction.logic;


import com.github.gkttk.epam.auction.logic.exception.AuctionException;
import com.github.gkttk.epam.auction.model.Lot;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Auction class with static price coefficient(5%) and trading session time(5 seconds)
 * List of lots injects in constructor.
 */
public class Auction {

    private static volatile Auction instance;
    //trading session time(5 seconds)
    private final static long TRADING_SESSION_TIME = 5;
    //coefficient of lot price increasing(5%)
    private final static double PRICE_COEFFICIENT = 1.05;
    //current auction lot(for bidders)
    private Lot currentLot;
    //thread pull(for bidders)
    private ExecutorService executorService;
    //indicator which showing that process of trading is going on at the moment
    private boolean isBiddingProcess;

    private Auction() {
        this.isBiddingProcess = false;
    }

    public static Auction getInstance() {
        Auction localInstance = instance;
        if (localInstance == null) {
            synchronized (Auction.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = new Auction();
                }
            }
        }
        return instance;
    }


    /**
     * @param bidders Auction participants.
     */
    public void startBidding(List<Lot> lots, List<Bidder> bidders) throws AuctionException {
        try {
            //initialisation of thread pool
            initBiddersThreadPool(bidders);
            //lots in turns
            for (Lot currentLot : lots) {
                this.currentLot = currentLot;
                //start of bidding for current lot
                this.isBiddingProcess = true;
                System.out.println("Start bidding for lot:\n" + currentLot);
                //trading for current lot
                bidd(bidders);
                System.out.println("Finish trading for lot:\n" + currentLot);
                if (currentLot.isNeeded()) {
                    System.out.println("Purchaser is :" + currentLot.getCustomer());
                } else {
                    System.out.println("Nobody wants to buy current lot");
                }
                System.out.println("--------------------------------------------");

            }
        } catch (InterruptedException exception) {
            throw new AuctionException("The process of bidding was interrupted", exception);
        } finally {
            if (this.executorService != null) {
                this.executorService.shutdown();
            }
        }
    }

    /**
     * This method initialize a pool of threads.
     *
     * @param bidders - Threads.
     */
    private void initBiddersThreadPool(List<Bidder> bidders) {
        int bidderNumber = bidders.size();
        this.executorService = Executors.newFixedThreadPool(bidderNumber);
    }

    /**
     * This method starts all threads in thread pool, then sleep some time(depends on TRADING_SESSION_TIME),
     * and then ends the bidding process;
     *
     * @param bidders Auction participants.
     */
    private void bidd(List<Bidder> bidders) throws InterruptedException {
        for (Bidder bidder : bidders) {
            executorService.execute(bidder);
        }
        TimeUnit.SECONDS.sleep(TRADING_SESSION_TIME);
        this.isBiddingProcess = false;
    }


    public static double getPriceCoefficient() {
        return PRICE_COEFFICIENT;
    }

    public boolean isBiddingProcess() {
        return isBiddingProcess;
    }

    public Lot getCurrentLot() {
        return currentLot;
    }


}
