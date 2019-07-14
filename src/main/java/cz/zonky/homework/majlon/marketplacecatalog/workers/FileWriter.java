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

/**
 * Component for saving loan details to dedicated folder.
 * Loan details are saved in JSON format.
 */
@Component
public class FileWriter implements Observer<SimpleLoan> {

    private static final Logger log = LoggerFactory.getLogger(FileWriter.class);
    private final MarketplaceService marketplaceService;
    private int fileCounter;
    private int savedFiles;

    @Autowired
    public FileWriter(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        fileCounter = 0;
        savedFiles = 0;
    }

    @Override
    public void onNext(SimpleLoan simpleLoan) {
        Maybe<LoanDetail> detail =
                marketplaceService.getLoanDetail(simpleLoan.getId());

        detail.subscribe(loanDetail -> {
                    Boolean fileSaved = marketplaceService.saveLoanDetail(loanDetail);
                    if (fileSaved) {
                        savedFiles++;
                    }
                    fileCounter++;
                }
        );
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Cant process data, cause:", throwable);
    }

    @Override
    public void onComplete() {
        if (fileCounter > 0) {
            log.info("Saved " + savedFiles + " files out of " + fileCounter);
        } else {
            log.info("No files to save received");
        }
    }
}
