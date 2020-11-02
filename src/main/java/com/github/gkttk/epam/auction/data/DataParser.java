package com.github.gkttk.epam.auction.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gkttk.epam.auction.data.exception.DataParserException;


public interface DataParser<T> {

    ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    T parse(String fileLocation) throws DataParserException;

}
