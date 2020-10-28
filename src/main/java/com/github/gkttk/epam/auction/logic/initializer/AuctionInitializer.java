package com.github.gkttk.epam.auction.logic.initializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gkttk.epam.auction.logic.Auction;
import com.github.gkttk.epam.auction.logic.initializer.exception.InitializerException;
import com.github.gkttk.epam.auction.model.Lot;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AuctionInitializer {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final static File LOT_FILE = new File("files/lots.txt");

  /*  public Auction createAuction() throws InitializerException {
        List<Lot> lots = null;
        try {
            //чтение лотов
            lots = OBJECT_MAPPER.readValue(LOT_FILE,
                    new TypeReference<List<Lot>>() {
                    });
        } catch (IOException exception) {
            throw new InitializerException("Can't initialize auction", exception);
        }

        return new Auction(lots);


    }*/

}
