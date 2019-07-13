package cz.zonky.homework.majlon.marketplacecatalog.scheduled;

import cz.zonky.homework.majlon.marketplacecatalog.domain.loan.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@Component
public class MarketplaceJob {

    private static final long SECOND = 1000L;
    private static final long MINUTE = 60 * SECOND;

    private static final String URL = "https://api.zonky.cz/loans/marketplace";

    private static final Logger log = LoggerFactory.getLogger(MarketplaceJob.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    private Long counter;

    @Value("${custom.multiplier}")
    private Integer multiplier;

    public MarketplaceJob() {
        this.counter = 1L;
    }

    //TODO change to 5 minutes before finishing
    @Scheduled(fixedRate = 10 * SECOND)
    public void getMarketplaceLoans() {
        dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
        log.info("Retrieving marketplace data {}", dateFormat.format(new Date()));
        System.out.println(counter * multiplier + " iteration.");

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Order", "-datePublished");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(URL);
        uri.queryParam("datePublished__gt", "2019-07-12T21:50");


        ResponseEntity<Loan[]> response = restTemplate.exchange(
                uri.toUriString(),
                HttpMethod.GET,
                entity,
                Loan[].class);

        List<Loan> loans = Arrays.asList(response.getBody());
        System.out.println("Retrieved loans: " + loans.size());
        System.out.println(loans.get(0));
        counter++;
    }

}
