package cz.zonky.homework.majlon.marketplacecatalog.service;


import cz.zonky.homework.majlon.marketplacecatalog.domain.loan.LoanDetail;
import cz.zonky.homework.majlon.marketplacecatalog.domain.loan.SimpleLoan;
import cz.zonky.homework.majlon.marketplacecatalog.scheduled.MarketplaceScanJob;
import cz.zonky.homework.majlon.marketplacecatalog.utils.MarketplaceUtils;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.Objects;

@Service
public class MarketplaceService {

    @Value("${marketplace.URL}")
    private String MARKETPLACE_URL;

    @Value("${loans.URL}")
    private String LOANS_URL;

    @Value("${loanDetail.filePath}")
    private String LOAN_DETAIL_FOLDER;


    private static final Logger log = LoggerFactory.getLogger(MarketplaceScanJob.class);
    private final RestTemplate restTemplate = new RestTemplate();

    public Maybe<LoanDetail> getLoanDetail(Long id) {
        log.info("Retrieving loan detail, id: " + id);
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(LOANS_URL);
        uri.path(id.toString());

        ResponseEntity<LoanDetail> response = restTemplate.exchange(
                uri.toUriString(),
                HttpMethod.GET,
                entity,
                LoanDetail.class);

        if (response.hasBody()) {
            return Maybe.just(Objects.requireNonNull(response.getBody()));
        } else {
            return Maybe.empty();
        }
    }

    public Observable<SimpleLoan> getLoansNewerThan(Date date) {
        log.info("Retrieving loans newer than: " + MarketplaceUtils.toIso8601Date(date));

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Order", "-datePublished");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(MARKETPLACE_URL);
        uri.queryParam("datePublished__gt", MarketplaceUtils.toParamDate(date));
        uri.queryParam("fields", SimpleLoan.getFields());

        ResponseEntity<SimpleLoan[]> response = restTemplate.exchange(
                uri.toUriString(),
                HttpMethod.GET,
                entity,
                SimpleLoan[].class);

        if (response.hasBody()) {
            return Observable.fromArray(Objects.requireNonNull(response.getBody()));
        } else {
            return Observable.empty();
        }
    }

    public Boolean saveLoanDetail(LoanDetail detail) {
        log.info(detail.toString());
        return true;
    }

}
