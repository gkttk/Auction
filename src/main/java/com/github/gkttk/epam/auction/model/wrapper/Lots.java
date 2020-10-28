package com.github.gkttk.epam.auction.model.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gkttk.epam.auction.model.Lot;

import java.util.List;
import java.util.Objects;

/**
 * Wrapper over lot for the correct JSON
 */
public class Lots {
    private List<Lot> lots;


    @JsonCreator
    public Lots(@JsonProperty("lots") List<Lot> lots) {
        this.lots = lots;
    }


    public List<Lot> getLots() {
        return lots;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lots lots1 = (Lots) o;
        return Objects.equals(lots, lots1.lots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lots);
    }

    @Override
    public String toString() {
        return lots.toString();
    }
}
