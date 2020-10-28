package com.github.gkttk.epam.auction.model.wrapper;

import com.github.gkttk.epam.auction.logic.Bidder;

import java.util.List;
import java.util.Objects;

/**
 * Wrapper over bidders for the correct JSON
 */
public class Bidders {

    private List<Bidder> bidders;

    //for json
    public Bidders() {
    }

    public Bidders(List<Bidder> bidders) {
        this.bidders = bidders;
    }


    public List<Bidder> getBidders() {
        return bidders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bidders bidders1 = (Bidders) o;
        return Objects.equals(bidders, bidders1.bidders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bidders);
    }

    @Override
    public String toString() {
        return bidders.toString();
    }
}