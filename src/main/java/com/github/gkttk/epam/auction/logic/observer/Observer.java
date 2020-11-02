package com.github.gkttk.epam.auction.logic.observer;

import com.github.gkttk.epam.auction.model.Lot;

public interface Observer {

    void notifyObserver(Lot lot);

}
