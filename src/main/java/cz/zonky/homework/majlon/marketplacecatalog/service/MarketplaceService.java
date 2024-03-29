package cz.zonky.homework.majlon.marketplacecatalog.service;


import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * General service for retrieving marketplace info
 * and loans/loan details. Important settings are
 * located in application.properties.
 *
 */
@Service
public class MarketplaceService {

    @Value("${marketplace.URL}")
    private String MARKETPLACE_URL;

    @Value("${loans.URL}")
    private String LOANS_URL;

    @Value("${loanDetail.exportFolder}")
    private String LOAN_DETAIL_FOLDER;

    private static final String LOAN_DETAIL = "_LoanDetail";
    private static final String JSON = ".json";

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

        return response.hasBody()
                ? Maybe.just(Objects.requireNonNull(response.getBody()))
                : Maybe.empty();
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

        return response.hasBody()
                ? Observable.fromArray(Objects.requireNonNull(response.getBody()))
                : Observable.empty();
    }

    public Boolean saveLoanDetail(LoanDetail detail) {
        log.info("Saving loan detail to file");
        ObjectMapper mapper = new ObjectMapper();

        File exportFolder = new File(System.getProperty("user.dir")
                + File.separator
                + LOAN_DETAIL_FOLDER);

        try {
            boolean folderCreated = true;

            if (!exportFolder.exists()) {
                folderCreated = exportFolder.mkdir();
            }

            if (!folderCreated) {
                throw new IOException("Failed to create loan export folder");
            }

            File fullExportPath = new File(exportFolder.getPath()
                    + File.separator
                    + detail.getId().toString()
                    + LOAN_DETAIL
                    + JSON);

            FileWriter writer = new FileWriter(fullExportPath);
            BufferedWriter bw = new BufferedWriter(writer);

            bw.write(mapper.writeValueAsString(detail));
            bw.flush();
            bw.close();
            return true;
        } catch (IOException e) {
            log.error("Failed saving loan detail to disk ", e);
            return false;
        }
    }
}
