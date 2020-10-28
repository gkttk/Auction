package com.github.gkttk.epam.auction.logic.observable;

import com.github.gkttk.epam.auction.logic.observer.Observer;

public interface Observable {

    void addObserver(Observer observer);

}
