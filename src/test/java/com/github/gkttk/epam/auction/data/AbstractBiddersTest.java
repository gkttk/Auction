package com.github.gkttk.epam.auction.data;

import com.github.gkttk.epam.auction.data.exception.DataParserException;
import com.github.gkttk.epam.auction.logic.observer.Bidder;
import com.github.gkttk.epam.auction.model.Lot;
import com.github.gkttk.epam.auction.model.wrapper.Bidders;
import com.github.gkttk.epam.auction.model.wrapper.Lots;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBiddersTest {

    protected String correctFileLocation = getCorrectFileLocation();
    protected final static String FILE_NOT_EXIST_LOCATION = "";
    protected final static String INCORRECT_JSON_LOCATION = "src/test/resources/incorrect_json";

    protected static Bidders testBidders;
    protected static Lots testLots;


    @BeforeAll
    static void init() {
        //init testLots
        Lot firstLot = new Lot(1, "Picture", 891);
        Lot secondLot = new Lot(2, "Clothespin", 50);
        Lot thirdLot = new Lot(3, "Statuette", 210);
        Lot fourthLot = new Lot(4, "Gobelin", 452);
        Lot fifthLot = new Lot(5, "Watch", 623);
        Lot sixthLot = new Lot(6, "I am not needed for anybody :C", 10000);
        List<Lot> lotsList = Arrays.asList(firstLot, secondLot, thirdLot, fourthLot, fifthLot, sixthLot);
        testLots = new Lots(lotsList);

        //init testBidders
        Map<String, Double> requiredLots1 = new HashMap<>();
        requiredLots1.put(firstLot.getName(), 1300d);
        requiredLots1.put(fourthLot.getName(), 620d);

        Map<String, Double> requiredLots2 = new HashMap<>();
        requiredLots2.put(secondLot.getName(), 210d);
        requiredLots2.put(firstLot.getName(), 1310d);

        Map<String, Double> requiredLots3 = new HashMap<>();
        requiredLots3.put(fourthLot.getName(), 550d);
        requiredLots3.put(thirdLot.getName(), 211d);

        Map<String, Double> requiredLots4 = new HashMap<>();
        requiredLots4.put(firstLot.getName(), 1500d);
        requiredLots4.put(fifthLot.getName(), 620d);

        Map<String, Double> requiredLots5 = new HashMap<>();
        requiredLots5.put(fifthLot.getName(), 800d);
        requiredLots5.put(secondLot.getName(), 150d);

        List<Bidder> biddersList = Arrays.asList(
                new Bidder("Bidder1", requiredLots1),
                new Bidder("Bidder2", requiredLots2),
                new Bidder("Bidder3", requiredLots3),
                new Bidder("Bidder4", requiredLots4),
                new Bidder("Bidder5", requiredLots5)
        );

        testBidders = new Bidders(biddersList);
    }

    @Test
    public void testParseShouldThrowExceptionWhenFileIsNotFound() {
        //given
        //when
        //then
        Assertions.assertThrows(DataParserException.class, () -> createDataParser().parse(FILE_NOT_EXIST_LOCATION));
    }

    @Test
    public void testParseShouldThrowExceptionWhenJsonIsNotCorrect() {
        //given
        //when
        //then
        Assertions.assertThrows(DataParserException.class, () -> createDataParser().parse(INCORRECT_JSON_LOCATION));
    }


    protected abstract String getCorrectFileLocation();

    protected abstract DataParser createDataParser();

}
