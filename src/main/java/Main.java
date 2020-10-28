import com.github.gkttk.epam.auction.data.BiddersDataParser;
import com.github.gkttk.epam.auction.data.exception.DataParserException;
import com.github.gkttk.epam.auction.logic.exception.AuctionException;
import com.github.gkttk.epam.auction.logic.observable.Auction;
import com.github.gkttk.epam.auction.logic.observer.Bidder;
import com.github.gkttk.epam.auction.model.Lot;
import com.github.gkttk.epam.auction.model.wrapper.Bidders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private final static Logger LOGGER = LogManager.getLogger(Main.class);
    private final static String BIDDERS_FILE_LOCATION = "files/bidders";

    public static void main(String[] args) {
        ExecutorService executorService = null;
        Auction auction = null;
        try {
            BiddersDataParser biddersDataParser = new BiddersDataParser();
            Bidders biddersWrapper = biddersDataParser.parse(BIDDERS_FILE_LOCATION);
            List<Bidder> bidders = biddersWrapper.getBidders();

            executorService = Executors.newFixedThreadPool(bidders.size());
            for (Bidder bidder : bidders) {
                executorService.execute(bidder);
            }

            auction = Auction.getInstance();
            auction.startBidding();

        } catch (AuctionException exception) {
            LOGGER.error("AuctionException has occurred", exception);
        } catch (DataParserException exception) {
            LOGGER.error("DataParserException has occurred", exception);
        } finally {
            if (executorService != null) {
                executorService.shutdown();
            }
        }

        if (auction != null) {
            List<Lot> auctionLots = auction.getAuctionLots();
            System.out.println("---------------Auction results:---------------");
            for (Lot auctionLot : auctionLots) {
                System.out.println(String.format("%s was purchased by %s for %.2f",
                        auctionLot.getName(), auctionLot.getCustomer(), auctionLot.getCurrentPrice()));
            }
        }
    }

}
