import com.github.gkttk.epam.auction.logic.Auction;
import com.github.gkttk.epam.auction.logic.Bidder;
import com.github.gkttk.epam.auction.logic.exception.AuctionException;
import com.github.gkttk.epam.auction.model.Lot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private final static Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        Lot lot1 = new Lot(1, "Picture", 891);
        Lot lot2 = new Lot(2, "Clothespin", 50);
        Lot lot3 = new Lot(3, "Statuette", 210);
        Lot lot4 = new Lot(4, "Gobelin", 452);
        Lot lot5 = new Lot(5, "Watch", 623);
        Lot notNeededLot = new Lot(6, "I am not needed for anybody :C", 10000);


        List<Lot> lots = Arrays.asList(lot1, lot2, lot3, lot4, lot5, notNeededLot);


        Map<String, Double> requiredLots1 = Stream.of(new Object[][]{
                {lot1.getName(), 1300d},
                {lot4.getName(), 620d},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));

        Map<String, Double> requiredLots2 = Stream.of(new Object[][]{
                {lot2.getName(), 210d},
                {lot1.getName(), 1310d},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));

        Map<String, Double> requiredLots3 = Stream.of(new Object[][]{
                {lot4.getName(), 550d},
                {lot3.getName(), 211d},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));

        Map<String, Double> requiredLots4 = Stream.of(new Object[][]{
                {lot1.getName(), 1500d},
                {lot5.getName(), 620d},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));

        Map<String, Double> requiredLots5 = Stream.of(new Object[][]{
                {lot5.getName(), 800d},
                {lot2.getName(), 150d},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));
        Auction auction = Auction.getInstance();
        Semaphore semaphore = new Semaphore(1);
        List<Bidder> bidders = Arrays.asList(
                new Bidder(semaphore, auction, "Bidder1", requiredLots1),
                new Bidder(semaphore, auction, "Bidder2", requiredLots2),
                new Bidder(semaphore, auction, "Bidder3", requiredLots3),
                new Bidder(semaphore, auction, "Bidder4", requiredLots4),
                new Bidder(semaphore, auction, "Bidder5", requiredLots5)
        );

        try {
            auction.startBidding(lots, bidders);
        } catch (AuctionException exception) {
            LOGGER.error("Auction was interrupted.\nException: ", exception);
        }


    }


}
