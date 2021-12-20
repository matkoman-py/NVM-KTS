package com.rest.RestaurantApp.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfitDTO {
    private Double earning;
    private int numberOfOrders;
    private Double totalMakingPrice;
    private Double totalSellingPrice;

    public ProfitDTO() {
        this.earning = 0.0;
        this.numberOfOrders = 0;
        this.totalSellingPrice = 0.0;
        this.totalMakingPrice = 0.0;
    }
}
