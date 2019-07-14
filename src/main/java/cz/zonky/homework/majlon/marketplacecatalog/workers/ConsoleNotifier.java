package cz.zonky.homework.majlon.marketplacecatalog.workers;

import cz.zonky.homework.majlon.marketplacecatalog.domain.loan.SimpleLoan;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *  Component for notifying user about
 *  new loans via console.
 */
@Component
public class ConsoleNotifier implements Observer<SimpleLoan> {

    private static final Logger log = LoggerFactory.getLogger(ConsoleNotifier.class);
    private int loansCounter;

    @Override
    public void onSubscribe(Disposable disposable) {
        loansCounter = 0;
    }

    @Override
    public void onNext(SimpleLoan simpleLoan) {
        log.info("Retrieved loan info: " + simpleLoan);
        loansCounter++;
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Cant process data, cause:", throwable);
    }

    @Override
    public void onComplete() {
        log.info("Received loans count: " + loansCounter);
    }
}
