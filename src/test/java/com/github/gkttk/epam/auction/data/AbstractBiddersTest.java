package com.github.gkttk.epam.auction.data;

import com.github.gkttk.epam.auction.data.exception.DataParserException;
import com.github.gkttk.epam.auction.logic.Bidder;
import com.github.gkttk.epam.auction.model.Lot;
import com.github.gkttk.epam.auction.model.wrapper.Bidders;
import com.github.gkttk.epam.auction.model.wrapper.Lots;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractBiddersTest {

    protected DataParser dataParser = createDataParser();
    protected String correctFileLocation = getCorrectFileLocation();
    protected final static String FILE_NOT_EXIST_LOCATION = "";
    protected final static String INCORRECT_JSON_LOCATION = "src/test/resources/incorrect_json";

    protected static Bidders testBidders;
    protected static Lots testLots;


    @BeforeAll
    static void init(){
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
        Map<String, Double> requiredLots1 = Stream.of(new Object[][]{
                {firstLot.getName(), 1300d},
                {fourthLot.getName(), 620d},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));

        Map<String, Double> requiredLots2 = Stream.of(new Object[][]{
                {secondLot.getName(), 210d},
                {firstLot.getName(), 1310d},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));

        Map<String, Double> requiredLots3 = Stream.of(new Object[][]{
                {fourthLot.getName(), 550d},
                {thirdLot.getName(), 211d},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));

        Map<String, Double> requiredLots4 = Stream.of(new Object[][]{
                {firstLot.getName(), 1500d},
                {fifthLot.getName(), 620d},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));

        Map<String, Double> requiredLots5 = Stream.of(new Object[][]{
                {fifthLot.getName(), 800d},
                {secondLot.getName(), 150d},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));

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
    public void testParseShouldThrowExceptionWhenFileIsNotFound(){
        //given
        //when
        //then
        Assertions.assertThrows(DataParserException.class, ()-> dataParser.parse(FILE_NOT_EXIST_LOCATION));
    }

    @Test
    public void testParseShouldThrowExceptionWhenJsonIsNotCorrect(){
        //given
        //when
        //then
        Assertions.assertThrows(DataParserException.class, ()-> dataParser.parse(INCORRECT_JSON_LOCATION));
    }


    protected abstract String getCorrectFileLocation();
    protected abstract DataParser createDataParser();

}
