package com.github.gkttk.epam.auction.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.gkttk.epam.auction.data.exception.DataParserException;
import com.github.gkttk.epam.auction.model.wrapper.Lots;

import java.io.File;
import java.io.IOException;

public class LotsDataParser implements DataParser<Lots> {

    @Override
    public Lots parse(String fileLocation) throws DataParserException {
        File file = new File(fileLocation);
        try {
            return OBJECT_MAPPER.readValue(file, new TypeReference<Lots>() {
            });
        } catch (IOException exception) {
            throw new DataParserException("Can't parse the the file: " + fileLocation + " to " + this.getClass(), exception);
        }
    }
}
