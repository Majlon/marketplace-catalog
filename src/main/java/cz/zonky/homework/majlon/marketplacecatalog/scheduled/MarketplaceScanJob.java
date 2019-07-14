package cz.zonky.homework.majlon.marketplacecatalog.scheduled;

import cz.zonky.homework.majlon.marketplacecatalog.domain.loan.SimpleLoan;
import cz.zonky.homework.majlon.marketplacecatalog.service.MarketplaceService;
import cz.zonky.homework.majlon.marketplacecatalog.utils.MarketplaceUtils;
import cz.zonky.homework.majlon.marketplacecatalog.workers.ConsoleNotifier;
import cz.zonky.homework.majlon.marketplacecatalog.workers.FileWriter;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;


/**
 * Periodically scans marketplace for new loans in 5
 * minute intervals
 */
@Component
public class MarketplaceScanJob {

    private static final long SECOND = 1000L;
    private static final long MINUTE = 60 * SECOND;
    private static final long SCAN_INTERVAL = 5 * MINUTE;

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
         * To compensate initial interval, lastScan
         * variable is reduced by value of SCAN_INTERVAL
         */
        lastScan = MarketplaceUtils.getComposedDate(
                Calendar.MILLISECOND,
                new Long(SCAN_INTERVAL * -1).intValue());
    }

    @Scheduled(fixedRate = SCAN_INTERVAL)
    public void getMarketplaceLoans() {
        final Observable<SimpleLoan> newLoans = marketplaceService.getLoansNewerThan(lastScan);

        newLoans.subscribeOn(Schedulers.computation()).subscribe(consoleNotifier);
        newLoans.subscribeOn(Schedulers.io()).subscribe(fileWriter);

        lastScan = new Date();
    }
}
