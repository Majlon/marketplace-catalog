package cz.zonky.homework.majlon.marketplacecatalog.service;


import cz.zonky.homework.majlon.marketplacecatalog.domain.loan.LoanDetail;
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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;
import java.util.Objects;

@Service
public class MarketplaceService {

    @Value("${marketplace.URL}")
    private String URL;

    private static final Logger log = LoggerFactory.getLogger(MarketplaceScanJob.class);
    private final RestTemplate restTemplate = new RestTemplate();

    public Maybe<LoanDetail> getLoanDetail(Long id) {
        throw new NotImplementedException();
    }

    public Observable<LoanDetail> getLoansNewerThan(Date date) {
        log.info("Retrieving loans newer than: " + date);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Order", "-datePublished");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(URL);
        uri.queryParam("datePublished__gt", MarketplaceUtils.toParamDate(date));

        ResponseEntity<LoanDetail[]> response = restTemplate.exchange(
                uri.toUriString(),
                HttpMethod.GET,
                entity,
                LoanDetail[].class);

        if (response.hasBody()) {
            return Observable.fromArray(Objects.requireNonNull(response.getBody()));
        } else {
            return Observable.empty();
        }
    }
}
