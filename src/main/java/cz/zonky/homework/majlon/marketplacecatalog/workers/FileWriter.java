package cz.zonky.homework.majlon.marketplacecatalog.workers;

import cz.zonky.homework.majlon.marketplacecatalog.domain.loan.LoanDetail;
import cz.zonky.homework.majlon.marketplacecatalog.domain.loan.SimpleLoan;
import cz.zonky.homework.majlon.marketplacecatalog.service.MarketplaceService;
import io.reactivex.Maybe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileWriter implements Observer<SimpleLoan> {

    private static final Logger log = LoggerFactory.getLogger(FileWriter.class);

    private final MarketplaceService marketplaceService;

    @Autowired
    public FileWriter(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;
    }

    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onNext(SimpleLoan simpleLoan) {
        log.info("Will save to file");
        Maybe<LoanDetail> loanDetail =
                marketplaceService.getLoanDetail(simpleLoan.getId());

        loanDetail.subscribe(marketplaceService::saveLoanDetail);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Cant process data, cause:", throwable);
    }

    @Override
    public void onComplete() {

    }
}
