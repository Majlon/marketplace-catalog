package cz.zonky.homework.majlon.marketplacecatalog.scheduled;

import cz.zonky.homework.majlon.marketplacecatalog.domain.loan.LoanDetail;
import cz.zonky.homework.majlon.marketplacecatalog.service.MarketplaceService;
import cz.zonky.homework.majlon.marketplacecatalog.utils.MarketplaceUtils;
import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;


@SuppressWarnings("ResultOfMethodCallIgnored")
@Component
public class MarketplaceScanJob {

    private static final long SECOND = 1000L;
    private static final long MINUTE = 60 * SECOND;

    private static final Logger log = LoggerFactory.getLogger(MarketplaceScanJob.class);

    private final MarketplaceService marketplaceService;
    private Date lastScan;

    @Autowired
    public MarketplaceScanJob(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;

        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -50);
        lastScan = now.getTime();
    }

    //TODO change to 5 minutes before finishing
    @Scheduled(fixedRate = 5 * SECOND)
    public void getMarketplaceLoans() {
        log.info("Retrieving marketplace data {}", MarketplaceUtils.toIso8601Date(new Date()));

        Observable<LoanDetail> newLoans = marketplaceService.getLoansNewerThan(lastScan);
        newLoans.subscribe(System.out::println);

        lastScan = new Date();
    }
}
