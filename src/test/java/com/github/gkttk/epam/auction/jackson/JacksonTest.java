package com.github.gkttk.epam.auction.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gkttk.epam.auction.logic.Auction;
import com.github.gkttk.epam.auction.logic.Bidder;
import com.github.gkttk.epam.auction.model.Lot;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JacksonTest {


    @Test
    public void writeJsonLot() throws IOException {
        Lot lot1 = new Lot(1, "Picture", 891);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("src/test/resources/lots.txt"), lot1);

    }


    @Test
    public void readJsonLot() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Lot lot = objectMapper.readValue(new File("src/test/resources/lots.txt"), Lot.class);
        System.out.println(lot);
    }

    @Test
    public void readJsonLots() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Lot> lots = objectMapper.readValue(new File("src/test/resources/lots.txt"), new TypeReference<List<Lot>>() {
        });
        lots.forEach(System.out::println);
    }


    @Test
    public void testCreateLotJSON() throws JsonProcessingException {
        Lot lot1 = new Lot(1, "Picture", 891);

        String resultJson = new ObjectMapper().writeValueAsString(lot1);
        System.out.println(resultJson);
    }


    @Test
    public void testCreateAuctionJSON() throws JsonProcessingException {
        Lot lot1 = new Lot(1, "Picture", 891);
        Lot lot2 = new Lot(2, "Clothespin", 50);
        Lot lot3 = new Lot(3, "Statuette", 210);
        Lot lot4 = new Lot(4, "Gobelin", 452);
        Lot lot5 = new Lot(5, "Watch", 623);
        Lot notNeededLot = new Lot(6, "I am not needed for anybody :C", 10000);
        List<Lot> lots = Arrays.asList(lot1, lot2, lot3, lot4, lot5,notNeededLot);

     //   Auction auction = new Auction(lots);



     //   String resultJson = new ObjectMapper().writeValueAsString(auction);
      //  System.out.println(resultJson);
    }



}
