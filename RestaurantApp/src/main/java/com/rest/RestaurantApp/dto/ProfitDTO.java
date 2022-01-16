package com.rest.RestaurantApp.dto;

public class ProfitDTO {
    private String articleName;
    private Double earning;
    private int numberOfOrders;
    private Double totalMakingPrice;
    private Double totalSellingPrice;

    public ProfitDTO(Double earning, int numberOfOrders, double totalSellingPrice, double totalMakingPrice, String name) {
        this.earning = earning;
        this.numberOfOrders = numberOfOrders;
        this.totalMakingPrice = totalMakingPrice;
        this.totalSellingPrice = totalSellingPrice;
        this.articleName = name;
    }

    public ProfitDTO() {
        this.earning = 0.0;
        this.numberOfOrders = 0;
        this.totalSellingPrice = 0.0;
        this.totalMakingPrice = 0.0;
        this.articleName = "";
    }

    public Double getTotalMakingPrice() {
        return totalMakingPrice;
    }

    public void setTotalMakingPrice(Double totalMakingPrice) {
        this.totalMakingPrice = totalMakingPrice;
    }

    public Double getTotalSellingPrice() {
        return totalSellingPrice;
    }

    public void setTotalSellingPrice(Double totalSellingPrice) {
        this.totalSellingPrice = totalSellingPrice;
    }

    public Double getEarning() {
        return earning;
    }

    public void setEarning(Double earning) {
        this.earning = earning;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }
}
