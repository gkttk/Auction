package com.github.gkttk.epam.auction.data;

import com.github.gkttk.epam.auction.data.exception.DataParserException;
import com.github.gkttk.epam.auction.logic.Bidder;
import com.github.gkttk.epam.auction.model.Lot;
import com.github.gkttk.epam.auction.model.wrapper.Bidders;
import com.github.gkttk.epam.auction.model.wrapper.Lots;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LotsDataParserTest extends AbstractBiddersTest{


    @Test
    public void testParseShouldParseFileWhenFileIsCorrect() throws DataParserException {
        //given
        int expectedSize = 6;
        //when
        Lots lots = (Lots)dataParser.parse(correctFileLocation);
        //then
        List<Lot> lotsList = lots.getLots();
        Assertions.assertEquals(expectedSize, lotsList.size());
        Assertions.assertEquals(lots, testLots);
    }



    @Override
    protected String getCorrectFileLocation() {
        return "src/test/resources/lot";
    }

    @Override
    protected DataParser createDataParser() {
        return new LotsDataParser();
    }
}
