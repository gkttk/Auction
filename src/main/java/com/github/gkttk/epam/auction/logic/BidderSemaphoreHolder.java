package com.github.gkttk.epam.auction.logic;

import java.util.concurrent.Semaphore;

/**
 * Common semaphore for bidders.
 */
public class BidderSemaphoreHolder {

    private final static Semaphore SEMAPHORE = new Semaphore(1);

    public static Semaphore getSEMAPHORE() {
        return SEMAPHORE;
    }
}
