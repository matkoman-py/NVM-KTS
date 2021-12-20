package com.rest.RestaurantApp.dto;

import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReportDTO {

    private HashMap<Integer, ProfitDTO> articleProfits;
}
