package cz.zonky.homework.majlon.marketplacecatalog.scheduled;

import cz.zonky.homework.majlon.marketplacecatalog.domain.loan.SimpleLoan;
import cz.zonky.homework.majlon.marketplacecatalog.service.MarketplaceService;
import cz.zonky.homework.majlon.marketplacecatalog.utils.MarketplaceUtils;
import cz.zonky.homework.majlon.marketplacecatalog.workers.ConsoleNotifier;
import cz.zonky.homework.majlon.marketplacecatalog.workers.FileWriter;
import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;


@Component
public class MarketplaceScanJob {

    private static final long SECOND = 1000L;
    private static final long MINUTE = 60 * SECOND;

    private static final Logger log = LoggerFactory.getLogger(MarketplaceScanJob.class);

    private final MarketplaceService marketplaceService;

    private final ConsoleNotifier consoleNotifier;
    private final FileWriter fileWriter;

    private Date lastScan;

    @Autowired
    public MarketplaceScanJob(MarketplaceService marketplaceService,
                              ConsoleNotifier consoleNotifier,
                              FileWriter fileWriter) {
        this.marketplaceService = marketplaceService;
        this.consoleNotifier = consoleNotifier;
        this.fileWriter = fileWriter;


        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -50);
        lastScan = now.getTime();
    }

    //TODO change to 5 minutes before finishing
    @Scheduled(fixedRate = 5 * SECOND)
    public void getMarketplaceLoans() {
        log.info("Retrieving marketplace data");

        Observable<SimpleLoan> newLoans = marketplaceService.getLoansNewerThan(lastScan);

        newLoans.subscribe(consoleNotifier);
        newLoans.subscribe(fileWriter);

        lastScan = new Date();
    }
}
