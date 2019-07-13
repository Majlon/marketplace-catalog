package cz.zonky.homework.majlon.marketplacecatalog.workers;

import cz.zonky.homework.majlon.marketplacecatalog.domain.loan.SimpleLoan;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsoleNotifier implements Observer<SimpleLoan> {

    private static final Logger log = LoggerFactory.getLogger(ConsoleNotifier.class);

    @Override
    public void onSubscribe(Disposable disposable) {
        log.info("Console notifier just subscribed!");
    }

    @Override
    public void onNext(SimpleLoan simpleLoan) {
        log.info("Retrieved loan info: " + simpleLoan);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Cant process data, cause:", throwable);
    }

    @Override
    public void onComplete() {
        log.info("Console notifier just finished!");
    }
}
