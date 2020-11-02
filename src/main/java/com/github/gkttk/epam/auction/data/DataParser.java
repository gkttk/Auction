package com.github.gkttk.epam.auction.data;

import com.github.gkttk.epam.auction.data.exception.DataParserException;

public interface DataParser<T> {

    T parse(String fileLocation) throws DataParserException;

}
