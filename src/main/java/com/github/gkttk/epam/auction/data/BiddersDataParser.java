package com.github.gkttk.epam.auction.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gkttk.epam.auction.data.exception.DataParserException;
import com.github.gkttk.epam.auction.model.wrapper.Bidders;

import java.io.File;
import java.io.IOException;

public class BiddersDataParser implements DataParser<Bidders> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Bidders parse(String fileLocation) throws DataParserException {
        File file = new File(fileLocation);
        try {
            return objectMapper.readValue(file, Bidders.class);
        } catch (IOException exception) {
            throw new DataParserException("Can't parse the the file: " + fileLocation + " to " + this.getClass(), exception);
        }
    }
}
