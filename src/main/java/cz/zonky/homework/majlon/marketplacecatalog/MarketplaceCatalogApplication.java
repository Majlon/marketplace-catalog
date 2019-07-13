package cz.zonky.homework.majlon.marketplacecatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MarketplaceCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketplaceCatalogApplication.class, args);
    }

}
