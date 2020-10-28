import com.github.gkttk.epam.auction.data.BiddersDataParser;
import com.github.gkttk.epam.auction.data.DataParser;
import com.github.gkttk.epam.auction.data.LotsDataParser;
import com.github.gkttk.epam.auction.data.exception.DataParserException;
import com.github.gkttk.epam.auction.logic.Auction;
import com.github.gkttk.epam.auction.logic.exception.AuctionException;
import com.github.gkttk.epam.auction.model.wrapper.Bidders;
import com.github.gkttk.epam.auction.model.wrapper.Lots;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private final static Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        String lotsFileLocation = "files/lot";
        String biddersFileLocation = "files/bidders";
        try {
            DataParser dataParser = new LotsDataParser();
            Lots lots = (Lots) dataParser.parse(lotsFileLocation);
            dataParser = new BiddersDataParser();
            Bidders bidders = (Bidders) dataParser.parse(biddersFileLocation);
            Auction auction = Auction.getInstance();
            auction.startBidding(lots, bidders);
        } catch (DataParserException exception) {
            LOGGER.error("Can't parse the given JSON", exception);
        } catch (AuctionException exception) {
            LOGGER.error("The process of bidding was interrupted in Auction", exception);
        }
    }

}
