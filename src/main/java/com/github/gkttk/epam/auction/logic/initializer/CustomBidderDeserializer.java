package com.github.gkttk.epam.auction.logic.initializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.gkttk.epam.auction.logic.Auction;
import com.github.gkttk.epam.auction.logic.Bidder;
import com.github.gkttk.epam.auction.logic.initializer.AuctionInitializer;
import com.github.gkttk.epam.auction.logic.initializer.exception.InitializerException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Semaphore;

/*
public class CustomBidderDeserializer extends StdDeserializer<Bidder> {

    private final static AuctionInitializer auctionInitializer = new AuctionInitializer();
    private final static Semaphore SEMAPHORE = new Semaphore(1);
    private final Auction auction;

    public CustomBidderDeserializer() throws InitializerException {
        this(null);

    }

*/
/*    public CustomBidderDeserializer(Class<?> vc) throws InitializerException {
        super(vc);
        auction = auctionInitializer.createAuction();
    }

    @Override
    public Bidder deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Bidder bidder = new Bidder();
        //сет семафора
        bidder.setSemaphore(SEMAPHORE);
        try {
            Auction auction = auctionInitializer.createAuction();
        } catch (InitializerException e) {
            e.printStackTrace();//todo
        }
        //сет аукциона
        bidder.setCurrentAuction(auction);

//сет имени
        ObjectCodec codec = jsonParser.getCodec();
        TreeNode treeNode = codec.readTree(jsonParser);

        TreeNode nameNode = treeNode.get("name");
        String name = nameNode.toString();
        bidder.setName(name);

        //сет requiredLots
        TreeNode requiredLotsNode = treeNode.get("required_lots");
        String requiredLotsString = requiredLotsNode.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Double> requiredLots = objectMapper.readValue(requiredLotsString, new TypeReference<Map<String, Double>>() {
        });
        bidder.setRequiredLots(requiredLots);


        return bidder;
    }*/
/*}*/
