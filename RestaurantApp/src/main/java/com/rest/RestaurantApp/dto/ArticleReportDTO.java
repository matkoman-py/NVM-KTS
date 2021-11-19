package com.rest.RestaurantApp.dto;

import java.util.HashMap;

public class ArticleReportDTO {

    private HashMap<Integer, ProfitDTO> articleProfits;

    public ArticleReportDTO(HashMap<Integer, ProfitDTO> articleProfits) {
        this.articleProfits = articleProfits;
    }

    public ArticleReportDTO() {
        articleProfits = new HashMap<>();
    }

    public HashMap<Integer, ProfitDTO> getArticleProfits() {
        return articleProfits;
    }

    public void setArticleProfits(HashMap<Integer, ProfitDTO> articleProfits) {
        this.articleProfits = articleProfits;
    }
}
