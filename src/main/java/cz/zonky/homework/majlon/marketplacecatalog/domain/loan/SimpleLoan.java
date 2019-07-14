package cz.zonky.homework.majlon.marketplacecatalog.domain.loan;

/**
 * Simplified version of loan entity.
 *
 */
public class SimpleLoan {

    private Long id;
    private String url;
    private String name;
    private String purpose;
    private Integer termInMonths;
    private Double interestRate;
    private Double revenueRate;
    private String rating;
    private Integer amount;
    private String currency;
    private String datePublished;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Integer getTermInMonths() {
        return termInMonths;
    }

    public void setTermInMonths(Integer termInMonths) {
        this.termInMonths = termInMonths;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getRevenueRate() {
        return revenueRate;
    }

    public void setRevenueRate(Double revenueRate) {
        this.revenueRate = revenueRate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    @Override
    public String toString() {
        return "SimpleLoan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating='" + rating + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", datePublished='" + datePublished + '\'' +
                '}';
    }

    public static String getFields() {
        return "id," +
                "url," +
                "name," +
                "purpose," +
                "termInMonths," +
                "interestRate," +
                "revenueRate," +
                "rating," +
                "amount," +
                "currency," +
                "datePublished";
    }
}
