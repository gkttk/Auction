package com.github.gkttk.epam.auction.data;

import com.github.gkttk.epam.auction.data.exception.DataParserException;
import com.github.gkttk.epam.auction.logic.Bidder;
import com.github.gkttk.epam.auction.model.wrapper.Bidders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BiddersDataParserTest extends AbstractBiddersTest{


    @Test
    public void testParseShouldParseFileWhenFileIsCorrect() throws DataParserException {
        //given
        int expectedSize = 5;
        //when
        Bidders bidders = (Bidders)dataParser.parse(correctFileLocation);
        //then
        List<Bidder> bidderList = bidders.getBidders();
        Assertions.assertEquals(expectedSize, bidderList.size());
        Assertions.assertEquals(bidders, testBidders);
    }


    @Override
    protected String getCorrectFileLocation() {
        return "src/test/resources/bidders";
    }

    @Override
    protected DataParser createDataParser() {
        return new BiddersDataParser();
    }
}
