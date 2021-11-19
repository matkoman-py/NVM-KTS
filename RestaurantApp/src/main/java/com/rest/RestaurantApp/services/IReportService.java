package com.rest.RestaurantApp.services;

import com.rest.RestaurantApp.dto.ArticleReportDTO;

public interface IReportService {
    ArticleReportDTO articleProfitDayReport(int year, int month, int day);

    ArticleReportDTO articleProfitMonthReport(int year, int month);

    ArticleReportDTO articleProfitYearReport(int year);

    ArticleReportDTO articleProfitQuarterReport(int year, int quarter);
}
