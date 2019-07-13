package cz.zonky.homework.majlon.marketplacecatalog.domain.loan;

public class LoanInsuranceHistory {

    private String policyPeriodFrom;
    private String policyPeriodTo;

    public String getPolicyPeriodFrom() {
        return policyPeriodFrom;
    }

    public void setPolicyPeriodFrom(String policyPeriodFrom) {
        this.policyPeriodFrom = policyPeriodFrom;
    }

    public String getPolicyPeriodTo() {
        return policyPeriodTo;
    }

    public void setPolicyPeriodTo(String policyPeriodTo) {
        this.policyPeriodTo = policyPeriodTo;
    }

    @Override
    public String toString() {
        return "LoanInsuranceHistory{" +
                "policyPeriodFrom='" + policyPeriodFrom + '\'' +
                ", policyPeriodTo='" + policyPeriodTo + '\'' +
                '}';
    }
}
