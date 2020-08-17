package ru.eva.oasis.model;

public class MortgageProgram {

    private String bankName;
    private Integer maxAge;
    private Integer maxInitialPayment;
    private Integer maxMortgageTerm;
    private Integer maxObjectCost;
    private Integer minAge;
    private Integer minInitialPayment;
    private Integer minMortgageTerm;
    private Integer minObjectCost;
    private String programName;
    private String textDescription;
    private String imageUrl;
    private String calculationMode;
    private Integer id;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMaxInitialPayment() {
        return maxInitialPayment;
    }

    public void setMaxInitialPayment(Integer maxInitialPayment) {
        this.maxInitialPayment = maxInitialPayment;
    }

    public Integer getMaxMortgageTerm() {
        return maxMortgageTerm;
    }

    public void setMaxMortgageTerm(Integer maxMortgageTerm) {
        this.maxMortgageTerm = maxMortgageTerm;
    }

    public Integer getMaxObjectCost() {
        return maxObjectCost;
    }

    public void setMaxObjectCost(Integer maxObjectCost) {
        this.maxObjectCost = maxObjectCost;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMinInitialPayment() {
        return minInitialPayment;
    }

    public void setMinInitialPayment(Integer minInitialPayment) {
        this.minInitialPayment = minInitialPayment;
    }

    public Integer getMinMortgageTerm() {
        return minMortgageTerm;
    }

    public void setMinMortgageTerm(Integer minMortgageTerm) {
        this.minMortgageTerm = minMortgageTerm;
    }

    public Integer getMinObjectCost() {
        return minObjectCost;
    }

    public void setMinObjectCost(Integer minObjectCost) {
        this.minObjectCost = minObjectCost;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCalculationMode() {
        return calculationMode;
    }

    public void setCalculationMode(String calculationMode) {
        this.calculationMode = calculationMode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
