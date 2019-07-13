package cz.zonky.homework.majlon.marketplacecatalog.scheduled;

import cz.zonky.homework.majlon.marketplacecatalog.domain.loan.SimpleLoan;
import cz.zonky.homework.majlon.marketplacecatalog.service.MarketplaceService;
import cz.zonky.homework.majlon.marketplacecatalog.utils.MarketplaceUtils;
import cz.zonky.homework.majlon.marketplacecatalog.workers.ConsoleNotifier;
import cz.zonky.homework.majlon.marketplacecatalog.workers.FileWriter;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
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
    private static final long SCAN_INTERVAL = 120 * MINUTE; // todo change back to 5

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

        /*
        * To compensate initial
        * */
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MILLISECOND,
                new Long(SCAN_INTERVAL * -1).intValue());
        lastScan = now.getTime();
    }

    @Scheduled(fixedRate = 10000) // todo change to scan_interval
    public void getMarketplaceLoans() {
        log.info("Retrieving marketplace data");

        Observable<SimpleLoan> newLoans = marketplaceService.getLoansNewerThan(lastScan);

        newLoans.subscribeOn(Schedulers.computation()).subscribe(consoleNotifier);
        newLoans.subscribeOn(Schedulers.io()).subscribe(fileWriter);

        lastScan = new Date();
    }
}
