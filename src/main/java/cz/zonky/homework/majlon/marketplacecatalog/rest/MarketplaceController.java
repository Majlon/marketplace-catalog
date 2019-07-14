package cz.zonky.homework.majlon.marketplacecatalog.rest;

import cz.zonky.homework.majlon.marketplacecatalog.domain.loan.SimpleLoan;
import cz.zonky.homework.majlon.marketplacecatalog.service.MarketplaceService;
import cz.zonky.homework.majlon.marketplacecatalog.utils.MarketplaceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class MarketplaceController {

    @Value("${rest.loanInterval}")
    private int LOAN_INTERVAL;

    private final MarketplaceService marketplaceService;

    @Autowired
    public MarketplaceController(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;
    }

    @RequestMapping("/latestLoans")
    public List<SimpleLoan> greeting() {
        Date pastDate = MarketplaceUtils.getComposedDate(Calendar.MINUTE, -1 * LOAN_INTERVAL);
        return marketplaceService.getLoansNewerThan(pastDate).toList().blockingGet();
    }
}
